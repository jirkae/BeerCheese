package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.Role;
import edu.vse.dtos.Roles;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.RoleService;

@RestController
@RequestMapping(value = "/api/roles")
public class RoleResource {

    private final RoleService roleService;

    @Autowired
    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Role getRole(@PathVariable int id) {
        return roleService.getRole(id)
                .orElseThrow(() -> new NotFoundException("Role not found."));
    }

    @RequestMapping(method = GET)
    public Roles listRoles(@RequestParam(required = false) Optional<Integer> user) {
        if (user.isPresent()) {
            return roleService.listUsersRoles(user.get());
        } else {
            return roleService.listRoles();
        }
    }
}
