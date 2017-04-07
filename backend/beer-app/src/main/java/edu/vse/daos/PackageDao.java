package edu.vse.daos;

import edu.vse.models.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackageDao extends JpaRepository<PackageEntity, Integer> {

    List<PackageEntity> findByOrder_Id(int id);

    Optional<PackageEntity> getById(int id);
}
