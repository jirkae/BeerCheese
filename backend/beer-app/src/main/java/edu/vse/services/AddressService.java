package edu.vse.services;

import edu.vse.daos.AddressDao;
import edu.vse.dtos.Address;
import edu.vse.models.AddressEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressDao addressDao;

    @Autowired
    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public Optional<Address> getAddress(int id) {
        try {
            return Optional.of(addressDao.getOne(id)).map(AddressEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=address-not-found id={}", id);
            return Optional.empty();
        }
    }
}
