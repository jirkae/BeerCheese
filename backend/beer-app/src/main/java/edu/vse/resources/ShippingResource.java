package edu.vse.resources;

import edu.vse.dtos.Shipping;
import edu.vse.dtos.Shippings;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/shippings")
public class ShippingResource {

    private final ShippingService shippingService;

    @Autowired
    public ShippingResource(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Shipping get(@PathVariable int id) {
        return shippingService.get(id)
                .orElseThrow(() -> new NotFoundException());
    }

    @RequestMapping(method = GET)
    public Shippings list() {
        return shippingService.list();
    }
}
