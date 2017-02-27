package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.WrappingEntity;

public interface WrappingDao extends JpaRepository<WrappingEntity, Integer> {

    WrappingEntity getOne(Integer id);

    List<WrappingEntity> findAll();
}
