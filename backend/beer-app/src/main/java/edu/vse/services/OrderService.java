package edu.vse.services;

import edu.vse.daos.AddressDao;
import edu.vse.daos.OrderDao;
import edu.vse.daos.OrderStatusDao;
import edu.vse.daos.PackageDao;
import edu.vse.daos.PaymentTypeDao;
import edu.vse.daos.ProductPackageDao;
import edu.vse.daos.ShippingDao;
import edu.vse.daos.UserDao;
import edu.vse.dtos.Order;
import edu.vse.exceptions.InternalServerErrorException;
import edu.vse.exceptions.NotFoundException;
import edu.vse.models.AddressEntity;
import edu.vse.models.OrderEntity;
import edu.vse.models.OrderStatusEntity;
import edu.vse.models.PackageEntity;
import edu.vse.models.PaymentTypeEntity;
import edu.vse.models.ProductPackageEntity;
import edu.vse.models.ShippingEntity;
import edu.vse.models.UserEntity;
import edu.vse.utils.UriConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderDao orderDao;
    private final PackageDao packageDao;
    private final ProductPackageDao productPackageDao;
    private final UserDao userDao;
    private final AddressDao addressDao;
    private final OrderStatusDao orderStatusDao;
    private final PaymentTypeDao paymentTypeDao;
    private final ShippingDao shippingDao;

    @Autowired
    public OrderService(OrderDao orderDao, PackageDao packageDao, ProductPackageDao productPackageDao, UserDao userDao, AddressDao addressDao, OrderStatusDao orderStatusDao, PaymentTypeDao paymentTypeDao, ShippingDao shippingDao) {
        this.orderDao = orderDao;
        this.packageDao = packageDao;
        this.productPackageDao = productPackageDao;
        this.userDao = userDao;
        this.addressDao = addressDao;
        this.orderStatusDao = orderStatusDao;
        this.paymentTypeDao = paymentTypeDao;
        this.shippingDao = shippingDao;
    }

    @Cacheable(value = "/orders/", key = "#p0")
    public Optional<Order> getOrder(int id) {
        try {
            List<Integer> packageIds = packageDao.findByOrder_Id(id).stream().map(PackageEntity::getId).collect(toList());
            List<ProductPackageEntity> productPackages = productPackageDao.findByPackageEntity_IdIn(packageIds);
            Float price = calculatePrice(productPackages);
            return Optional.of(orderDao.getOne(id)).map(orderEntity -> orderEntity.toDto((price)));
        } catch (EntityNotFoundException e) {
            log.info("action=address-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/orders/", key = "#p0/#p1")
    public Optional<Order> getOrderForUser(int id, int user) {
        try {
            List<Integer> packageIds = packageDao.findByOrder_Id(id).stream().map(PackageEntity::getId).collect(toList());
            List<ProductPackageEntity> productPackages = productPackageDao.findByPackageEntity_IdIn(packageIds);
            Float price = calculatePrice(productPackages);
            return orderDao.findByIdAndUser_Id(id, user).map(orderEntity -> orderEntity.toDto(price));
        } catch (EntityNotFoundException e) {
            log.info("action=address-not-found id={}", id);
            return Optional.empty();
        }
    }

    @CacheEvict(value = "/orders/")
    public Order save(Order order) {
        OrderEntity orderEntity = orderDao.saveAndFlush(fromDto(order));
        return orderEntity.toDto(0F);
    }

    @CacheEvict(value = "/orders/")
    public Order saveWithId(Order order, int id) {
        OrderEntity oldOrderEntity = orderDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        OrderEntity detached = fromDto(order);
        detached.setId(oldOrderEntity.getId());
        OrderEntity orderEntity = orderDao.saveAndFlush(detached);
        return orderEntity.toDto(0F);
    }

    private Float calculatePrice(List<ProductPackageEntity> productPackages) {
        return productPackages.stream().map(productPackageEntity -> productPackageEntity.getPrice()).reduce((left, right) -> left + right).orElseThrow(InternalServerErrorException::new);
    }

    private OrderEntity fromDto(Order order) {
        notNull(order, "Order is mandatory");
        notNull(order.getBillingAddress(), "Billing address is mandatory");
        notNull(order.getShippingAddress(), "Shipping address is mandatory");
        notNull(order.getPaymentType(), "Payment type is mandatory");
        notNull(order.getUser(), "User is mandatory");
        notNull(order.getStatus(), "Status is mandatory");
        notNull(order.getShipping(), "Shipping is mandatory");

        isTrue(UriConstants.user.matches(order.getUser()), "Invalid user URI");
        isTrue(UriConstants.address.matches(order.getBillingAddress()), "Invalid billing address URI");
        isTrue(UriConstants.address.matches(order.getShippingAddress()), "Invalid shipping address URI");
        isTrue(UriConstants.shipping.matches(order.getShipping()), "Invalid shipping URI");

        int user = Integer.parseInt(UriConstants.user.match(order.getUser()).get("id"));
        UserEntity userEntity = userDao.findById(user)
                .orElseThrow(() -> new NotFoundException("User not found"));
        int billingAddress = Integer.parseInt(UriConstants.address.match(order.getBillingAddress()).get("id"));
        AddressEntity billingAddressEntity = addressDao.findById(billingAddress)
                .orElseThrow(() -> new NotFoundException("Billing address not found"));
        int shippingAddress = Integer.parseInt(UriConstants.address.match(order.getBillingAddress()).get("id"));
        AddressEntity shippingAddressEntity = addressDao.findById(shippingAddress)
                .orElseThrow(() -> new NotFoundException("Shipping address not found"));
        OrderStatusEntity orderStatusEntity = orderStatusDao.findByName(order.getStatus())
                .orElseThrow(() -> new NotFoundException("Order status not found"));
        PaymentTypeEntity paymentTypeEntity = paymentTypeDao.findByName(order.getPaymentType())
                .orElseThrow(() -> new NotFoundException("Payment type not found"));
        int shipping = Integer.parseInt(UriConstants.shipping.match(order.getShipping()).get("id"));
        ShippingEntity shippingEntity = shippingDao.findById(shipping)
                .orElseThrow(() -> new NotFoundException("Shipping not found"));
        return new OrderEntity(
                userEntity,
                billingAddressEntity,
                shippingAddressEntity,
                orderStatusEntity,
                paymentTypeEntity,
                shippingEntity
        );
    }
}