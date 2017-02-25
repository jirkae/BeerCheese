package edu.vse.resources;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;

import org.junit.Test;

import edu.vse.AbstractAppMvcTest;
import net.javacrumbs.jsonunit.JsonMatchers;

public class CountryComponentTest extends AbstractAppMvcTest {

    @Test
    public void testGet() throws Exception {
        fire()
                .get()
                .to("/api/countries/12")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/country.json")));
    }

    @Test
    public void testListAll() throws Exception {
        fire()
                .get()
                .to("/api/countries")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBody(jsonEquals(getResourceAsString("json/countries.json")));
    }
}
