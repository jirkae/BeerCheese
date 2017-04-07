package edu.vse.models;

import edu.vse.dtos.Package;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "`product_package`")
public class ProductPackageEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "`product`")
    private ProductEntity product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "`package`")
    private PackageEntity packageEntity;

    private Integer quantity;

    private Float price;

    private String message;

    public ProductPackageEntity() {
    }

    public ProductPackageEntity(ProductEntity product, PackageEntity packageEntity, Integer quantity, Float price, String message) {
        this.product = product;
        this.packageEntity = packageEntity;
        this.quantity = quantity;
        this.price = price;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public PackageEntity getPackageEntity() {
        return packageEntity;
    }

    public void setPackageEntity(PackageEntity packageEntity) {
        this.packageEntity = packageEntity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Package.EmbeddedProduct toDto() {
        return new Package.EmbeddedProduct(id, quantity, message, price);
    }
}
