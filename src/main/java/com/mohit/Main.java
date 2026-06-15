package com.mohit;

import com.mohit.entity.Student;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

    public static void main(String[] args) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession(); // gives session to the database

        Transaction tx =
                session.beginTransaction();

        Student student =
                new Student("Mohit", "J", "himohitj@gmail.com");

        session.persist(student);

        tx.commit();

        session.close();

        System.out.println("Saved");
    }
}


/*also try

package com.mohit;

import com.mohit.entity.Student;
import com.mohit.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {

        // --- CREATE ---
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student s = new Student("Mohit", "Sharma", "mohit@example.com");
        session.persist(s);  // Hibernate 6 uses persist() instead of save()

        tx.commit();
        session.close();
        System.out.println("Saved: " + s);

        // --- READ ---
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        Student fetched = session2.get(Student.class, 1);
        System.out.println("Fetched: " + fetched);
        session2.close();

        HibernateUtil.shutdown();
    }
}
 */