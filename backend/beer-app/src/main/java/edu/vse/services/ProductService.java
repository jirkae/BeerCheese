package edu.vse.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.vse.daos.ProductDao;
import edu.vse.dtos.Product;
import edu.vse.dtos.Products;
import edu.vse.models.ProductEntity;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Cacheable(value = "/products/", key = "#p0")
    public Optional<Product> get(int id) {
        try {
            return Optional.of( productDao.getOne(id)).map(ProductEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=product-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/products/")
    public Products list() {
        List<Product> collect = productDao.findAll().stream().map(ProductEntity::toDto).collect(toList());
        return new Products(collect);
    }

    @Cacheable(value = "/products/", key = "'?supplier='.concat(#p0)")
    public Products listBySupplier(int supplier) {
        List<Product> collect = productDao.findBySupplier(supplier).stream().map(ProductEntity::toDto).collect(toList());
        return new Products(collect);
    }
}
