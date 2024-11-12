package com.niantic.controllers;

import com.niantic.models.User;
import com.niantic.services.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        ArrayList<User> users = userDao.getAllUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("action", "add");
        return "users/add_edit";
    }

    @PostMapping("/users/add")
    public String addUser(Model model, @ModelAttribute("user") User user) {
        userDao.addUser(user);
        model.addAttribute("user", user);
        return "users/add_success";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(Model model, @PathVariable int id) {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("action", "edit");
        return "users/add_edit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUser(@ModelAttribute("user") User user, @PathVariable int id) {
        user.setUserId(id);
        userDao.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(Model model, @PathVariable int id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            model.addAttribute("message", String.format("There is no user with id %d", id));
            return "404";
        }
        model.addAttribute("user", user);
        return "users/delete";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        userDao.deleteUser(id);
        return "redirect:/users";
    }
}
