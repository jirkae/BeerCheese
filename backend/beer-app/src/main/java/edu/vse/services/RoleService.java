package edu.vse.services;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.vse.daos.RoleDao;
import edu.vse.dtos.Role;
import edu.vse.dtos.Roles;
import edu.vse.models.RoleEntity;

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Optional<Role> getRole(int id) {
        RoleEntity roleEntity = roleDao.getOne(id);
        if (nonNull(roleEntity)) {
            return Optional.of(roleEntity).map(RoleEntity::toDto);
        } else {
            return Optional.empty();
        }
    }

    public Roles listRoles() {
        List<Role> roles = roleDao.findAll()
                .stream()
                .map(RoleEntity::toDto)
                .collect(toList());
        return new Roles(roles);
    }

    public Roles listUsersRoles(int id) {
        List<Role> roles = roleDao.findUsersRoles(id)
                .stream()
                .map(RoleEntity::toDto)
                .collect(toList());
        return new Roles(roles);
    }
}
