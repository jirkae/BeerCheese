package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;

public class RoleComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGetRole() throws Exception {
        fire()
                .get()
                .to("/api/roles/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/role.json")));
    }

    @Test
    public void testListRoles() throws Exception {
        fire()
                .get()
                .to("/api/roles")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/roles.json")));
    }

    @Test
    public void testListUsersRoles() throws Exception {
        fire()
                .get()
                .to("/api/roles?user=1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/users_roles.json")));
    }
}
