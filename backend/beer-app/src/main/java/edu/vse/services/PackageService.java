package edu.vse.services;

import edu.vse.context.CallContext;
import edu.vse.daos.OrderDao;
import edu.vse.daos.PackageDao;
import edu.vse.daos.ProductDao;
import edu.vse.daos.ProductPackageDao;
import edu.vse.dtos.Package;
import edu.vse.dtos.Packages;
import edu.vse.exceptions.NotFoundException;
import edu.vse.models.OrderEntity;
import edu.vse.models.PackageEntity;
import edu.vse.models.ProductEntity;
import edu.vse.models.ProductPackageEntity;
import edu.vse.utils.UriConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Service
public class PackageService {

    private final PackageDao packageDao;
    private final ProductPackageDao productPackageDao;
    private final ProductDao productDao;
    private final OrderDao orderDao;

    @Autowired
    public PackageService(PackageDao packageDao, ProductPackageDao productPackageDao, ProductDao productDao, OrderDao orderDao) {
        this.packageDao = packageDao;
        this.productPackageDao = productPackageDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    @Cacheable(value = "/packages/", key = "#p0")
    public Optional<Package> getPackage(int id) {
        List<ProductPackageEntity> list = productPackageDao.findByPackageEntity_Id(id);
        return packageDao.getById(id).map(packageEntity -> packageEntity.toDto(list));
    }

    @Cacheable(value = "/packages/", key = "#p0/#p1")
    public Optional<Package> getPackageForUser(int id, int user) {
        Optional<PackageEntity> byIdAndUser = packageDao.getById(id);
        if (byIdAndUser.isPresent() && byIdAndUser.get().getOrder().getUser().getId() == user) {
            List<ProductPackageEntity> list = productPackageDao.findByPackageEntity_Id(id);
            return byIdAndUser.map(packageEntity -> packageEntity.toDto(list));
        } else {
            return Optional.empty();
        }
    }

    @Cacheable(value = "/packages/")
    public Packages listAll() {
        List<Package> collect = packageDao.findAll()
                .stream()
                .map(packageEntity -> packageEntity.toDto(productPackageDao.findByPackageEntity_Id(packageEntity.getId())))
                .collect(toList());
        return new Packages(collect);
    }

    @Cacheable(value = "/packages/", key = "'?user='.concat(#p0)")
    public Packages listAllForUser(int user) {
        List<Package> collect = packageDao.findAll()
                .stream()
                .filter(packageEntity -> packageEntity.getOrder().getUser().getId() == user)
                .map(packageEntity -> packageEntity.toDto(productPackageDao.findByPackageEntity_Id(packageEntity.getId())))
                .collect(toList());
        return new Packages(collect);
    }

    @CacheEvict(value = "/packages/")
    public Package save(Package _package) {
        PackageEntity newPackageEntity = fromDto(_package);
        if (newPackageEntity.getOrder().getUser().getId() != CallContext.getContext().getUserId().get() || CallContext.isAdmin()) {
            throw new NotFoundException("Order not found");
        }
        PackageEntity packageEntity = packageDao.saveAndFlush(newPackageEntity);
        List<ProductPackageEntity> list = _package.getProducts()
                .stream()
                .map(embeddedProduct -> embeddedFromDto(embeddedProduct, packageEntity, true))
                .map(productPackageEntity -> productPackageDao.saveAndFlush(productPackageEntity))
                .collect(toList());
        return packageEntity.toDto(list);
    }

    @CacheEvict(value = "/packages/")
    @Transactional
    public Package saveWithId(Package _package, int id) {
        PackageEntity oldPackageEntity = packageDao.getById(id)
                .orElseThrow(() -> new NotFoundException("Package not found"));

        if (oldPackageEntity.getOrder().getUser().getId() != CallContext.getContext().getUserId().get() && !CallContext.isAdmin()) {
            throw new NotFoundException("Package not found");
        }

        PackageEntity detached = fromDto(_package);
        detached.setId(oldPackageEntity.getId());
        PackageEntity packageEntity = packageDao.saveAndFlush(detached);

        productPackageDao.deleteAllByPackageEntity_Id(packageEntity.getId());

        List<ProductPackageEntity> list = _package.getProducts()
                .stream()
                .map(embeddedProduct -> embeddedFromDto(embeddedProduct, packageEntity, !CallContext.isAdmin()))
                .map(productPackageEntity -> productPackageDao.saveAndFlush(productPackageEntity))
                .collect(toList());
        return packageEntity.toDto(list);
    }

    private PackageEntity fromDto(Package _package) {
        notNull(_package, "Package is mandatory");
        notNull(_package.getOrder(), "Order is mandatory");

        isTrue(UriConstants.order.matches(_package.getOrder()), "Invalid order URI");
        int order = Integer.parseInt(UriConstants.order.match(_package.getOrder()).get("id"));
        OrderEntity orderEntity = orderDao.findById(order)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        return new PackageEntity(orderEntity);
    }

    private ProductPackageEntity embeddedFromDto(Package.EmbeddedProduct embeddedProduct, PackageEntity packageEntity, boolean setPrice) {
        notNull(embeddedProduct, "Embedded product is mandatory");
        notNull(embeddedProduct.getProduct(), "Product in embedded product is mandatory");
        notNull(embeddedProduct.getQuantity(), "Quantity in embedded product is mandatory");

        isTrue(UriConstants.product.matches(embeddedProduct.getProduct()), "Invalid product URI");
        int product = Integer.parseInt(UriConstants.product.match(embeddedProduct.getProduct()).get("id"));
        ProductEntity productEntity = productDao.findById(product)
                .orElseThrow(() -> new NotFoundException("Product in embedded product not found"));

        return new ProductPackageEntity(productEntity, packageEntity, embeddedProduct.getQuantity(), getPrice(embeddedProduct, setPrice, productEntity), embeddedProduct.getMessage());
    }

    private Float getPrice(Package.EmbeddedProduct embeddedProduct, boolean setPrice, ProductEntity productEntity) {
        return setPrice ?
                productEntity.getPrice() :
                embeddedProduct.getPrice() == null ?
                        productEntity.getPrice() :
                        embeddedProduct.getPrice();
    }
}
