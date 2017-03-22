package edu.vse.daos;

import edu.vse.models.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingDao extends JpaRepository<ShippingEntity, Integer> {

    Optional<ShippingEntity> findById(int id);
}
