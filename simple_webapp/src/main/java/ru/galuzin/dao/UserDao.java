package ru.galuzin.dao;

import ru.galuzin.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by galuzin on 03.02.2017.
 */
@Stateless
public class UserDao {
    @PersistenceContext(unitName = "DataSourceEx")
    private EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u")
                .getResultList();
    }
}
