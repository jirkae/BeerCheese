package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.ProductEntity;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {

    ProductEntity getOne(Integer id);

    List<ProductEntity> findAll();

    List<ProductEntity> findBySupplier (Integer supplier);
}
