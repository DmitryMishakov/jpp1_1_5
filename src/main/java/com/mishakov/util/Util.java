package com.mishakov.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

//  JDBC part
    private static final String DBNAME = "mydbtest";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DBNAME;
    private static final String USER = "root";
    private static final String PASSWORD = "rood";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // for old java (< 8 version)

    public Connection getMySQLConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER); // for old java (< 8 version)
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            System.out.println("Problem with connection");
            sqlException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Driver not found");
            classNotFoundException.printStackTrace();
        }
        return conn;
    }

// Hibernate part
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
