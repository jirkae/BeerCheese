package edu.vse.resources;

import edu.vse.dtos.Package;
import edu.vse.dtos.Packages;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/packages")
public class PackageResource {

    private final PackageService packageService;

    @Autowired
    public PackageResource(PackageService packageService) {
        this.packageService = packageService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Package get(@PathVariable int id) {
        return packageService.get(id)
                .orElseThrow(() -> new NotFoundException("Package not found"));
    }

    @RequestMapping(method = GET)
    public Packages list() {
        return packageService.listAll();
    }

}
