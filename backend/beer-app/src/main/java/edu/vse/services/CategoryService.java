package edu.vse.services;

import edu.vse.daos.CategoryDao;
import edu.vse.dtos.Categories;
import edu.vse.dtos.Category;
import edu.vse.models.CategoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryDao categoryDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    @Cacheable(value = "/categories/", key = "#p0")
    public Optional<Category> getCategory(int id) {
        try {
            return Optional.of(categoryDao.getOne(id)).map(CategoryEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=category-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/categories/")
    public Categories list() {
        List<Category> collect = categoryDao.findAll().stream().map(CategoryEntity::toDto).collect(toList());
        return new Categories(collect);
    }

    @Cacheable(value = "/categories/", key = "'?mainCategory='.concat(#p0)")
    public Categories listByMainCategory(int id) {
        List<Category> collect = categoryDao.findByMainCategory_Id(id).stream().map(CategoryEntity::toDto).collect(toList());
        return new Categories(collect);
    }

    @Cacheable(value = "/categories/", key = "'?isMainCategory='.concat(#p0)")
    public Categories listByTypeOfCategory(boolean isMainCategory) {
        Stream<CategoryEntity> collect;
        if (isMainCategory) {
            collect = categoryDao.findByMainCategoryIsNull().stream();
        } else {
            collect = categoryDao.findByMainCategoryIsNotNull().stream();
        }
        return new Categories(collect.map(CategoryEntity::toDto).collect(toList()));
    }
}
