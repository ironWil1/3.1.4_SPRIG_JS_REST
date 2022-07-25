package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

public interface RoleDAO {
    void cleanBindedRoles(User user);
    void persistRoles(User user);
}
