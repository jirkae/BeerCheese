package edu.vse.resources;

import edu.vse.AbstractAppMvcTest;
import io.jsonwebtoken.Jwts;
import net.javacrumbs.restfire.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

public class AuthResourceComponentTest extends AbstractAppMvcTest {

    @Value("${security.jwt.secret.access}")
    private String accessJwtSecret;

    @Test
    public void testLogin() throws Exception {
        fire()
                .post()
                .to("/api/auth/login")
                .withBody("{\"login\":{\"username\":\"dummy\",\"password\":\"dummyEncryptedPassword\"}}")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingHeaderEqualTo(ACCESS_CONTROL_EXPOSE_HEADERS, "X-Auth")
                .havingHeader("X-Auth=", notNullValue());
    }

    @Test
    public void testLoginAndLogout() throws Exception {
        Response response = fire()
                .post()
                .to("/api/auth/login")
                .withBody("{\"login\":{\"username\":\"dummy\",\"password\":\"dummyEncryptedPassword\"}}")
                .getResponse();

        response.getValidator().havingHeaderEqualTo(ACCESS_CONTROL_EXPOSE_HEADERS, "X-Auth");
        String xAuth = response.getHeader("X-Auth");
        if (xAuth != null) {
            fire()
                    .delete()
                    .to("/api/auth/login")
                    .withHeader("X-Auth", xAuth)
                    .expectResponse()
                    .havingStatusEqualTo(200)
                    .havingHeaderEqualTo(ACCESS_CONTROL_EXPOSE_HEADERS, "X-Auth")
                    .havingHeaderEqualTo("X-Auth", "");
        } else {
            fail();
        }
    }

    @Test
    public void testLoginRefresh() throws Exception {
        Response response = fire()
                .post()
                .to("/api/auth/login")
                .withBody("{\"login\":{\"username\":\"dummy\",\"password\":\"dummyEncryptedPassword\"}}")
                .getResponse();

        String xAuth = response.getHeader("X-Auth");
        if (xAuth != null) {
            Response responseRefresh = fire()
                    .get()
                    .to("/api/auth/token")
                    .withHeader("X-Auth", xAuth)
                    .getResponse();

            responseRefresh.getValidator()
                    .havingStatusEqualTo(200)
                    .havingHeaderEqualTo(ACCESS_CONTROL_EXPOSE_HEADERS, "X-Auth")
                    .havingHeader("X-Auth", notNullValue());

            String refresh = responseRefresh.getHeader("X-Auth");

            if (refresh != null) {
                assertFalse(xAuth.equalsIgnoreCase(refresh));
            } else {
                fail();
            }
        } else {
            fail();
        }
    }

    @Test
    public void testPingAuthenticated() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, Calendar.MINUTE);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("X-Roles", "admin");
        claims.put("X-User", 1);
        claims.put("X-Username", "dummy");
        claims.put("X-Expiration", calendar.getTime());

        String token = Jwts.builder()
                .setSubject("dummy")
                .setClaims(claims)
                .signWith(HS512, accessJwtSecret)
                .compact();

        fire()
                .get()
                .to("/api/ping")
                .withHeader("X-Auth", token)
                .expectResponse()
                .havingStatusEqualTo(204);
    }

    @Test
    public void testPingExpired() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -5);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("X-Roles", "ADMIN");
        claims.put("X-User", 1);
        claims.put("X-Username", "dummy");
        claims.put("X-Expiration", calendar.getTime());

        String token = Jwts.builder()
                .setSubject("dummy")
                .setClaims(claims)
                .signWith(HS512, accessJwtSecret)
                .compact();

        fire()
                .get()
                .to("/api/ping")
                .withHeader("X-Auth", token)
                .expectResponse()
                .havingStatusEqualTo(401);
    }

    @Test
    public void testBasicAuth() throws Exception {
        String auth = "Basic " + Base64.getEncoder().encodeToString("dummy:dummyEncryptedPassword".getBytes());

        fire()
                .get()
                .withHeader("Authorization", auth)
                .to("/api/ping/secure")
                .expectResponse()
                .havingStatusEqualTo(204);
    }

    @Test
    public void testBasicAuthRoles() throws Exception {
        String auth = "Basic " + Base64.getEncoder().encodeToString("dummy:dummyEncryptedPassword".getBytes());

        fire()
                .get()
                .withHeader("Authorization", auth)
                .to("/api/users")
                .expectResponse()
                .havingStatusEqualTo(200);
    }
}
