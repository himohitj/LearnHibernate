package com.mohit;

import com.mohit.entity.Employee;
import com.mohit.entity.Department;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class unidirectionalOneToMany {

    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory(Department.class, Employee.class);

// --- SAVE ---
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Department dept = new Department("IT");
        dept.addEmployee(new Employee("Mohit", "Developer"));
        dept.addEmployee(new Employee("Rahul", "Tester"));

        session.persist(dept);  // cascades and saves both employees too

        tx.commit();
        session.close();

// --- FETCH ---
        Session session2 = sf.openSession();
        Department d = session2.find(Department.class, 1);
        System.out.println(d.getEmployees());   // ✅ works
// employee.getDepartment()             // ❌ doesn't exist — unidirectional
        session2.close();
    }
}
