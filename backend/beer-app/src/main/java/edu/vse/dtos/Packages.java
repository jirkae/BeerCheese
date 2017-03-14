package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("packages")
public class Packages {

    private final List<Package> items;

    public Packages(List<Package> items) {
        this.items = items;
    }

    public List<Package> getItems() {
        return items;
    }
}
