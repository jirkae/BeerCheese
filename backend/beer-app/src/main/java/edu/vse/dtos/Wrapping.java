package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.wrapping;
import static org.springframework.util.Assert.notNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("wrapping")
public class Wrapping {

    private final Integer id;
    private final String name;
    private final Float price;
    private final Links links;

    public Wrapping(Integer id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id);

            this.self = wrapping.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }
    }
}
