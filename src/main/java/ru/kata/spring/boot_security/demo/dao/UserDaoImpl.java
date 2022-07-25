package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;
    private final RoleDAO roleDAO;
    @Autowired
    public UserDaoImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        roleDAO.persistRoles(user);
        em.persist(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.id = ?1", User.class);
        em.remove(query.setParameter(1,id).getSingleResult());
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
        roleDAO.cleanBindedRoles(user);
        roleDAO.persistRoles(user);
        em.merge(user);
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
                "SELECT u FROM User u JOIN FETCH u.roles where u.username = ?1", User.class);
        return query.setParameter(1, username).getResultList().stream().findAny();
    }
}
