package edu.vse.services;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.vse.daos.UserDao;
import edu.vse.dtos.User;
import edu.vse.dtos.Users;
import edu.vse.models.UserEntity;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> getUser(int id) {
        UserEntity userEntity = userDao.findOne(id);
        if (nonNull(userEntity)) {
            return Optional.of(userEntity).map(UserEntity::toDto);
        } else {
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
