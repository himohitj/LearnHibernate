package com.mohit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    // Constructors - default constructor is required by JPA
    public Student() {}

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters & Setters (or use Lombok @Data)
    // toString() for printing
    @Override
    public String toString() {
        return "Student{id=" + id + ", name=" + firstName + " " + lastName + "}";
    }
}