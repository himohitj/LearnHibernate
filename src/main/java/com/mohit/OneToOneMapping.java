package com.mohit;

import com.mohit.entity.Student;
import com.mohit.entity.Address;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OneToOneMapping {

    public static void main(String[] args) {

        SessionFactory sf = HibernateUtil.getSessionFactory(Student.class, Address.class);

        // --- SAVE ---
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Address address = new Address("MG Road", "Mumbai", "Maharashtra");
        Student student = new Student("Mohit", "mohit@gmail.com", address);

        session.persist(student);  // saves BOTH student and address due to CascadeType.ALL

        tx.commit();
        session.close();

        // --- FETCH ---
        Session session2 = sf.openSession();
        Student s = session2.find(Student.class, 1);
        System.out.println(s);               // Student{name=Mohit, address=Address{...}}
        System.out.println(s.getAddress());  // works fine
        // s.getAddress().getStudent()       // ❌ doesn't exist — unidirectional
        session2.close();
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