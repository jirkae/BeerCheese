package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("orders")
public class Orders {
    private final List<Order> items;
    private final Paging paging;

    public Orders(List<Order> items, Paging paging) {
        this.items = items;
        this.paging = paging;
    }

    public List<Order> getItems() {
        return items;
    }

    public Paging getPaging() {
        return paging;
    }
}
