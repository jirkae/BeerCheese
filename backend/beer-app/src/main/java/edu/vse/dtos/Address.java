package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.vse.utils.UriConstants;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.address;
import static org.springframework.util.Assert.notNull;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("address")
public class Address {

    private final Integer id;
    private final String street;
    private final String city;
    private final String country;
    private final String note;
    private final Links links;

    public Address(Integer id, String street, String city, Integer country, String note) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = UriConstants.country.expand(country).toString();
        this.note = note;
        this.links = new Links(id);
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

    public Links getLinks() {
        return links;
    }

    @JsonInclude(NON_NULL)
    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = address.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }
    }
}
