package com.mishakov.util;

import com.mishakov.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";

    private static SessionFactory sessionFactory = buildMySessionFactory();

    private static SessionFactory buildMySessionFactory() {
        if(sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, DIALECT);
                settings.put(Environment.SHOW_SQL, true); // не зыбыть потом удалить

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(settings).build();

                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(User.class);
                Metadata metadata = metadataSources.buildMetadata();

                return metadata.getSessionFactoryBuilder().build();
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        } else {
            return sessionFactory;
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
