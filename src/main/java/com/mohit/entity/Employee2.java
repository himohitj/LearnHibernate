package com.mohit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employees2")
public class Employee2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String designation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_id")   // FK column, owner side
    private Department2 department;

    public Employee2() {}

    public Employee2(String name, String designation) {
        this.name = name;
        this.designation = designation;
    }

    // getters, setters
    public void setDepartment(Department2 d){
        this.department = d;
    }

    @Override
    public String toString() {
        // ⚠️ don't print department here — avoid infinite loop
        return "Employee{name=" + name + ", designation=" + designation + "}";
    }
}