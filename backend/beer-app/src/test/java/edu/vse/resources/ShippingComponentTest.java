package edu.vse.resources;

import edu.vse.AbstractAppMvcTest;
import org.junit.Test;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

public class ShippingComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGet() throws Exception {
        fire()
                .get()
                .to("/api/shippings/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/shipping.json")));
    }

    @Test
    public void testList() throws Exception {
        fire()
                .get()
                .to("/api/shippings")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/shippings.json")));
    }
}
