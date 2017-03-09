package edu.vse.hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Bean that checks and creates temp folder if necessary for storing images upon application startup.
 */
@Component
public class ImageLocationHook {

    private static final Logger log = LoggerFactory.getLogger(ImageLocationHook.class);

    private final String imageLocationPath;

    @Autowired
    public ImageLocationHook(@Value("${images.location.path:#{null}}") String imageLocationPath) throws IOException {

        if (imageLocationPath != null) {
            this.imageLocationPath = imageLocationPath;
            checkThatImageLocationPathExists();
        } else {
            Path tempDirectory = Files.createTempDirectory("beer");
            // shutdown hook for cleanup
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    // folder has only one level structure
                    for (File file: tempDirectory.toFile().listFiles()) {
                        file.delete();
                    }
                    Files.delete(tempDirectory);
                } catch (IOException e) {
                    log.error("action=temp-folder-deletion-failed", e);
                }
            }));
            this.imageLocationPath = tempDirectory.toString();
            checkThatImageLocationPathExists();
        }
    }

    /**
     * Check that location exists and if not then throws exception
     * @throws RuntimeException
     */
    private void checkThatImageLocationPathExists() {
        boolean exists = new File(imageLocationPath).exists();
        if (!exists) {
            throw new RuntimeException("Image location doesn't exist");
        }
    }

    public String getImageLocationPath() {
        return imageLocationPath;
    }
}
