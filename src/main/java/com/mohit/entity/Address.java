package com.mohit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String street;
    private String city;
    private String state;

    public Address() {}

    public Address(String street, String city, String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    // getters, setters

    @Override
    public String toString() {
        return "Address{street=" + street + ", city=" + city + "}";
    }
}