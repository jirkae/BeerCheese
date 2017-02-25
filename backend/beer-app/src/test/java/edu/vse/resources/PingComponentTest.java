package edu.vse.resources;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.vse.AbstractAppComponentTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PingComponentTest extends AbstractAppComponentTest {

    @Test
    public void testPing() throws Exception {
        fire()
                .get()
                .to("/api/ping")
                .expectResponse()
                .havingStatusEqualTo(204);
    }
}
