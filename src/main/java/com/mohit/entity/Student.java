package com.mohit.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "students")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Column(name = "first_name")
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Transient
    private String aadharNumber;  // transient field: simpy means it wont be stored in DB even if it is set
    /*
     Transient is used for fields that are not required to be stored in DB
    but are required for the application to work properly
     For example, a transient field can be used to store a temporary value
    this allows selective persistence (insertion) of fields in the database
     */

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

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
        return " Student{id=" + id + ", name=" + firstName + " " + lastName + "}";
    }
}