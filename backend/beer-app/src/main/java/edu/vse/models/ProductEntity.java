package edu.vse.models;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.vse.dtos.Product;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category")
    private CategoryEntity category;

    private String name;

    private Float price;

    private Integer quantity;

    private Float priceAfterDiscount;

    private boolean active;

    private String image;

    private Integer supplier;

    private String description;

    public ProductEntity() {
    }

    public ProductEntity(CategoryEntity category, String name, Float price, Integer quantity, Float priceAfterDiscount, boolean active, String image, Integer supplier, String description) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.priceAfterDiscount = priceAfterDiscount;
        this.active = active;
        this.image = image;
        this.supplier = supplier;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public Product toDto() {
        return new Product(id, category.getId(), name, price, quantity, priceAfterDiscount, active, image, supplier);
    }
}
