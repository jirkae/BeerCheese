package edu.vse.daos;

import edu.vse.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryDao extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findById(Integer id);

    List<CategoryEntity> findAll();

    List<CategoryEntity> findByMainCategory_Id(int id);
}
