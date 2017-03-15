package edu.vse.resources;

import edu.vse.dtos.Countries;
import edu.vse.dtos.Country;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/countries")
public class CountryResource {

    private final CountryService countryService;

    @Autowired
    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Country get(@PathVariable int id) {
        return countryService.get(id)
                .orElseThrow(() -> new NotFoundException("Country not found"));
    }

    @RequestMapping(method = GET)
    public Countries list() {
        return countryService.listAll();
    }
}