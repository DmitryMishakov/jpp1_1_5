package com.mishakov.service;

import com.mishakov.dao.UserDao;
import com.mishakov.dao.UserDaoHibernateImpl;
import com.mishakov.model.User;
import com.mishakov.util.Util;
import org.apache.logging.log4j.Level;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void printUsers(List<User> userList) {
        userList.forEach(x -> Util.getUtilLogger().log(Level.INFO, x.toString()));
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
