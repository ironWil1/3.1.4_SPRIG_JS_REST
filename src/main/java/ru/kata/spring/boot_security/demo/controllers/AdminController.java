package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserList;
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
//    @InitBinder
//    public void initBinder(WebDataBinder binder, HttpServletRequest request) {
//        String httpMethod = request.getMethod();
//        if ("POST".equals(httpMethod)) {
//            binder.setAllowedFields("username","email", "password");
//        } else if ("PUT".equals(httpMethod)) {
//            // update
//            binder.setAllowedFields("username","email", "password");
//        }
//
//    }
    @GetMapping(value = "")
    public String home(Principal principal, Model model) {
        UserList listUsers = new UserList();
        List<User> userList = userService.getAll();
        userList.forEach(listUsers::addUser);
        model.addAttribute("listUsers", listUsers);
        System.out.println(listUsers);
        if (userService.findUserByname(principal.getName()).isPresent()) {
            return "admin_homepage";
        } else {
            return "login_form";
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
        if (bindingResult.hasErrors()) {
            return "add_form";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping (value = "/edit")
    public String editUser(@RequestParam long id,
                           @ModelAttribute UserList userlist,
                           BindingResult bindingResult) {
        //userValidator.validate(userlist.findUserByID(id), bindingResult);
        if (bindingResult.hasErrors()) {
            return "edit_error";
        }
        userService.saveUser(userlist.findUserByID(id));
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete")
    public String deleteUserFromForm(@RequestParam long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

//    @GetMapping("/edit")
//    public ModelAndView editCustomerForm(@RequestParam long id) {
//        ModelAndView mav = new ModelAndView("edit_form");
//        User user = userService.getUser(id);
//        mav.addObject("user", user);
//        return mav;
//    }

}
