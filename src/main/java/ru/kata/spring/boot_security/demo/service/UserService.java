package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;


public interface UserService {
    void saveUser(User user);
    void deleteUser(long id);

    User getUser(long id);
    Optional<User> findUserByname(String name);
    Optional<User> findUserByEmail(String email);
    List<User> getAll();
}
