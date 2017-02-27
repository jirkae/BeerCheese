package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.product;
import static org.springframework.util.Assert.notNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.vse.utils.UriConstants;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("product")
public class Product {

    private final Integer id;
    private final String name;
    private final Float price;
    private final Integer quantity;
    private final Float priceAfterDiscount;
    private final boolean active;
    private final String supplier;
    private final String image;
    private final Links links;

    public Product(Integer id, String name, Float price, Integer quantity, Float priceAfterDiscount, boolean active, String image, Integer supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.priceAfterDiscount = priceAfterDiscount;
        this.active = active;
        this.supplier = UriConstants.supplier.expand(supplier).toString();
        this.image = image;
        this.links = new Links(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public boolean isActive() {
        return active;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getImage() {
        return image;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id);

            this.self = product.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }

    }
}
