package edu.vse.models.builders;

import java.util.Date;

import edu.vse.dtos.User;
import edu.vse.models.UserEntity;

public class UserBuilder {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthday;

    private UserBuilder() {

    }

    public UserBuilder id(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder birthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserEntity createEntity() {
        return new UserEntity(login, firstName, lastName, email, phoneNumber, birthday);
    }

    public User createDto() {
        return new User(id, login, firstName, lastName, email, phoneNumber, birthday);
    }
}