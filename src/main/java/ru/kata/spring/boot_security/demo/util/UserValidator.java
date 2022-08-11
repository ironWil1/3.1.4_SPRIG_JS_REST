package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserList;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> checkedUserByName = userService.findUserByname(user.getUsername());
        if (checkedUserByName.isPresent()) {
            errors.rejectValue("username", "", "A client with this name already exists," +
                    " please enter a different name");
        }
        Optional<User> checkedUserByEmail = userService.findUserByEmail(user.getEmail());
        if (checkedUserByEmail.isPresent()) {
            errors.rejectValue("email", "", "A client with this email already exists," +
                    " please enter a different email");
        }
    }
}
