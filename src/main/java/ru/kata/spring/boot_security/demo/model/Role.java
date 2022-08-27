package ru.kata.spring.boot_security.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Override
    public String toString() {
        if (role.equals(Roles.adminRole())) {
            return "ADMIN";
        } else {
            return "USER";
        }
    }

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
                            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User owner;

    public Role(){}
    public Role(User owner, String role) {
        this.owner = owner;
        this.role = role;
    }

    public Role(String role) {
        this.role = role;
    }

    @Column(name = "role")

    private String role;

    @Override
    public String getAuthority() {
        return getRole();
    }

}
