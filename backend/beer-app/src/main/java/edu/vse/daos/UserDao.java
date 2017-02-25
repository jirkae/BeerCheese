package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findAll();
}
