package edu.vse.dtos;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@JsonTypeName("registration")
public class Registration {
    private final String login;
    private final String password;
    private final String verifyPassword;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String birthday;

    @JsonCreator
    public Registration(@JsonProperty("login") String login,
                        @JsonProperty("password") String password,
                        @JsonProperty("verifyPassword") String verifyPassword,
                        @JsonProperty("firstName") String firstName,
                        @JsonProperty("lastName") String lastName,
                        @JsonProperty("email") String email,
                        @JsonProperty("phoneNumber") String phoneNumber,
                        @JsonProperty("birthday") String birthday) {
        this.login = login;
        this.password = password;
        this.verifyPassword = verifyPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
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

    public String getBirthday() {
        return birthday;
    }
}
