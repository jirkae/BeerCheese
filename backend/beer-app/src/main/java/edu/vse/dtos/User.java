package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static edu.vse.utils.UriConstants.user;
import static edu.vse.utils.UriConstants.userAddresses;
import static edu.vse.utils.UriConstants.userOrders;
import static edu.vse.utils.UriConstants.userRoles;
import static org.springframework.util.Assert.notNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("user")
public class User {

    private final Integer id;
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final Date birthday;
    private final Links links;

    @JsonCreator
    public User(@JsonProperty("id") Integer id, @JsonProperty("login") String login,
                @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName,
                @JsonProperty("email") String email, @JsonProperty("phoneNumber") String phoneNumber,
                @JsonProperty("birthDay") Date birthday) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.links = new Links(id);
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Links getLinks() {
        return links;
    }

    @JsonFormat(shape = STRING, pattern = "dd/MM/yyyy", timezone="Europe/Prague")
    public Date getBirthday() {
        return birthday;
    }

    public static class Links {
        private final String self;
        private final String orders;
        private final String addresses;
        private final String roles;

        public Links(Integer id) {
            notNull(id, "Id is mandatory.");

            this.self = user.expand(id).toString();
            this.orders = userOrders.expand(id).toString();
            this.addresses = userAddresses.expand(id).toString();
            this.roles = userRoles.expand(id).toString();
        }

        public String getSelf() {
            return self;
        }

        public String getOrders() {
            return orders;
        }

        public String getAddresses() {
            return addresses;
        }

        public String getRoles() {
            return roles;
        }
    }
}
