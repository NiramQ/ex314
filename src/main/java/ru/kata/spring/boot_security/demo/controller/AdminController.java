package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String adminPage(Model model) {
        model.addAttribute("user", userService.getListUsers());
        return "main";
    }

    @GetMapping("/new")
    public String getFormSavePerson(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    @ResponseBody
    public String saveNewPerson(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String getPersonForEdit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        //model.addAttribute("roles", roleService.findRoleById());
        return "edit";
    }

    @DeleteMapping("/edit/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String updatePerson(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }
}
