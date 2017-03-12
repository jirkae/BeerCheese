package edu.vse.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.vse.daos.AddressDao;
import edu.vse.dtos.Address;
import edu.vse.dtos.Addresses;
import edu.vse.models.AddressEntity;

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

    public Optional<Address> getAddressForUser(int id, int user) {
        try {
            return addressDao.getByIdAndUser(id, user).map(AddressEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=address-not-found id={}", id);
            return Optional.empty();
        }
    }

    public Addresses getUsersAdresses(int user) {
        List<Address> collect = addressDao.findByUser(user)
                .stream()
                .map(AddressEntity::toDto)
                .collect(toList());
        return new Addresses(collect);
    }
}
