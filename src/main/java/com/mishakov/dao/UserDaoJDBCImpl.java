package com.mishakov.dao;

import com.mishakov.model.User;
import com.mishakov.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` " +
            "(`id` BIGINT NOT NULL AUTO_INCREMENT," +
            " `name` VARCHAR(45) NOT NULL," +
            " `lastName` VARCHAR(45) NOT NULL," +
            " `age` TINYINT NOT NULL," +
            " PRIMARY KEY (`id`))";

    private final String DELETE_TABLE = "DROP TABLE IF EXISTS users";
    private final String SAVE_NEW_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    private final String GET_ALL_USERS = "SELECT * FROM users";
    private final String CLEAR_TABLE = "DELETE FROM users";
    private final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    private Connection connection = Util.getMyConnection();
    private PreparedStatement myPS = null;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            myPS = connection.prepareStatement(CREATE_TABLE);
            myPS.executeUpdate();
            myPS.close();
        } catch(SQLException ex) {
            System.out.println("Problems with creating table");
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
