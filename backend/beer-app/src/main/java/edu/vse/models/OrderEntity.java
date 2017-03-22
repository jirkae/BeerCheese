package edu.vse.models;

import edu.vse.dtos.Order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "`order`")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`user`")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "shipping_address")
    private AddressEntity shippingAddress;

    @ManyToOne
    @JoinColumn(name = "billing_address")
    private AddressEntity billingAddress;

    @ManyToOne
    @JoinColumn(name = "order_status")
    private OrderStatusEntity status;

    @ManyToOne
    @JoinColumn(name = "payment_type")
    private PaymentTypeEntity paymentType;

    @ManyToOne
    @JoinColumn(name = "shipping")
    private ShippingEntity shipping;

    private Float discount;

    public OrderEntity() {
    }

    public OrderEntity(UserEntity user, AddressEntity shippingAddress, AddressEntity billingAddress, OrderStatusEntity status, PaymentTypeEntity paymentType, ShippingEntity shipping) {
        this.user = user;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.status = status;
        this.paymentType = paymentType;
        this.shipping = shipping;
        this.discount = 0F;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressEntity getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressEntity billingAddress) {
        this.billingAddress = billingAddress;
    }

    public OrderStatusEntity getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEntity status) {
        this.status = status;
    }

    public PaymentTypeEntity getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeEntity paymentType) {
        this.paymentType = paymentType;
    }

    public ShippingEntity getShipping() {
        return shipping;
    }

    public void setShipping(ShippingEntity shipping) {
        this.shipping = shipping;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Order toDto(Float price) {
        return new Order(id, user.getId(), status.getName(), paymentType.getName(), shipping.getId(), shippingAddress.getId(), billingAddress == null ? null : billingAddress.getId(), discount, price);
    }
}
