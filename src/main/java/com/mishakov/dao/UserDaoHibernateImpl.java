package com.mishakov.dao;

import com.mishakov.model.User;
import com.mishakov.util.Util;
import org.apache.logging.log4j.Level;
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
    private final String CLEAR_TABLE = "DELETE FROM users";
    private final String GET_ALL_USERS = "SELECT * FROM users";



    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Util.getUtilLogger().log(Level.ERROR,"Problem with create table", ex);
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(DELETE_TABLE, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Util.getUtilLogger().log(Level.ERROR,"Problem with delete table", ex);
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
            Util.getUtilLogger().log(Level.INFO,"User ?? ???????????? - " + name + " ?????????????? ???????????????? ?? ???????? ????????????");
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Util.getUtilLogger().log(Level.ERROR,"Problem with save user", ex);
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
            Util.getUtilLogger().log(Level.ERROR,"Problem with delete user", ex);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users= new ArrayList<>();
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            users = session.createNativeQuery(GET_ALL_USERS, User.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Util.getUtilLogger().log(Level.ERROR,"Problem with getting users", ex);
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(CLEAR_TABLE, User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            Util.getUtilLogger().log(Level.ERROR,"Problem with clean table", ex);
        } finally {
            session.close();
        }
    }
}
