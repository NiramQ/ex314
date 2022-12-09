package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("")
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getListUsers())
                .addAttribute("newUser", new User())
                .addAttribute("currentUser", userService.getUserByUsername(principal.getName()));
        return "/main";
    }

    @GetMapping("/update")
    public String getFormSavePerson(Model model) {
        model.addAttribute("newUser", new User())
                .addAttribute("roles", roleService.getAllRoles());
        return "/main";
    }

    @PostMapping("/create")
    public String saveNewPerson(@ModelAttribute("user") User user,
                                BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getPageForEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserById(id));
        return "/user";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/update")
    public String updatePerson(@ModelAttribute("user") User user,
                               @PathVariable("id") int id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }
}
