package edu.vse.services;

import edu.vse.daos.ShippingDao;
import edu.vse.dtos.Shipping;
import edu.vse.dtos.Shippings;
import edu.vse.models.ShippingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ShippingService {

    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);

    private final ShippingDao shippingDao;

    @Autowired
    public ShippingService(ShippingDao shippingDao) {
        this.shippingDao = shippingDao;
    }

    @Cacheable(value = "/shippings/", key = "#p0")
    public Optional<Shipping> get(int id) {
        try {
            return Optional.of(shippingDao.getOne(id)).map(ShippingEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=shipping-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/shippings/")
    public Shippings list() {
        List<Shipping> collect = shippingDao.findAll().stream().map(ShippingEntity::toDto).collect(toList());
        return new Shippings(collect);
    }
}
