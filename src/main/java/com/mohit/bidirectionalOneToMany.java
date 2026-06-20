package com.mohit;

import com.mohit.entity.Employee2;
import com.mohit.entity.Department2;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class bidirectionalOneToMany {

    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory(Department2.class, Employee2.class);

        // --- SAVE ---
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Department2 dept = new Department2("IT");
        dept.addEmployee(new Employee2("Mohit", "Developer"));
        dept.addEmployee(new Employee2("Rahul", "Tester"));

        session.persist(dept);   // single INSERT per employee, dept_id set immediately — no extra UPDATE

        tx.commit();
        session.close();

//        // --- FETCH ---
//        Session session2 = sf.openSession();
//
//        // From Department side
//        Department2 d = session2.find(Department2.class, 1);
//        System.out.println(d.getEmployees());            // ✅ list of employees
//
//        // From Employee side
//        Employee2 e = session2.find(Employee2.class, 1);
//        System.out.println(e.getDepartment().getName());  // ✅ "IT"
//
//        session2.close();
    }
}
