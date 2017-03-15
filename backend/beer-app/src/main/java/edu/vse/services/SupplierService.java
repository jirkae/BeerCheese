package edu.vse.services;

import edu.vse.daos.SupplierDao;
import edu.vse.dtos.Supplier;
import edu.vse.dtos.Suppliers;
import edu.vse.models.SupplierEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SupplierService {

    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);

    private final SupplierDao supplierDao;

    @Autowired
    public SupplierService(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Cacheable(value = "/suppliers/", key = "#p0")
    public Optional<Supplier> get(int id) {
        try {
            return Optional.of(supplierDao.getOne(id)).map(SupplierEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=supplier-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/suppliers/")
    public Suppliers list() {
        List<Supplier> collect = supplierDao.findAll().stream().map(SupplierEntity::toDto).collect(toList());
        return new Suppliers(collect);
    }
}
