package com.mohit.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_id")   // FK column created in employees table
    private List<Employee> employees = new ArrayList<>();

    public Department() {}

    public Department(String name) {
        this.name = name;
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }
    public List<Employee> getEmployees() {
        return employees;
    }

    // getters, setters

    @Override
    public String toString() {
        return "Department{name=" + name + ", employees=" + employees + "}";
    }
}