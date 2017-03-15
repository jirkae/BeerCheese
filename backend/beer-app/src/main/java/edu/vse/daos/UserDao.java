package edu.vse.daos;

import edu.vse.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findAll();

    Optional<UserEntity> findByLogin(String login);
}
