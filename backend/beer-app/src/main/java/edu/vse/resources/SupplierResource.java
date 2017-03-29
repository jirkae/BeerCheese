package edu.vse.resources;

import edu.vse.dtos.Supplier;
import edu.vse.dtos.Suppliers;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/api/suppliers")
public class SupplierResource {

    private final SupplierService supplierService;

    @Autowired
    public SupplierResource(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Supplier get(@PathVariable int id) {
        return supplierService.get(id)
                .orElseThrow(() -> new NotFoundException());
    }

    @RequestMapping(method = GET)
    public Suppliers list() {
        return supplierService.list();
    }

    @RequestMapping(method = POST)
    public Supplier post(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public Supplier update(@PathVariable int id, @RequestBody Supplier supplier) {
        return supplierService.saveWithId(supplier, id);
    }
}
