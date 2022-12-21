package com.mishakov.service;

import com.mishakov.model.User;

import java.util.List;

public interface UserService {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void printUsers(List<User> userList);

    void cleanUsersTable();
}
