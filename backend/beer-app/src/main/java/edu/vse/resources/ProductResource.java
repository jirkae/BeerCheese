package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.Product;
import edu.vse.dtos.Products;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.ProductService;

@RestController
@RequestMapping(value = "/api/products")
public class ProductResource {

    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Product get(@PathVariable int id) {
        return productService.get(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @RequestMapping(method = GET)
    public Products list(@RequestParam(required = false) Optional<Integer> supplier) {
        if (supplier.isPresent()) {
            return productService.listBySupplier(supplier.get());
        } else {
            return productService.list();
        }
    }
}
