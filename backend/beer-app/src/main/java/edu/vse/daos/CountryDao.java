package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.CountryEntity;

public interface CountryDao extends JpaRepository<CountryEntity, Integer> {

    CountryEntity getOne(Integer id);

    List<CountryEntity> findAll();
}
