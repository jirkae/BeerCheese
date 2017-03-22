package edu.vse.daos;

import edu.vse.models.PaymentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTypeDao extends JpaRepository<PaymentTypeEntity, Integer> {

    Optional<PaymentTypeEntity> findByName(String name);
}
