package edu.vse.daos;

import edu.vse.models.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusDao extends JpaRepository<OrderStatusEntity, Integer> {

    Optional<OrderStatusEntity> findByName(String name);
}
