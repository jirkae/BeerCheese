package edu.vse.services;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.vse.daos.CountryDao;
import edu.vse.dtos.Countries;
import edu.vse.dtos.Country;
import edu.vse.models.CountryEntity;

@Service
public class CountryService {

    private final CountryDao countryDao;

    @Autowired
    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Cacheable(value = "/countries/", key = "#p0")
    public Optional<Country> get(int id) {
        CountryEntity countryEntity = countryDao.getOne(id);
        if (nonNull(countryEntity)) {
            return Optional.of(countryEntity).map(CountryEntity::toDto);
        } else {
            return Optional.empty();
        }
    }

    @Cacheable(value = "/countries/")
    public Countries listAll() {
        List<Country> collect = countryDao.findAll().stream().map(CountryEntity::toDto).collect(toList());
        return new Countries(collect);
    }
}
