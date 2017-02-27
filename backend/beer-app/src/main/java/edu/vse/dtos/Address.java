package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.address;
import static java.util.Objects.nonNull;
import static org.springframework.util.Assert.notNull;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import edu.vse.utils.UriConstants;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("address")
@JsonInclude(NON_NULL)
public class Address {

    private final Integer id;
    private final String street;
    private final String city;
    private final String country;
    private final String note;
    private final String user;
    private final Links links;

    public Address(Integer id, String street, String city, Integer country, String note) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = UriConstants.country.expand(country).toString();
        this.note = note;
        this.user = null;
        this.links = new Links(id, null);
    }

    public Address(Integer id, String street, String city, Integer country, String note, Integer user) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = UriConstants.country.expand(country).toString();
        this.note = note;
        this.user = nonNull(user) ? UriConstants.user.expand(id).toString() : null;
        this.links = new Links(id, user);
    }

    public Integer getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getNote() {
        return note;
    }

    public String getUser() {
        return user;
    }

    public Links getLinks() {
        return links;
    }

    @JsonInclude(NON_NULL)
    public static class Links {
        private final String self;
        private final String userAddresses;

        public Links(Integer id, Integer user) {
            notNull(id);

            this.self = address.expand(id).toString();
            if (nonNull(user)) {
                this.userAddresses = UriConstants.userAddresses.expand(id).toString();
            } else {
                this.userAddresses = null;
            }
        }

        public String getSelf() {
            return self;
        }

        public String getUserAddresses() {
            return userAddresses;
        }
    }
}
