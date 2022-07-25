package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void saveUser(User user);

    void deleteUser(long id);

    User getUser(long id);

    void updateUser(User user);

    List<User> showAllUsers();

    Optional<User> findUserByName(String username);
}
