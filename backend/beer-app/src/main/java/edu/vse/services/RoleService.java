package edu.vse.services;

import edu.vse.daos.RoleDao;
import edu.vse.dtos.Role;
import edu.vse.dtos.Roles;
import edu.vse.models.RoleEntity;
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
public class RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Cacheable(value = "/roles/", key = "#p0")
    public Optional<Role> getRole(int id) {
        try {
            return Optional.of(roleDao.getOne(id)).map(RoleEntity::toDto);
        } catch (EntityNotFoundException e) {
            log.info("action=role-not-found id={}", id);
            return Optional.empty();
        }
    }

    @Cacheable(value = "/roles/")
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
