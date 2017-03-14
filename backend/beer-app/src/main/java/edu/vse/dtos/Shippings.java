package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("shippings")
public class Shippings {

    private final List<Shipping> items;

    public Shippings(List<Shipping> items) {
        this.items = items;
    }

    public List<Shipping> getItems() {
        return items;
    }
}
