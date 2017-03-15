package edu.vse.daos;

import edu.vse.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenDao extends JpaRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByUserAndToken(int user, String token);

    int deleteAllByExpirationBefore(Date date);

    int deleteAllByUserAndToken(int user, String token);
}
