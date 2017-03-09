package edu.vse.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.vse.daos.ProductDao;
import edu.vse.exceptions.ImageConflictException;
import edu.vse.exceptions.InternalServerErrorException;
import edu.vse.hooks.ImageLocationHook;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageLocationHook imageLocationHook;
    private final ImageResolver imageResolver;
    private final ProductDao productDao;

    @Autowired
    public ImageService(ImageLocationHook imageLocationHook, ImageResolver imageResolver, ProductDao productDao) {
        this.imageLocationHook = imageLocationHook;
        this.imageResolver = imageResolver;
        this.productDao = productDao;
    }

    public Optional<InputStream> getFile(String fileName) {
        String imagePath = String.format("%s/%s", imageLocationHook.getImageLocationPath(), fileName);

        try {
            byte[] image = imageResolver.getImage(imagePath);
            ByteArrayInputStream bufferedInputStream = new ByteArrayInputStream(image);
            return Optional.of(bufferedInputStream);
        } catch (FileNotFoundException e) {
            log.info("action=file-not-found filename={} path={}", fileName, imagePath);
            return Optional.empty();
        } catch (IOException e) {
            log.info("action=io-error filename={} path={}", fileName, imagePath);
            return Optional.empty();
        }
    }

    public void saveFile(String fileName, MultipartFile multipartFile) {
        boolean exists = productDao.imageUriExists(fileName);
        if (!exists) {
            throw new ImageConflictException("Filename is not unique or does not exist");
        }

        String imagePath = String.format("%s/%s", imageLocationHook.getImageLocationPath(), fileName);

        try {
            multipartFile.transferTo(new File(imagePath));
            imageResolver.invalidateImage(imagePath);
        } catch (IOException e) {
            log.info("action=io-error filename={} path={}", fileName, imagePath);
            throw new InternalServerErrorException("Error while IO");
        }
    }
}
