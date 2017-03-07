package edu.vse.services;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.vse.daos.UserDao;
import edu.vse.dtos.User;
import edu.vse.dtos.Users;
import edu.vse.models.UserEntity;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUser(int id) {
        try {
            return Optional.of(userDao.getOne(id)).map(UserEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=user-not-found id={}", id);
            return Optional.empty();
        }
    }

    public Users listUsers() {
        List<User> users = userDao.findAll()
                .stream()
                .map(UserEntity::toDto)
                .collect(toList());
        return new Users(users);
    }
}
