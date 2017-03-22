package edu.vse.daos;

import edu.vse.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressDao extends JpaRepository<AddressEntity, Integer> {

    Optional<AddressEntity> findById(int id);
}
