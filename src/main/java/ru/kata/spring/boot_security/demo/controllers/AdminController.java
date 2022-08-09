package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping(value = "")
    public ModelAndView home(Principal principal) {
        if (userService.findUserByname(principal.getName()).isPresent()) {
            User user = userService.findUserByname(principal.getName()).get();
            List<User> listUsers = userService.getAll();
            ModelAndView mav = new ModelAndView("admin_homepage");
            mav.addObject("listUsers", listUsers);
            mav.addObject("user", user);
            return mav;
        } else {
            return new ModelAndView("login_form");
        }
    }

    @GetMapping("/new")
    public String newCustomerForm(@ModelAttribute("user") User user) {
        return "add_form";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors() & user.getId() == null) {
            return "add_form";
        } else if (bindingResult.hasErrors() & user.getId() != null) {
            return "edit_error";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete")
    public String deleteUserFromForm(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("edit_form");
        User user = userService.getUser(id);
        mav.addObject("user", user);
        return mav;
    }

}
