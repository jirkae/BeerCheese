package edu.vse.daos;

import edu.vse.models.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierDao extends JpaRepository<SupplierEntity, Integer> {

    Optional<SupplierEntity> findById(Integer id);

    List<SupplierEntity> findAll();
}
