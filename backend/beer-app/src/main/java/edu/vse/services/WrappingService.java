package edu.vse.services;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import edu.vse.daos.WrappingDao;
import edu.vse.dtos.Wrapping;
import edu.vse.dtos.Wrappings;
import edu.vse.models.WrappingEntity;

@Service
public class WrappingService {

    private final WrappingDao wrappingDao;

    @Autowired
    public WrappingService(WrappingDao wrappingDao) {
        this.wrappingDao = wrappingDao;
    }

    @Cacheable(value = "/wrappings/", key = "#p0")
    public Optional<Wrapping> get(int id) {
        WrappingEntity wrappingEntity = wrappingDao.getOne(id);
        if (nonNull(wrappingEntity)) {
            return Optional.of(wrappingEntity).map(WrappingEntity::toDto);
        } else {
            return Optional.empty();
        }
    }

    @Cacheable(value = "/wrappings/")
    public Wrappings list() {
        List<Wrapping> collect = wrappingDao.findAll().stream().map(WrappingEntity::toDto).collect(toList());
        return new Wrappings(collect);
    }
}
