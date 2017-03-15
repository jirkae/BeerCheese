package edu.vse.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private Integer user;

    private String token;

    private Date created;

    private Date expiration;

    public TokenEntity() {
    }

    public TokenEntity(Integer user, String token) {
        this.user = user;
        this.token = token;
    }

    public TokenEntity(int user, String token, Date created, Date expiration) {
        this.user = user;
        this.token = token;
        this.created = created;
        this.expiration = expiration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
