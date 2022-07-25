package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ModelAndView home() {
        List<User> listUsers = userService.getAll();
        ModelAndView mav = new ModelAndView("admin_homepage");
        mav.addObject("listUsers", listUsers);
        return mav;
    }

    @GetMapping("/new")
    public String newCustomerForm(@ModelAttribute("user") User user) {
        return "add_form";
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
