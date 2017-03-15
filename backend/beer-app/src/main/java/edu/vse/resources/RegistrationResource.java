package edu.vse.resources;

import edu.vse.daos.RoleDao;
import edu.vse.daos.UserDao;
import edu.vse.dtos.Registration;
import edu.vse.dtos.User;
import edu.vse.exceptions.InternalServerErrorException;
import edu.vse.models.UserEntity;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api/registrations")
public class RegistrationResource {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationResource(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(method = POST)
    public User post(@RequestBody Registration registration) {
        checkRegistration(registration);

        userDao.findByLogin(registration.getLogin()).ifPresent(u -> {
            throw new InternalServerErrorException();
        });

        UserEntity userEntity = userDao.saveAndFlush(toEntity(registration));

        return userEntity.toDto();
    }

    private void checkRegistration(Registration registration) {
        notNull(registration,"Registration is mandatory.");
        notNull(registration.getLogin(), "Login is mandatory.");
        notNull(registration.getPassword(), "Product is mandatory.");
        notNull(registration.getVerifyPassword(), "Verify passord is mandatory.");
        notNull(registration.getFirstName(), "First name is mandatory.");
        notNull(registration.getLastName(), "Last name is mandatory.");
        notNull(registration.getBirthday(), "Birthday is mandatory.");
        notNull(registration.getEmail(),"Email is mandatory.");
        notNull(registration.getPhoneNumber(),"Phone number is mandatory.");
        isTrue(registration.getVerifyPassword().equals(registration.getPassword()),"Passwords must equal.");
        isTrue(EMAIL_PATTERN.matcher(registration.getEmail()).matches(),"Invalid email format.");
        try {
            DateUtils.parseDate(registration.getBirthday(), "dd/MM/yyyy");
        } catch (ParseException e) {
            isTrue(false, "Invalid date format.");
        }
    }

    private UserEntity toEntity(Registration registration) {
        Date date = null;
        try {
            date = DateUtils.parseDate(registration.getBirthday(), "dd/MM/yyyy");
        } catch (ParseException e) {
            isTrue(false, "Invalid date format.");
        }
        return new UserEntity(
                registration.getLogin(),
                passwordEncoder.encode(registration.getPassword()),
                registration.getFirstName(),
                registration.getLastName(),
                registration.getEmail(),
                registration.getPhoneNumber(),
                date,
                Arrays.asList(roleDao.findByName("user"))
        );
    }
}
