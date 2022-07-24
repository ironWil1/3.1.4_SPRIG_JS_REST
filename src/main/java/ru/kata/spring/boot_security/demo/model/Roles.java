package ru.kata.spring.boot_security.demo.model;

public enum Roles {
    ROLE_ADMIN,
    ROLE_USER;

    public static String adminRole(){
        return ROLE_ADMIN.toString();
    }

    public static String userRole(){
        return ROLE_USER.toString();
    }

}
