package edu.vse.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.vse.models.AddressEntity;

public interface AddressDao extends JpaRepository<AddressEntity, Integer> {

    @Query(value = "SELECT a.id as id, a.street as street, a.city as city, c.id as country, c.name as country_name, a.note as note, a.user as user " +
            "FROM address a JOIN country c ON a.country=c.id " +
            "WHERE a.user=?1",
            nativeQuery = true
    )
    List<AddressEntity> findByUser(int user);
}
