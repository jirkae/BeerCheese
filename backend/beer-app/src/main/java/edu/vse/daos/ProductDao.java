package edu.vse.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.vse.models.ProductEntity;

public interface ProductDao extends JpaRepository<ProductEntity, Integer> {

    Optional<ProductEntity> findById(int id);

    ProductEntity getOne(Integer id);

    List<ProductEntity> findAll();

    List<ProductEntity> findBySupplier(Integer supplier);

    List<ProductEntity> findByCategory_Id(Integer category);

    @Query(
            value = "SELECT CASE WHEN COUNT(p) = 1 THEN true ELSE false END FROM ProductEntity p WHERE p.image = :imageUri"
    )
    boolean imageUriExists(@Param("imageUri") String imageUri);
}
