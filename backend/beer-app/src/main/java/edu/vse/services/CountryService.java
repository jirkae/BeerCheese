package edu.vse.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.vse.daos.CountryDao;
import edu.vse.dtos.Countries;
import edu.vse.dtos.Country;
import edu.vse.models.CountryEntity;

@Service
public class CountryService {

    private static final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryDao countryDao;

    @Autowired
    public CountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Cacheable(value = "/countries/", key = "#p0")
    public Optional<Country> get(int id) {
        try {
            return Optional.of(countryDao.getOne(id)).map(CountryEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=country-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/countries/")
    public Countries listAll() {
        List<Country> collect = countryDao.findAll().stream().map(CountryEntity::toDto).collect(toList());
        return new Countries(collect);
    }
}
