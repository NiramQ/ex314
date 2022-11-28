package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {

    void saveUser(User user);

    void updateUser(User user, int id);

    void removeUser(int id);

    User getUserById(int id);

    List<User> getListUsers();

}
