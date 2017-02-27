package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("wrappings")
public class Wrappings {

    private final List<Wrapping> items;

    public Wrappings(List<Wrapping> items) {
        this.items = items;
    }

    public List<Wrapping> getItems() {
        return items;
    }
}
