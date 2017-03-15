package edu.vse.daos;

import edu.vse.models.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryDao extends JpaRepository<CountryEntity, Integer> {

    CountryEntity getOne(Integer id);

    List<CountryEntity> findAll();
}
