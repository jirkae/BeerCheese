package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class UserComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGetUser() throws Exception {
        fire()
                .get()
                .to("/api/users/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/user.json")));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        fire()
                .get()
                .to("/api/users/2")
                .expectResponse()
                .havingStatusEqualTo(404)
                .havingBody(jsonEquals(getResourceAsString("json/user_not_found.json")));
    }

    @Test
    public void testListUsers() throws Exception {
        fire()
                .get()
                .to("/api/users")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/users.json")));
    }
}
