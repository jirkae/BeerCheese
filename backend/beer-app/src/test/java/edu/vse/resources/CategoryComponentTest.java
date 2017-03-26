package edu.vse.resources;

import edu.vse.AbstractAppMvcTest;
import org.junit.Test;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

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

    @Test
    public void testListMainCategories() throws Exception {
        fire()
                .get()
                .to("/api/categories?isMainCategory=true")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/mainCategories.json")));
    }

    @Test
    public void testListSubCategories() throws Exception {
        fire()
                .get()
                .to("/api/categories?isMainCategory=false")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/subCategories.json")));
    }
}
