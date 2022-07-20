package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",unique = true)
    @Size(min = 2,max = 22,message = "Names length should be between 2 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String name;
    @Column(name = "surname")
    @Size(min = 2,max = 20,message = "Surname length should be between 2 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String surName;
    @Column(name = "password")
    @Size(min = 5,max = 20,message = "Password length should be between 5 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String password;

    @Column(name = "role")
    @OneToMany(mappedBy="owner")
    private List<Role> roles;

    public User() {
    }
    @Override
    public String getPassword() {
        return String.valueOf(password);
    }

    public User(String name, String surName, String password) {
        this.name = name;
        this.surName = surName;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
