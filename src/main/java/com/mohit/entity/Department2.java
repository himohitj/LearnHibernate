package com.mohit.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments2")
public class Department2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee2> employees = new ArrayList<>();

    public Department2() {}

    public Department2(String name) {
        this.name = name;
    }

    public void addEmployee(Employee2 e) {
        employees.add(e);
        e.setDepartment(this);   // keep both sides in sync
    }

    // getters, setters

    @Override
    public String toString() {
        return "Department{name=" + name + "}";
    }
}