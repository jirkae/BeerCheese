package edu.vse.schedulers;

import edu.vse.AbstractAppMvcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenGarbageCollectorComponentTest extends AbstractAppMvcTest {

    @Autowired
    private TokenGarbageCollector tokenGarbageCollector;

    @Test
    public void testGC() throws Exception {
        tokenGarbageCollector.garbageCollectOldTokens();
    }
}
