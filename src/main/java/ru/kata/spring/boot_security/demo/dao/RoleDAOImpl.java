package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    EntityManager em;

    @Override
    public void cleanBindedRoles(User user) {
        List<Role> rolesOfUser = em.createQuery(
                "SELECT r FROM Role r WHERE r.owner = ?1", Role.class).setParameter(1, user).getResultList();
        rolesOfUser.forEach(role -> em.remove(role));
    }

    @Transactional(readOnly = true)
    @Override
    public void persistRoles(User user) {
        List<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new ArrayList<>();
            roles.add(new Role(Roles.userRole()));
            user.setRoles(roles);
        }
        if (roles.stream().anyMatch(role -> role.getRole().equals(Roles.adminRole())) &&
                roles.stream().noneMatch(role -> role.getRole().equals(Roles.userRole()))) {
            roles.add(new Role(Roles.userRole()));
        }
        roles.forEach(role -> role.setOwner(user));
    }
}
