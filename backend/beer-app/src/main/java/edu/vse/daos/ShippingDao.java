package edu.vse.daos;

import edu.vse.models.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingDao extends JpaRepository<ShippingEntity, Integer> {
}
