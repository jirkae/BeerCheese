package edu.vse;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.Integer.valueOf;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.ResourceUtils;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import io.jsonwebtoken.Jwts;
import net.javacrumbs.restfire.RequestBuilder;
import net.javacrumbs.restfire.RequestFactory;
import net.javacrumbs.restfire.RequestProcessor;
import net.javacrumbs.restfire.httpcomponents.HttpComponentsRequestFactory;

@TestPropertySource(locations = "classpath:test.properties")
public abstract class AbstractAppComponentTest {

    private static DB db;

    @LocalServerPort
    private String port;

    @Value("${security.jwt.secret.access}")
    private String accessJwtSecret;

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    static {
        try {
            db = DB.newEmbeddedDB(33060);
            db.start();
            db.run("CREATE SCHEMA `beer` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci");
        } catch (ManagedProcessException e) {
            fail(e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    db.stop();
                } catch (ManagedProcessException e) {
                    fail(e.getMessage());
                }
            }
        });
    }

    protected RequestFactory fire() {
        return new HttpComponentsRequestFactory(httpClient, new RequestProcessor() {
            @Override
            public void processRequest(RequestBuilder requestBuilder) {
                requestBuilder.withPort(valueOf(port));
                requestBuilder.withHeader("content-type", "application/json");
            }
        });
    }

    protected RequestFactory fireAsAdmin() {
        String token = generateToken("dummy", "admin");

        return fireAuthenticated(token);
    }

    protected RequestFactory fireAsUser() {
        String token = generateToken("user", "user");

        return fireAuthenticated(token);
    }

    private RequestFactory fireAuthenticated(String token) {
        return new HttpComponentsRequestFactory(httpClient, new RequestProcessor() {
            @Override
            public void processRequest(RequestBuilder requestBuilder) {
                requestBuilder.withPort(valueOf(port));
                requestBuilder.withHeader("content-type", "application/json");
                requestBuilder.withHeader("X-Auth", token);
            }
        });
    }

    private String generateToken(String username, String roles) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(5, Calendar.MINUTE);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("X-Roles", roles);
        claims.put("X-User", 1);
        claims.put("X-Username", username);
        claims.put("X-Expiration", calendar.getTime());

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .signWith(HS512, accessJwtSecret)
                .compact();
    }


    protected String getResourceAsString(final String fullPathOnClassPath) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + fullPathOnClassPath), "UTF8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
