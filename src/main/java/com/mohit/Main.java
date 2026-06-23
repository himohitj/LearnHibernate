package com.mohit;

import com.mohit.entity.Student;
import com.mohit.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ====================================================================
        // 1. CREATE — insert new rows
        // ====================================================================
        createStudents();

        // ====================================================================
        // 2. READ — fetch using get() and using HQL
        // ====================================================================
        readWithGet(1);          // fetch single row by primary key
        readAllWithHQL();        // fetch all rows via HQL
        readWithWhereClause();   // HQL with a WHERE filter
        readWithNamedParameter("J"); // HQL with a bind parameter

        // ====================================================================
        // 3. UPDATE — change existing rows (object-based + bulk HQL)
        // ====================================================================
        updateStudentById(1);
        bulkUpdateEmailDomain();

        // ====================================================================
        // 4. DELETE — remove rows (object-based + bulk HQL)
        // ====================================================================
        deleteStudentById(2);

        // Aggregate HQL (COUNT)
        countStudents();

        // Always release the SessionFactory at application shutdown
        HibernateUtil.shutdown();
    }

    // --------------------------------------------------------------------
    // CREATE
    // --------------------------------------------------------------------
    private static void createStudents() {
        // openSession() -> gives a single-threaded unit of work connected to the DB
        Session session = HibernateUtil.getSessionFactory().openSession();
        // A Transaction groups operations so they all succeed or all fail (ACID)
        Transaction tx = session.beginTransaction();

        Student s1 = new Student("Mohit", "J", "mohit@gmail.com");
        Student s2 = new Student("Aman", "K", "aman@gmail.com");
        Student s3 = new Student("Riya", "S", "riya@gmail.com");

        // persist() schedules the entity for INSERT (Hibernate 6/7 replaces save())
        session.persist(s1);
        session.persist(s2);
        session.persist(s3);

        tx.commit();   // commit() flushes pending SQL to the DB and ends the transaction
        session.close(); // close() releases the JDBC connection back to the pool

        System.out.println("CREATE done -> inserted 3 students");
    }

    // --------------------------------------------------------------------
    // READ #1 — get() by primary key (no HQL needed)
    // --------------------------------------------------------------------
    private static void readWithGet(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // get() loads the entity immediately by PK; returns null if not found
        Student student = session.find(Student.class, id);

        System.out.println("READ get() -> " + student);
        session.close();
    }

    // --------------------------------------------------------------------
    // READ #2 — HQL: select all students
    // --------------------------------------------------------------------
    private static void readAllWithHQL() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // NOTE: HQL uses the ENTITY name "Student" and FIELD names, NOT table/column names
        // createQuery(hql, ResultType.class) -> returns a typed Query object
        Query<Student> query =
                session.createQuery("FROM Student", Student.class);

        // getResultList() executes the SELECT and returns all matching rows
        List<Student> students = query.getResultList();

        System.out.println("READ HQL (all):");
        students.forEach(System.out::println);

        session.close();
    }

    // --------------------------------------------------------------------
    // READ #3 — HQL with WHERE clause
    // --------------------------------------------------------------------
    private static void readWithWhereClause() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // 's' is an alias for the Student entity (like SQL aliases)
        // We reference the FIELD 'firstName', not the column 'first_name'
        Query<Student> query = session.createQuery(
                "FROM Student s WHERE s.firstName = 'Mohit'", Student.class);

        List<Student> result = query.getResultList();

        System.out.println("READ HQL (WHERE firstName='Mohit'):");
        result.forEach(System.out::println);

        session.close();
    }

    // --------------------------------------------------------------------
    // READ #4 — HQL with a named bind parameter (safe from SQL injection)
    // --------------------------------------------------------------------
    private static void readWithNamedParameter(String lastName) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // ':ln' is a NAMED PARAMETER placeholder
        Query<Student> query = session.createQuery(
                "FROM Student s WHERE s.lastName = :ln", Student.class);

        // setParameter() binds a real value to the placeholder safely
        query.setParameter("ln", lastName);

        // uniqueResult() returns ONE row (or null). Throws if more than one row matches.
        List<Student> students = query.getResultList();

        System.out.println("READ HQL (param lastName='" + lastName + "'):");
        students.forEach(System.out::println);

        session.close();
    }

    // --------------------------------------------------------------------
    // UPDATE #1 — object-based: load, modify, commit (dirty checking)
    // --------------------------------------------------------------------
    private static void updateStudentById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student student = session.find(Student.class, id);
        if (student != null) {
            // Hibernate tracks ("dirty checks") changes to managed entities
            // and auto-generates an UPDATE on commit — no explicit save needed
            student.setEmail("mohit.updated@gmail.com");
        }

        tx.commit();
        session.close();

        System.out.println("UPDATE done -> id=" + id + " email changed");
    }

    // --------------------------------------------------------------------
    // UPDATE #2 — bulk update via HQL (single SQL, many rows, fast)
    // --------------------------------------------------------------------
    private static void bulkUpdateEmailDomain() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // HQL UPDATE statement. executeUpdate() returns number of affected rows.
        int updatedRows = session.createMutationQuery(
                        "UPDATE Student s SET s.lastName = :newLast WHERE s.lastName = :oldLast")
                .setParameter("newLast", "Kumar")
                .setParameter("oldLast", "K")
                .executeUpdate();

        tx.commit();
        session.close();

        System.out.println("UPDATE HQL (bulk) -> rows affected: " + updatedRows);
    }

    // --------------------------------------------------------------------
    // DELETE — object-based delete
    // --------------------------------------------------------------------
    private static void deleteStudentById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Student student = session.find(Student.class, id);
        if (student != null) {
            // remove() schedules the entity for DELETE (Hibernate 6/7 replaces delete())
            session.remove(student);
        }

        tx.commit();
        session.close();

        System.out.println("DELETE done -> id=" + id + " removed");
    }

    // --------------------------------------------------------------------
    // AGGREGATE HQL — COUNT
    // --------------------------------------------------------------------
    private static void countStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Aggregate functions return a scalar value, so result type is Long
        Long total = session.createQuery(
                "SELECT COUNT(s) FROM Student s", Long.class)
                .getSingleResult();

        System.out.println("HQL COUNT -> total students = " + total);
        session.close();
    }
}