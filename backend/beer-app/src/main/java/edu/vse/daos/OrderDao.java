package edu.vse.daos;

import edu.vse.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDao extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> getByIdAndUser_Id(int id, int user);
}
