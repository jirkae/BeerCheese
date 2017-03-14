package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.product;
import static org.springframework.util.Assert.notNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.vse.utils.UriConstants;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("product")
public class Product {

    private final Integer id;
    private final String category;
    private final String name;
    private final Float price;
    private final Integer quantity;
    private final Float priceAfterDiscount;
    private final boolean active;
    private final String supplier;
    private final String image;
    private final String description;
    private final Links links;

    @JsonCreator
    public Product(@JsonProperty("id") Integer id,
                   @JsonProperty("category") String category,
                   @JsonProperty("name") String name,
                   @JsonProperty("price") Float price,
                   @JsonProperty("quantity") Integer quantity,
                   @JsonProperty("priceAfterDiscount") Float priceAfterDiscount,
                   @JsonProperty("active") boolean active,
                   @JsonProperty("supplier") String supplier,
                   @JsonProperty("description") String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.priceAfterDiscount = priceAfterDiscount;
        this.active = active;
        this.supplier = supplier;
        this.image = null;
        this.links = null;
        this.category = category;
        this.description = description;
    }

    public Product(Integer id, Integer category, String name, Float price, Integer quantity, Float priceAfterDiscount, boolean active, String image, Integer supplier, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.priceAfterDiscount = priceAfterDiscount;
        this.active = active;
        this.supplier = UriConstants.supplier.expand(supplier).toString();
        this.image = UriConstants.image.expand(image).toString();
        this.links = new Links(id);
        this.category = UriConstants.category.expand(category).toString();
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
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

    public String getDescription() {
        return description;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = product.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }

    }
}
