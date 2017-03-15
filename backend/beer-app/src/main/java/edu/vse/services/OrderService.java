package edu.vse.services;

import edu.vse.daos.OrderDao;
import edu.vse.dtos.Order;
import edu.vse.models.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Cacheable(value = "/orders/", key = "#p0")
    public Optional<Order> getOrder(int id) {
        try {
            return Optional.of(orderDao.getOne(id)).map(OrderEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=address-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/orders/", key = "#p0/#p1")
    public Optional<Order> getOrderForUser(int id, int user) {
        try {
            return orderDao.getByIdAndUser_Id(id, user).map(OrderEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=address-not-found id={}", id);
            return Optional.empty();
        }
    }
}