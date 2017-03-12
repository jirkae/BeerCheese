package edu.vse.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.CategoryEntity;

public interface CategoryDao extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findById(Integer id);

    List<CategoryEntity> findAll();

    List<CategoryEntity> findByMainCategory_Id(int id);
}
