package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.Address;
import edu.vse.dtos.Addresses;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.AddressService;

@RestController
@RequestMapping(value = "/api/addresses")
public class AddressResource {

    private final AddressService addressService;

    @Autowired
    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Address getAddress(@PathVariable int id) {
        return addressService.getAddress(id)
                .orElseThrow(() -> new NotFoundException("Address not found"));
    }

    @RequestMapping(method = GET)
    public Addresses listAddresses(@RequestParam int user) {
        return addressService.getUsersAdresses(user);
    }
}
