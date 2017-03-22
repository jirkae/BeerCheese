package edu.vse.resources;

import edu.vse.AbstractAppMvcTest;
import org.junit.Test;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

public class OrderComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGetAsAdmin() throws Exception {
        fireAsUser()
                .get()
                .to("/api/orders/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/order.json")));
    }

    @Test
    public void testGetAsUser() throws Exception {
        fireAsUser()
                .get()
                .to("/api/orders/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/order.json")));
    }

    @Test
    public void testCreateOrder() throws Exception {
        fireAsUser()
                .post()
                .to("/api/orders")
                .withBody(getResourceAsString("json/createOrderRequest.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/createOrderResponse.json")));
    }

    @Test
    public void testUpdateOrderAsUser() throws Exception {
        fireAsUser()
                .put()
                .to("/api/orders/1")
                .withBody(getResourceAsString("json/createOrderRequest.json"))
                .expectResponse()
                .havingStatusEqualTo(403);
    }

    @Test
    public void testUpdateOrderAsAdmin() throws Exception {
        fireAsAdmin()
                .put()
                .to("/api/orders/1")
                .withBody(getResourceAsString("json/createOrderRequest.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/createOrderResponse.json")));
    }
}
