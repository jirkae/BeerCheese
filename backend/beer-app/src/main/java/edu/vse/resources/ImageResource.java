package edu.vse.resources;

import edu.vse.exceptions.InternalServerErrorException;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.ImageService;
import edu.vse.utils.UriConstants;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/images")
public class ImageResource {

    private static final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private final ImageService imageService;

    @Autowired
    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "/{filename}", method = GET)
    public void getImage(@PathVariable String filename, ServletResponse servletResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Optional<InputStream> inputStream = imageService.getFile(filename);
        if (inputStream.isPresent()) {
            try {
                ServletOutputStream outputStream = httpServletResponse.getOutputStream();
                IOUtils.copy(inputStream.get(), outputStream);
            } catch (IOException e) {
                log.error("action=image-io-error", e);
                throw new InternalServerErrorException("Error while IO");
            }
            httpServletResponse.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        } else {
            throw new NotFoundException("Image not found");
        }
    }

    @RequestMapping(value = "/{filename}", method = POST)
    public ResponseEntity<Void> uploadImage(@PathVariable String filename, @RequestParam("image") MultipartFile multipartFile) {
        URI location;
        try {
            location = new URI(UriConstants.image.expand(filename).toString());
        } catch (URISyntaxException e) {
            log.error("action=cannot-parse-location-uri", e);
            throw new InternalServerErrorException("Error while parsing filename");
        }
        imageService.saveFile(filename, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }
}
