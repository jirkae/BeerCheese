package edu.vse.daos;

import edu.vse.models.ProductPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPackageDao extends JpaRepository<ProductPackageEntity, Integer> {

    List<ProductPackageEntity> findByPackageEntity_IdIn(List<Integer> ids);
}
