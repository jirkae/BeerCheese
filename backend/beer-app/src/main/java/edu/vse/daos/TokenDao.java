package edu.vse.daos;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.vse.models.TokenEntity;

public interface TokenDao extends JpaRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByUserAndToken(int user, String token);

    int deleteAllByExpirationBefore(Date date);

    int deleteAllByUserAndToken(int user, String token);
}
