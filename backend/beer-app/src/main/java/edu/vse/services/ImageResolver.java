package edu.vse.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Extracted to own bean for caching purposes due to issues with Spring AOP.
 */
@Component
public class ImageResolver {

    @Cacheable(value = "/images/", key = "#p0")
    public byte[] getImage(String imagePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(imagePath));
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        return bytes;
    }

    @CacheEvict(value = "/images/", key = "#p0")
    public void invalidateImage(String imagePath) {

    }
}
