package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("categories")
public class Categories {

    public final List<Category> items;

    public Categories(List<Category> items) {
        this.items = items;
    }

    public List<Category> getItems() {
        return items;
    }
}
