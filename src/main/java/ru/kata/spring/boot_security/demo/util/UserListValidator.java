package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserList;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Objects;
import java.util.Optional;

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
        UserList userList = (UserList) target;
        User user = userList.getUserList().stream()
                .filter(user1 -> user1.getUsername()!=null)
                .findFirst().get();
        Optional<User> checkedUserByName = userService.findUserByname(user.getUsername());
        if (checkedUserByName.isPresent() & !Objects.equals(user.getId(), checkedUserByName.get().getId())) {
            errors.rejectValue("userList", "", "A client with this name already exists," +
                    " please enter a different name");
        }
        Optional<User> checkedUserByEmail = userService.findUserByEmail(user.getEmail());
        if (checkedUserByEmail.isPresent() & !Objects.equals(user.getId(), checkedUserByEmail.get().getId())) {
            errors.rejectValue("userList", "", "A client with this email already exists," +
                    " please enter a different email");
        }
    }
}
