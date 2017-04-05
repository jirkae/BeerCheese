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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class AuthResourceComponentTest extends AbstractAppMvcTest {

    private static final Pattern pattern = Pattern.compile("X-Auth=(.+?);");

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
                .havingHeader("Set-cookie", hasItem(
                        allOf(
                                containsString("X-Auth="),
                                containsString("Secure"),
                                containsString("HttpOnly")
                        )
                        )
                );
    }

    @Test
    public void testLoginAndLogout() throws Exception {
        Response response = fire()
                .post()
                .to("/api/auth/login")
                .withBody("{\"login\":{\"username\":\"dummy\",\"password\":\"dummyEncryptedPassword\"}}")
                .getResponse();

        String xAuthValue = response.getHeader("Set-cookie");
        Matcher matcher = pattern.matcher(xAuthValue);
        if (matcher.find()) {
            String xAuth = matcher.group(1);

            fire()
                    .delete()
                    .to("/api/auth/login")
                    .withHeader("X-Auth", xAuth)
                    .expectResponse()
                    .havingStatusEqualTo(200)
                    .havingHeaderEqualTo("Set-cookie", "X-Auth=;Max-Age=0;Secure;HttpOnly");
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

        String xAuthValue = response.getHeader("Set-cookie");
        Matcher matcher = pattern.matcher(xAuthValue);
        if (matcher.find()) {
            String xAuth = matcher.group(1);

            Response responseRefresh = fire()
                    .get()
                    .to("/api/auth/token")
                    .withHeader("X-Auth", xAuth)
                    .getResponse();

            responseRefresh.getValidator()
                    .havingStatusEqualTo(200)
                    .havingHeader("Set-cookie", hasItem(
                            allOf(
                                    containsString("X-Auth="),
                                    containsString("Secure"),
                                    containsString("HttpOnly")
                            )
                            )
                    );

            Matcher matcherRefresh = pattern.matcher(responseRefresh.getHeader("Set-cookie"));

            if (matcherRefresh.find()) {
                String xAuthRefresh = matcherRefresh.group(1);

                assertFalse(xAuth.equalsIgnoreCase(xAuthRefresh));
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
