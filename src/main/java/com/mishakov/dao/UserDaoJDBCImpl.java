package com.mishakov.dao;

import com.mishakov.model.User;
import com.mishakov.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try {
            myPS = connection.prepareStatement(DELETE_TABLE);
            myPS.executeUpdate();
            myPS.close();
        } catch(SQLException ex) {
            System.out.println("Problems with deleting table");
            ex.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            myPS = connection.prepareStatement(SAVE_NEW_USER);
            myPS.setString(1, name);
            myPS.setString(2, lastName);
            myPS.setByte(3, age);
            myPS.executeUpdate();
            System.out.println("User с именем - " + name + " успешно добавлен в базу данных");
            myPS.close();
        } catch(SQLException ex) {
            System.out.println("Problems with saving user");
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            myPS = connection.prepareStatement(DELETE_USER_BY_ID);
            myPS.setLong(1, id);
            myPS.executeUpdate();
            myPS.close();
        } catch(SQLException ex) {
            System.out.println("Problems with removing user");
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result =new ArrayList<>();
        try {
            myPS = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = myPS.executeQuery();
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                result.add(user);
            }
            myPS.close();
        } catch(SQLException ex) {
            System.out.println("Problems with select all users");
            ex.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {

    }
}
