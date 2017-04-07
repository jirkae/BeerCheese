package edu.vse.resources;

import edu.vse.context.CallContext;
import edu.vse.dtos.Package;
import edu.vse.dtos.Packages;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
        if (CallContext.isAdmin()) {
            return packageService.getPackage(id)
                    .orElseThrow(() -> new NotFoundException("Package not found"));
        } else {
            return CallContext.getContext().getUserId()
                    .flatMap(user -> packageService.getPackageForUser(id, user))
                    .orElseThrow(() -> new NotFoundException("Package not found"));
        }
    }

    @RequestMapping(method = GET)
    public Packages list() {
        if (CallContext.isAdmin()) {
            return packageService.listAll();
        } else {
            return packageService.listAllForUser(CallContext.getContext().getUserId().get());
        }
    }

    @RequestMapping(method = POST)
    public Package post(@RequestBody Package _package) {
        return packageService.save(_package);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public Package post(@PathVariable int id, @RequestBody Package _package) {
        return packageService.saveWithId(_package, id);
    }
}
