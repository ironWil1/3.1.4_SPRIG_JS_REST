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
import java.util.Optional;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void saveUser(User user) {
        this.persistRoles(user);
        em.persist(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        em.remove(getUser(id));
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(long id) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.id = ?1", User.class);
        return query.setParameter(1, id).getSingleResult();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        this.cleanBindedRoles(user);
        this.persistRoles(user);
        em.merge(user);
    }

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


    @Transactional(readOnly = true)
    @Override
    public List<User> showAllUsers() {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findUserByName(String username) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u where u.username = ?1", User.class);
        return query.setParameter(1, username).getResultList().stream().findAny();
    }

}
