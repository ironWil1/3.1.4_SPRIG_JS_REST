package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    EntityManager em;

    @Override
    public void cleanBindedRoles(User user) {
        TypedQuery<Role> query = em.createQuery(
                "SELECT r FROM Role r WHERE r.owner = ?1", Role.class);
        List<Role> rolesOfUser = query.setParameter(1, user).getResultList();
        rolesOfUser.forEach(role -> em.remove(role));
    }

    @Transactional(readOnly = true)
    @Override
    public void persistRoles(User user) {
        Set<Role> roles = user.getRoles();
        if(roles == null) {
            roles = new HashSet<>();
            roles.add(new Role(Roles.userRole()));
            user.setRoles(roles);
        }
        if (roles.stream().anyMatch(role -> role.getRole().equals(Roles.adminRole()))) {
            roles.add(new Role(Roles.userRole()));
        }
        roles.forEach(role -> role.setOwner(user));
    }
}
