package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserList;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserListValidator;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserListValidator userListValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, UserListValidator userListValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userListValidator = userListValidator;
    }

//    @GetMapping(value = "")
//    public String home(Principal principal, Model model) {
//        UserList listUsers = new UserList();
//        listUsers.setUserList(userService.getAll());
//        model.addAttribute("listUsers", listUsers);
////        if (userService.findUserByname(principal.getName()).isPresent()) {
////            return "admin_homepage";
////        } else {
////            return "login_form";
////        }
//        return "admin_homepage";
//    }

    @GetMapping("/new")
    public String newCustomerForm(@ModelAttribute("user") User user) {
        return "add_form";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "add_form";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping (value = "/edit")
    public String editUser(@RequestParam long id,
                           @ModelAttribute UserList userList,
                           BindingResult bindingResult, Model model) {
        userListValidator.validate(userList, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "edit_error";
        }
        userService.saveUser(userList.findUserByID(id));
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete")
    public String deleteUserFromForm(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
