package com.mohit;

import com.mohit.entity.Student;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;

public class L2cachetest {

    public static void main(String[] args) throws IOException {


        SessionFactory sf = HibernateUtil.getSessionFactory(Student.class);

    // First call — hits the DATABASE
        Session s1 = sf.openSession();
        Student student1 = s1.find(Student.class, 1);
        s1.close();

    // Second call — hits the CACHE, not the DB
        Session s2 = sf.openSession();
        Student student2 = s2.find(Student.class, 1);
        s2.close();

    }
}
