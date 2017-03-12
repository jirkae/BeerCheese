package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class RegistrationComponentTest extends AbstractAppMvcTest {

    @Test
    public void testPost() throws Exception {
        fire()
                .post()
                .to("/api/registrations")
                .withBody(getResourceAsString("json/registration.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/registrationResponse.json")));
    }
}
