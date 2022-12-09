package com.mishakov;

import com.mishakov.dao.UserDao;
import com.mishakov.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.createUsersTable();
    }
}
