package com.mishakov.dao;

import com.mishakov.model.User;
import com.mishakov.util.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Selection;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final String DELETE_TABLE = "DROP TABLE IF EXISTS users";
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` " +
            "(`id` BIGINT NOT NULL AUTO_INCREMENT," +
            " `name` VARCHAR(45) NOT NULL," +
            " `lastName` VARCHAR(45) NOT NULL," +
            " `age` TINYINT NOT NULL," +
            " PRIMARY KEY (`id`))";
    private final String GET_ALL_USERS = "FROM User";



    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Problem with create table");
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(DELETE_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Problem with delete table");
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " успешно добавлен в базу данных");
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Problem with save user");
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Problem with delete user");
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> users= new ArrayList<>();
        try {
            session.beginTransaction();
            users = session.createQuery(GET_ALL_USERS, User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Problem with getting users");
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        List<User> usersList = this.getAllUsers();
        try {
            session.beginTransaction();
            try {
                usersList.forEach(x -> session.remove(x));
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            System.out.println("Problem with clean table");
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }
}
