package edu.vse.models;

import edu.vse.dtos.Shipping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "shipping")
public class ShippingEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private String name;

    private Float price;

    private Float deliveryTime;

    public ShippingEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Float deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Shipping toDto() {
        return new Shipping(id, name, price, deliveryTime);
    }
}
