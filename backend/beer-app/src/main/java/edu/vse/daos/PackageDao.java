package edu.vse.daos;

import edu.vse.models.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageDao extends JpaRepository<PackageEntity, Integer> {
}
