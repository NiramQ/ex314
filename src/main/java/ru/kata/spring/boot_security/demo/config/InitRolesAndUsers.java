package ru.kata.spring.boot_security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitRolesAndUsers {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public InitRolesAndUsers(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void start() {
        roleRepository.save(new Role(1, "ROLE_ADMIN"));
        roleRepository.save(new Role(2, "ROLE_USER"));
        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        adminRole.add(roleRepository.findById(1).orElse(null));
        userRole.add(roleRepository.findById(2).orElse(null));
        User admin = new User(1, "admin", 20, "admin@mail.ru", "admin", adminRole);
        User user = new User(2, "user", 20, "user@mail.ru", "user", userRole);
        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
