package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserList;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserListValidator;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<Object>> home(Principal principal) {
        User loggedUser = userService.findUserByname(principal.getName()).get();
        List<Object> loggedUserInfo = new ArrayList<>();
        loggedUserInfo.add(loggedUser.getEmail());
        loggedUserInfo.add(loggedUser.getRoles());
        List<Object> listOfObjects = new ArrayList<>();
        listOfObjects.add(loggedUserInfo);
        listOfObjects.add(userService.getAll());
        try {
            return new ResponseEntity<>(listOfObjects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping (value = "/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable long id) {
        try {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    //@CrossOrigin(origins = "http://localhost:8080")
    @PostMapping (value = "/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        System.out.println(user);
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping (value = "/delete")
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        System.out.println(user);
        userService.deleteUser(user.getId());
        try {
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
