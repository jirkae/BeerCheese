package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.vse.utils.UriConstants;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.supplier;
import static org.springframework.util.Assert.notNull;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("supplier")
public class Supplier {

    private final Integer id;
    private final String name;
    private final String phoneNumber;
    private final Long deliveryTime;
    private final Links links;

    public Supplier(Integer id, String name, String phoneNumber, Long deliveryTime) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.deliveryTime = deliveryTime;
        this.links = new Links(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private final String self;
        private final String suppliersProducts;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = supplier.expand(id).toString();
            this.suppliersProducts = UriConstants.suppliersProducts.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }

        public String getSuppliersProducts() {
            return suppliersProducts;
        }
    }
}
