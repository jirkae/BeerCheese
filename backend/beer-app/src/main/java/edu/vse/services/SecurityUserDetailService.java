package edu.vse.services;

import edu.vse.daos.UserDao;
import edu.vse.models.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class SecurityUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    public SecurityUserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity example = new UserEntity(username);

        UserEntity user = userDao.findOne(Example.of(example));

        if (user != null) {
            Collection<? extends GrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream()
                    .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                    .collect(toList());
            return new User(user.getLogin(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("Username not found!");
        }
    }
}
