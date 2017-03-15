package edu.vse.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.vse.utils.UriConstants;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static org.springframework.util.Assert.notNull;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("category")
@JsonInclude(NON_NULL)
public class Category {

    private final Integer id;
    private final String name;
    private final String mainCategory;
    private final Links links;

    public Category(Integer id, String name, Integer mainCategory) {
        this.id = id;
        this.name = name;
        if (Objects.nonNull(mainCategory)) {
            this.mainCategory = UriConstants.category.expand(mainCategory).toString();
        } else {
            this.mainCategory = null;
        }
        this.links = new Links(id, mainCategory);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public Links getLinks() {
        return links;
    }

    @JsonInclude(NON_NULL)
    public static class Links {
        private final String self;
        private final String subCategories;

        public Links(Integer id, Integer mainCategory) {
            notNull(id, "Id is mandatory.");

            this.self = UriConstants.category.expand(id).toString();
            if (Objects.nonNull(mainCategory)) {
                this.subCategories = null;
            } else {
                this.subCategories = UriConstants.subCategories.expand(id).toString();
            }
        }

        public String getSelf() {
            return self;
        }

        public String getSubCategories() {
            return subCategories;
        }
    }
}
