package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class UserComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGetUser() throws Exception {
        fireAsAdmin()
                .get()
                .to("/api/users/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/user.json")));
    }

    @Test
    public void testGetUserAsDifferentUser() throws Exception {
        fireAsUser()
                .get()
                .to("/api/users/1")
                .expectResponse()
                .havingStatusEqualTo(403);
    }

    @Test
    public void testGetUserUnauthenticated() throws Exception {
        fire()
                .get()
                .to("/api/users/1")
                .expectResponse()
                .havingStatusEqualTo(403);
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        fireAsAdmin()
                .get()
                .to("/api/users/current")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/user.json")));
    }

    @Test
    public void testGetCurrentUserUnauthenticated() throws Exception {
        fire()
                .get()
                .to("/api/users/current")
                .expectResponse()
                .havingStatusEqualTo(403);
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        fireAsAdmin()
                .get()
                .to("/api/users/100")
                .expectResponse()
                .havingStatusEqualTo(404)
                .havingBody(jsonEquals(getResourceAsString("json/userNotFound.json")));
    }

    @Test
    public void testListUsers() throws Exception {
        fireAsAdmin()
                .get()
                .to("/api/users")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/users.json")));
    }
}
