package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class SupplierComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGet() throws Exception {
        fire()
                .get()
                .to("/api/suppliers/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/supplier.json")));
    }

    @Test
    public void testList() throws Exception {
        fire()
                .get()
                .to("/api/suppliers")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/suppliers.json")));
    }

    @Test
    public void testCreate() throws Exception {
        fireAsAdmin()
                .post()
                .withBody(getResourceAsString("json/createSupplierRequest.json"))
                .to("/api/suppliers")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/createSupplierResponse.json")));
    }

    @Test
    public void testUpdate() throws Exception {
        fireAsAdmin()
                .put()
                .withBody(getResourceAsString("json/createSupplierRequest.json"))
                .to("/api/suppliers/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/supplier.json")));
    }
}
