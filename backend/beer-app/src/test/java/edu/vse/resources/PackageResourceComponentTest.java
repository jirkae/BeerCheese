package edu.vse.resources;

import edu.vse.AbstractAppMvcTest;
import org.junit.Test;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

public class PackageResourceComponentTest extends AbstractAppMvcTest {

    @Test
    public void testListAsUser() throws Exception {
        fireAsUser()
                .get()
                .to("/api/packages")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/packages.json")));
    }

    @Test
    public void testListAsAdmin() throws Exception {
        fireAsAdmin()
                .get()
                .to("/api/packages")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/packages.json")));
    }

    @Test
    public void testFindOneAsUserNotFound() throws Exception {
        fireAsUser()
                .get()
                .to("/api/packages/300")
                .expectResponse()
                .havingStatusEqualTo(404)
                .havingBody(jsonEquals(getResourceAsString("json/packageNotFound.json")));
    }

    @Test
    public void testFindOneAsAdmin() throws Exception {
        fireAsUser()
                .get()
                .to("/api/packages/1")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/packageAsAdmin.json")));
    }

    @Test
    public void testCreateAsUser() throws Exception {
        fireAsUser()
                .post()
                .to("/api/packages")
                .withBody(getResourceAsString("json/createPackageRequest.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/createPackageResponse.json")));
    }

    @Test
    public void testUpdateAsUser() throws Exception {
        fireAsUser()
                .put()
                .to("/api/packages/1")
                .withBody(getResourceAsString("json/updatePackageAsUserRequest.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/updatePackageAsUserResponse.json")));
    }

    @Test
    public void testUpdateAsAdmin() throws Exception {
        fireAsAdmin()
                .put()
                .to("/api/packages/1")
                .withBody(getResourceAsString("json/updatePackageAsAdminRequest.json"))
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/updatePackageAsAdminResponse.json")));
    }
}
