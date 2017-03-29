package edu.vse.resources;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.cache.CacheManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.vse.AbstractAppMvcTest;
import edu.vse.hooks.ImageLocationHook;

public class ImageResourceComponentTest extends AbstractAppMvcTest {

    @Autowired
    private ImageLocationHook imageLocationHook;

    @Autowired
    private CacheManager cacheManager;

    private String path;
    private String fileName;

    @Before
    public void setUp() throws Exception {
        fileName = "/test" + System.currentTimeMillis();
        path = imageLocationHook.getImageLocationPath() + fileName;
        Path file = Paths.get(path);
        Files.write(file, Arrays.asList("Hello World!"), Charset.forName("UTF-8"));
    }

    @After
    public void tearDown() throws Exception {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }

    @Test
    public void testGetFile() throws Exception {
        fire()
                .get()
                .to("/images" + fileName)
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingHeaderEqualTo("Content-type", "image/jpeg")
                .havingHeaderEqualTo("Cache-Control", "private, max-age=600")
                .havingBodyEqualTo("Hello World!\n");
    }

    @Test
    public void testGetFileNonExisting() throws Exception {
        fire()
                .get()
                .to("/images/trollMe")
                .expectResponse()
                .havingStatusEqualTo(404);
    }

    @Test
    public void testGetFileRepeatableRead() throws Exception {
        fire()
                .get()
                .to("/images" + fileName)
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingHeaderEqualTo("Content-type", "image/jpeg")
                .havingBodyEqualTo("Hello World!\n");

        fire()
                .get()
                .to("/images" + fileName)
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingHeaderEqualTo("Content-type", "image/jpeg")
                .havingBodyEqualTo("Hello World!\n");
    }

    @Test
    public void testUploadFile() throws Exception {
        fireAsAdmin()
                .post()
                .withHeader("Content-type", "multipart/form-data; boundary=---------------------------9051914041544843365972754266")
                .withBody("-----------------------------9051914041544843365972754266\r\n" +
                        "Content-Disposition: form-data; name=\"image\"; filename=\"a.txt\"\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "\r\n" +
                        "Content of a.txt.\r\n" +
                        "\r\n" +
                        "-----------------------------9051914041544843365972754266--\r\n")
                .to("/images/guhppkyg5c")
                .expectResponse()
                .havingStatusEqualTo(201)
                .havingHeaderEqualTo("Location", "/images/guhppkyg5c");

        fire()
                .get()
                .to("/images/guhppkyg5c")
                .expectResponse()
                .havingStatusEqualTo(200)
                .havingBodyEqualTo("Content of a.txt.\r\n");
    }

    @Test
    public void testUploadFileToNonExistingFilename() throws Exception {
        fireAsAdmin()
                .post()
                .withHeader("Content-type", "multipart/form-data; boundary=---------------------------9051914041544843365972754266")
                .withBody("-----------------------------9051914041544843365972754266\r\n" +
                        "Content-Disposition: form-data; name=\"image\"; filename=\"a.txt\"\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "\r\n" +
                        "Content of a.txt.\r\n" +
                        "\r\n" +
                        "-----------------------------9051914041544843365972754266--\r\n")
                .to("/images/yolo.jpeg")
                .expectResponse()
                .havingStatusEqualTo(409);
    }
}
