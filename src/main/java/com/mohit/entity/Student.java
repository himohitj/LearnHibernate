package com.mohit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")   // FK column created in students table
    private Address address;

    public Student() {}

    public Student(String firstName, String email, Address address) {
        this.firstName = firstName;
        this.email = email;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
    // getters, setters

    @Override
    public String toString() {
        return "Student{name=" + firstName + ", address=" + address + "}";
    }
}