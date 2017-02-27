package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.Wrapping;
import edu.vse.dtos.Wrappings;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.WrappingService;

@RestController
@RequestMapping(value = "/api/wrappings")
public class WrappingResource {

    private final WrappingService wrappingService;

    @Autowired
    public WrappingResource(WrappingService wrappingService) {
        this.wrappingService = wrappingService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Wrapping get(@PathVariable int id) {
        return wrappingService.get(id)
                .orElseThrow(() -> new NotFoundException());
    }

    @RequestMapping(method = GET)
    public Wrappings list() {
        return wrappingService.list();
    }
}
