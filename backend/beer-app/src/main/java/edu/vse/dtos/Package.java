package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Objects.nonNull;
import static org.springframework.util.Assert.notNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.vse.utils.UriConstants;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("package")
public class Package {

    private final Integer id;
    private final Wrapping wrapping;
    private final List<EmbeddedProduct> products;
    private final Links links;

    public Package(Integer id, Wrapping wrapping, List<EmbeddedProduct> products) {
        this(id, wrapping, products, null);
    }

    public Package(Integer id, Wrapping wrapping, List<EmbeddedProduct> products, Integer order) {
        this.id = id;
        this.wrapping = wrapping;
        this.products = products;
        this.links = new Links(id, order);
    }

    public Integer getId() {
        return id;
    }

    public Wrapping getWrapping() {
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
        private final String order;

        public Links(Integer id, Integer order) {
            notNull(id);

            this.self = UriConstants._package.expand(id).toString();
            if (nonNull(order)) {
                this.order = UriConstants.order.expand(order).toString();
            } else {
                this.order = null;
            }
        }

        public String getSelf() {
            return self;
        }

        public String getOrder() {
            return order;
        }
    }

    @JsonInclude(NON_NULL)
    public static class EmbeddedProduct {
        private final String product;
        private final Integer quantitity;
        private final Float price;

        public EmbeddedProduct(Integer product, Integer quantity, Float price) {
            notNull(product);

            this.product = UriConstants.product.expand(product).toString();
            this.quantitity = quantity;
            this.price = price;
        }

        public String getProduct() {
            return product;
        }

        public Integer getQuantity() {
            return quantitity;
        }

        public Float getPrice() {
            return price;
        }
    }
}
