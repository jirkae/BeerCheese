package edu.vse.models;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.vse.dtos.Address;

@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private String street;

    private String city;

    @ManyToOne
    @JoinColumn(name = "country", nullable = false)
    private CountryEntity country;

    private String note;

    private Integer user;

    public AddressEntity() {
    }

    public AddressEntity(String street, String city, CountryEntity country, String note) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Address toDto() {
        return new Address(id, street, city, country.getId(), note, user);
    }
}
