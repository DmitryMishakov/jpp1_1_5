package com.mishakov;

import com.mishakov.service.UserService;
import com.mishakov.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 12);
        userService.saveUser("Tom", "Tomanov", (byte) 23);
        userService.saveUser("Sam", "Samanov", (byte) 34);
        userService.saveUser("Jack", "Jackanov", (byte) 45);
        userService.printUsers(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
