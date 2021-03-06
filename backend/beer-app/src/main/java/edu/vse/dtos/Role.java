package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.role;
import static org.springframework.util.Assert.notNull;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("role")
public class Role {

    private final Integer id;
    private final String name;
    private final Links links;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.links = new Links(id);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Links getLinks() {
        return links;
    }

    public static class Links {
        private final String self;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = role.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }
    }
}
