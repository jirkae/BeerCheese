package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.shipping;
import static org.springframework.util.Assert.notNull;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("shipping")
public class Shipping {

    private final Integer id;
    private final String name;
    private final Float price;
    private final Float deliveryTime;
    private final Links links;

    public Shipping(Integer id, String name, Float price, Float deliveryTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.deliveryTime = deliveryTime;
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

    public Float getDeliveryTime() {
        return deliveryTime;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = shipping.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }
    }
}
