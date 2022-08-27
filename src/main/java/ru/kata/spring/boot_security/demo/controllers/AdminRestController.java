package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserListValidator;
import ru.kata.spring.boot_security.demo.util.UserValidator;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin")
public class AdminRestController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final UserListValidator userListValidator;

    @Autowired
    public AdminRestController(UserService userService, UserValidator userValidator, UserListValidator userListValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userListValidator = userListValidator;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Object>> home() {
        List<Object> listOfObjects = new ArrayList<>();
        try {
            listOfObjects.add(userService.getAll());
            return new ResponseEntity<>(listOfObjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/userInfo")
    public ResponseEntity<User> ShowLoggedUserInfo(Principal principal) {
        System.out.println(principal);
        if (principal != null) {
            Optional<User> loggedUser = userService.findUserByname(principal.getName());
            System.out.println(loggedUser);
            if (loggedUser.isPresent()) {
                User user = loggedUser.get();
                try {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable long id) {
        try {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        System.out.println(user);
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        System.out.println(user);
        userService.deleteUser(user.getId());
        try {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
