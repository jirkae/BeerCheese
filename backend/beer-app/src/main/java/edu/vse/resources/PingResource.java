package edu.vse.resources;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/ping")
public class PingResource {

    @RequestMapping(method = GET)
    @ResponseStatus(NO_CONTENT)
    public void ping() {
    }
}
