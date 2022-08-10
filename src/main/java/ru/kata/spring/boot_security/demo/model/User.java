package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Table(name = "user")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    @Size(min = 2,max = 22,message = "Names length should be between 2 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String username;
    @Column(name = "surname")
    @Size(min = 2,max = 20,message = "Surname length should be between 2 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String surName;

    @Column(name="email")
    @NotEmpty(message = "Email should be filled")
    private String email;

    @Column(name="age")
    @NotEmpty(message = "Age should be filled")
    private String age;

    @Column(name = "password")
    @Size(min = 5,max = 20,message = "Password length should be between 5 and 20 characters")
    @NotEmpty(message = "Crucial field")
    private String password;


    @OneToMany(mappedBy="owner",
                cascade = {CascadeType.DETACH,CascadeType.MERGE,
                CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE},
                fetch= FetchType.LAZY)
    private List<Role> roles;

    public User() {
    }

    public User(String username, String surName, String email, String age, String password, List<Role> roles) {
        this.username = username;
        this.surName = surName;
        this.email = email;
        this.age = age;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String surName, String email, String age, String password) {
        this.username = username;
        this.surName = surName;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    @Override
    public String getPassword() {
        return String.valueOf(password);
    }

    public void addRole(Role someRole) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(someRole);
        someRole.setOwner(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        getRoles().forEach(role -> grantedAuthorityList.add(new SimpleGrantedAuthority(role.getRole())));
        return grantedAuthorityList;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", surName='" + surName + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
