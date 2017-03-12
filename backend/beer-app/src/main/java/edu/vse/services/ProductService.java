package edu.vse.services;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.vse.daos.CategoryDao;
import edu.vse.daos.ProductDao;
import edu.vse.daos.SupplierDao;
import edu.vse.dtos.Product;
import edu.vse.dtos.Products;
import edu.vse.exceptions.NotFoundException;
import edu.vse.models.CategoryEntity;
import edu.vse.models.ProductEntity;
import edu.vse.utils.UriConstants;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final SupplierDao supplierDao;

    @Autowired
    public ProductService(ProductDao productDao, CategoryDao categoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.supplierDao = supplierDao;
    }

    @Cacheable(value = "/products/", key = "#p0")
    public Optional<Product> get(int id) {
        try {
            return Optional.of(productDao.getOne(id)).map(ProductEntity::toDto);
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

    @CacheEvict(value = "/products/")
    public Product save(Product product) {
        ProductEntity productEntity = productDao.saveAndFlush(fromDto(product, true));
        return productEntity.toDto();
    }

    @CacheEvict(value = "/products/")
    public Product saveWithId(Product product, int id) {
        ProductEntity oldProductEntity = productDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        ProductEntity detached = fromDto(product, false);
        detached.setId(id);
        detached.setImage(oldProductEntity.getImage());
        ProductEntity productEntity = productDao.saveAndFlush(detached);
        return productEntity.toDto();
    }

    private ProductEntity fromDto(Product product, boolean generateImageUUID) {
        notNull(product);
        notNull(product.getCategory());
        notNull(product.getName());
        notNull(product.getPrice());
        notNull(product.getQuantity());
        notNull(product.getPriceAfterDiscount());
        notNull(product.getSupplier());
        notNull(product.getDescription());
        isTrue(UriConstants.category.matches(product.getCategory()));
        isTrue(UriConstants.supplier.matches(product.getSupplier()));

        int category = Integer.parseInt(UriConstants.category.match(product.getCategory()).get("id"));
        CategoryEntity categoryEntity = categoryDao.findById(category)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        int supplier = Integer.parseInt(UriConstants.supplier.match(product.getSupplier()).get("id"));
        supplierDao.findById(supplier)
                .orElseThrow(() -> new NotFoundException("Supplier not found"));
        return new ProductEntity(
                categoryEntity,
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getPriceAfterDiscount(),
                true,
                generateImageUUID ? UUID.randomUUID().toString() : null,
                supplier,
                product.getDescription()
        );
    }
}
