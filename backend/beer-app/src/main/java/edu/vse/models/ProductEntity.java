package edu.vse.models;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import edu.vse.dtos.Product;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private String name;

    private Float price;

    private Integer quantity;

    private Float priceAfterDiscount;

    private boolean active;

    private String image;

    private Integer supplier;

    public ProductEntity() {
    }

    public ProductEntity(String name, Float price, Integer quantity, Float priceAfterDiscount, boolean active, String image, Integer supplier) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.priceAfterDiscount = priceAfterDiscount;
        this.active = active;
        this.image = image;
        this.supplier = supplier;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(Float priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Product toDto() {
        return new Product(id, name, price, quantity, priceAfterDiscount, active, image, supplier);
    }
}
