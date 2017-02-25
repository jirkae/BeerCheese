package edu.vse.daos;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.CountryEntity;

public interface CountryDao extends JpaRepository<CountryEntity, Integer> {

    @Cacheable(value = "/countries/", key = "#id")
    CountryEntity getOne(Integer id);

    @Cacheable(value = "/countries/", key = "LIST")
    List<CountryEntity> findAll();
}
