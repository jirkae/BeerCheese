package edu.vse.resources;

import org.junit.Test;

import edu.vse.AbstractAppComponentTest;

public class StaticContentComponentTest extends AbstractAppComponentTest {

    public static final String INDEX =
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "</head>\n" +
            "<body>\n" +
            "Hello world!\n" +
            "</body>\n" +
            "</html>";

    @Test
    public void testGetIndex() throws Exception {
        fire()
                .get()
                .to("/")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBodyEqualTo(INDEX);
    }
}
