package edu.vse.resources;

import org.junit.Test;

import edu.vse.AbstractAppComponentTest;

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
