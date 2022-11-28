package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getListUsers() {
        return entityManager.createQuery("SELECT x FROM User x", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user, int id) {
        User userToBeUpdate = getUserById(id);
        userToBeUpdate.setUsername(user.getUsername());
        userToBeUpdate.setAge(user.getAge());
        userToBeUpdate.setEmail(user.getEmail());
        userToBeUpdate.setPassword(passwordEncoder().encode(user.getPassword()));
        userToBeUpdate.setRoles(user.getRoles());
        entityManager.merge(userToBeUpdate);
    }

    @Override
    public void removeUser(int id) {
        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }
}
