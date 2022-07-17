package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void saveUser(User user) {
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
        em.merge(user);
    }


    @Transactional(readOnly = true)
    @Override
    public List<User> showAllUsers() {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}
