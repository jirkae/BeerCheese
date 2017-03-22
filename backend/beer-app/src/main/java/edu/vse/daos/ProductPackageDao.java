package edu.vse.daos;

import edu.vse.models.ProductPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPackageDao extends JpaRepository<ProductPackageEntity, Integer> {
}
