package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.CategoryEntity;

public interface CategoryDao extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity getOne(Integer id);

    List<CategoryEntity> findAll();

    List<CategoryEntity> findByMainCategory_Id(int id);
}
