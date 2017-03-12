package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class ProductComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGet() throws Exception {
        fire()
                .get()
                .to("/api/products/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/product.json")));
    }

    @Test
    public void testList() throws Exception {
        fire()
                .get()
                .to("/api/products")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/products.json")));
    }

    @Test
    public void testListBySupplier() throws Exception {
        fire()
                .get()
                .to("/api/products?supplier=1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/products.json")));
    }

    @Test
    public void testPostProduct() throws Exception {
        fireAsAdmin()
                .post()
                .to("/api/products")
                .withBody(getResourceAsString("json/createProduct.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/createProductResponse.json")));

        fire()
                .get()
                .to("/api/products/2")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/createProductResponse.json")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        fireAsAdmin()
                .put()
                .to("/api/products/1")
                // can reused for update
                .withBody(getResourceAsString("json/createProduct.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/updateProductResponse.json")));
    }
}
