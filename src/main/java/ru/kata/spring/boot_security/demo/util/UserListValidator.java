package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.UserList;
import ru.kata.spring.boot_security.demo.service.UserService;

@Component
public class UserListValidator implements Validator {
    private final UserService userService;

    public UserListValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserList.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
