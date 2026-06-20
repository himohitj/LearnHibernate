package com.mohit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String designation;

    public Employee() {}

    public Employee(String name, String designation) {
        this.name = name;
        this.designation = designation;
    }

    // getters, setters

    @Override
    public String toString() {
        return "Employee{name=" + name + ", designation=" + designation + "}";
    }
}