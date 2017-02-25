package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class AddressComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGetAddress() throws Exception {
        fire()
                .get()
                .to("/api/addresses/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/address.json")));
    }

    @Test
    public void testGetAddressWithoutUser() throws Exception {
        fire()
                .get()
                .to("/api/addresses/2")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/address2.json")));
    }

    @Test
    public void testListAddressesByUser() throws Exception {
        fire()
                .get()
                .to("/api/addresses?user=1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/user_addresses.json")));
    }
}
