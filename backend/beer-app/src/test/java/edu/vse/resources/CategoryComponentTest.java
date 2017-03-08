package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class CategoryComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGetCategory() throws Exception {
        fire()
                .get()
                .to("/api/categories/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/category.json")));
    }

    @Test
    public void testListCategories() throws Exception {
        fire()
                .get()
                .to("/api/categories")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/categories.json")));
    }

    @Test
    public void testListCategoriesByMainCategory() throws Exception {
        fire()
                .get()
                .to("/api/categories?mainCategory=1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/categoriesByMainCategory.json")));
    }
}
