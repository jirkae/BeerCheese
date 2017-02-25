package edu.vse.services;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.vse.daos.AddressDao;
import edu.vse.dtos.Address;
import edu.vse.dtos.Addresses;
import edu.vse.models.AddressEntity;

@Service
public class AddressService {

    private final AddressDao addressDao;

    @Autowired
    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public Optional<Address> getAddress(int id) {
        AddressEntity addressEntity = addressDao.getOne(id);
        if (nonNull(addressEntity)) {
            return Optional.of(addressEntity).map(AddressEntity::toDto);
        } else {
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
