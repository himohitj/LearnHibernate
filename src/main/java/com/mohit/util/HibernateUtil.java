package com.mohit.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(Class<?>... entityClasses) {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration config = new Configuration().configure();
            for (Class<?> clazz : entityClasses) {
                config.addAnnotatedClass(clazz);
            }
            sessionFactory = config.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) getSessionFactory().close();
    }
}