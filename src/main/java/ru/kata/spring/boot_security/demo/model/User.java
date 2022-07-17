package ru.kata.spring.boot_security.demo.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User {
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", accountBalance=" + password +
                '}';
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    @Size(min = 2,max = 22,message = "Names length should be between 2 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String name;
    @Column(name = "surname")
    @Size(min = 2,max = 20,message = "Surname length should be between 2 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String surName;
    @Column(name = "password")
    private long password;

    public User() {
    }

    public User(String name, String surName, long password) {
        this.name = name;
        this.surName = surName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }
}
