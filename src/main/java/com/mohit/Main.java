package com.mohit;

import com.mohit.entity.Student;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Session session = HibernateUtil
                .getSessionFactory(Student.class)
                .openSession(); // gives session to the database

        Transaction tx =
                session.beginTransaction();


        /*----------------Example of lazy loading----------------
        Student s2 = session.getReference(Student.class, 3);
        System.out.println(s2.getId());
        System.in.read();
        System.out.println(s2.getFirstName());
        tx.commit();
        session.close();
         */

        /* --------Example of eager loading--------
        Student s1 = session.find(Student.class, 1);
        System.out.println(s1.getId());
        System.in.read();
        System.out.println(s1.getFirstName());

        tx.commit();
        session.close();

         */



        /* Level 1 cache
        Student s3 = session.find(Student.class, 1);  // this will run the query
        System.out.println(s3);
        Student s4 = session.find(Student.class, 1); // this will be fetched from the cache because the session already created the cache of the object which has id = 1
        System.out.println(s4);

        Student s5 = session.find(Student.class, 3); // this will again run the hibernate query because there is no object in the cache whose id = 3
        System.out.println(s5);
         */




    }
}
