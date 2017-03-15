package edu.vse.daos;

import edu.vse.models.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<AddressEntity, Integer> {

}
