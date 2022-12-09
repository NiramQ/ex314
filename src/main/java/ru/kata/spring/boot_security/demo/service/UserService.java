package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void updateUser(User user, int id);

    void removeUserById(int id);

    User getUserById(int id);

    List<User> getListUsers();

    User getUserByUsername(String name);

}
