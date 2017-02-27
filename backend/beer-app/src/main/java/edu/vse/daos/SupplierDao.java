package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.SupplierEntity;

public interface SupplierDao extends JpaRepository<SupplierEntity, Integer> {

    SupplierEntity getOne(Integer id);

    List<SupplierEntity> findAll();
}
