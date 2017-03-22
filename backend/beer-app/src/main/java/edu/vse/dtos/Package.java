package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.vse.utils.UriConstants;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static org.springframework.util.Assert.notNull;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("package")
public class Package {

    private final Integer id;
    private final String order;
    private final String wrapping;
    private final List<EmbeddedProduct> products;
    private final Links links;

    public Package(Integer id, Integer order, Integer wrapping, List<EmbeddedProduct> products) {
        this.id = id;
        this.order = UriConstants.order.expand(order).toString();
        this.wrapping = UriConstants.wrapping.expand(wrapping).toString();
        this.products = products;
        this.links = new Links(id);
    }

    @JsonCreator
    public Package(@JsonProperty("id") Integer id,
                   @JsonProperty("order") String order,
                   @JsonProperty("wrapping") String wrapping,
                   @JsonProperty("products") List<EmbeddedProduct> products) {
        this.id = id;
        this.order = order;
        this.wrapping = wrapping;
        this.products = products;
        this.links = new Links(id);
    }

    public Integer getId() {
        return id;
    }

    public String getOrder() {
        return order;
    }

    public String getWrapping() {
        return wrapping;
    }

    public List<EmbeddedProduct> getProducts() {
        return products;
    }

    public Links getLinks() {
        return links;
    }

    @JsonInclude(NON_NULL)
    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = UriConstants._package.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }
    }

    @JsonInclude(NON_NULL)
    public static class EmbeddedProduct {
        private final String product;
        private final Integer quantity;
        private final String message;
        private final Float price;

        public EmbeddedProduct(Integer product, Integer quantity, String message, Float price) {
            notNull(product, "Product is mandatory.");

            this.product = UriConstants.product.expand(product).toString();
            this.message = message;
            this.quantity = quantity;
            this.price = price;
        }

        @JsonCreator
        public EmbeddedProduct(@JsonProperty("product") String product,
                               @JsonProperty("quantity") Integer quantity,
                               @JsonProperty("message") String message,
                               @JsonProperty("price") Float price) {
            this.product = product;
            this.quantity = quantity;
            this.message = message;
            this.price = price;
        }

        public String getProduct() {
            return product;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getMessage() {
            return message;
        }

        public Float getPrice() {
            return price;
        }
    }
}
