package edu.vse.services;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.vse.daos.SupplierDao;
import edu.vse.dtos.Supplier;
import edu.vse.dtos.Suppliers;
import edu.vse.models.SupplierEntity;

@Service
public class SupplierService {

    private final SupplierDao supplierDao;

    @Autowired
    public SupplierService(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Cacheable(value = "/suppliers/", key = "#p0")
    public Optional<Supplier> get(int id) {
        SupplierEntity supplierEntity = supplierDao.getOne(id);
        if (nonNull(supplierEntity)) {
            return Optional.of(supplierEntity).map(SupplierEntity::toDto);
        } else {
            return Optional.empty();
        }
    }

    @Cacheable(value = "/suppliers/")
    public Suppliers list() {
        List<Supplier> collect = supplierDao.findAll().stream().map(SupplierEntity::toDto).collect(toList());
        return new Suppliers(collect);
    }
}
