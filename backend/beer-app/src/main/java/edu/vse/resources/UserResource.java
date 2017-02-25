package edu.vse.resources;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.vse.dtos.User;
import edu.vse.dtos.Users;
import edu.vse.exceptions.NotFoundException;
import edu.vse.services.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserResource {

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public User getUser(@PathVariable int id) {
        return userService.getUser(id)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    @RequestMapping(value = "/current", method = GET)
    public User getCurrentUser() {
        //TODO get id or login from security context
        return null;
    }

    //TODO maybe paging
    @RequestMapping(method = GET)
    public Users listUser() {
        return userService.listUsers();
    }
}