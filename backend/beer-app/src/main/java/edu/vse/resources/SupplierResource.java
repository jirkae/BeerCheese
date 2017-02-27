package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.Supplier;
import edu.vse.dtos.Suppliers;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.SupplierService;

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
}
