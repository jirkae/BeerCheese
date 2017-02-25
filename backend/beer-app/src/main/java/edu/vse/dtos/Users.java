package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("users")
public class Users {

    private final List<User> items;

    public Users(List<User> items) {
        this.items = items;
    }

    public List<User> getItems() {
        return items;
    }
}
