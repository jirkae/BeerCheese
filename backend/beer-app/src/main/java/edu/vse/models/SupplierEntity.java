package edu.vse.models;

import edu.vse.dtos.Supplier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private String name;

    private String phoneNumber;

    private Long deliveryTime;

    public SupplierEntity() {
    }

    public SupplierEntity(String name, String phoneNumber, Long deliveryTime) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.deliveryTime = deliveryTime;
    }

    public SupplierEntity(Integer id, String name, String phoneNumber, Long deliveryTime) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.deliveryTime = deliveryTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Supplier toDto() {
        return new Supplier(id, name, phoneNumber, deliveryTime);
    }
}
