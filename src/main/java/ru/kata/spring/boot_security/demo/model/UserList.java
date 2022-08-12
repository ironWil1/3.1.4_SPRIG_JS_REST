package ru.kata.spring.boot_security.demo.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserList {
    public UserList() {
    }

    public UserList(List<User> userList) {
        this.userList = userList;
    }

    private List<User> userList;

    public void addUser(User user) {
        if (userList == null) {
            userList = new ArrayList<>();
    }
        this.userList.add(user);
    }

    public User findUserByID(long id) {
        return userList.stream()
                .filter(user -> user.getUsername()!=null)
                .filter(user -> user.getId()==id)
                .findFirst()
                .get();
    }

}
