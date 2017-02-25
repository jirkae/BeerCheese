package edu.vse.resources;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.vse.AbstractAppComponentTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
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
