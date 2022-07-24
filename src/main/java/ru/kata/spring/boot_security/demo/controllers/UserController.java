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
public class UserController {
    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public UserController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public ModelAndView home() {
        List<User> listUsers = userService.getAll();
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("listUsers", listUsers);
        return mav;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/registration";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_form";
    }

    @GetMapping("/user")
    public ModelAndView currentUser(Principal principal) {
        ModelAndView userView = new ModelAndView("user");
        ModelAndView loginView = new ModelAndView("login_form");
        if (principal == null) {
            return loginView;
        } else {
            User user = userService.findUserByname(principal.getName()).get();
            userView.addObject("user", user);
        }
        return userView;
    }

    @GetMapping("/new")
    public String newCustomerForm(@ModelAttribute("user") User user) {
        return "add_form";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage() {
        return "accessDenied_page";
    }
    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_form";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete")
    public String deleteUserFromForm(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("edit_form");
        User user = userService.getUser(id);
        mav.addObject("user", user);
        return mav;
    }
}
