package ru.galuzin.payment_system.persistance.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.payment_system.persistance.HibernateSession;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 23.02.2016.
 */
public class BasicDAO {
    private static final Logger LOG = LoggerFactory.getLogger(BasicDAO.class);

    public Serializable addEntity(Object entity){
        Session session = HibernateSession.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Serializable id =session.save(entity);
        session.getTransaction().commit();
        return id;
    }

    public void updateEntity(Object entity){
        Session session = HibernateSession.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
    }

    public void deleteEtity(Object etitty){
        Session session = HibernateSession.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(etitty);
        session.getTransaction().commit();
    }

    public Object getEntity (Class clazz, Serializable entityId){
        Session session = HibernateSession.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Object entity = session.get(clazz, entityId);
        session.getTransaction().commit();
        return entity;
    }

    public java.util.Collection getAllEntities (Class clazz){
        Session session = HibernateSession.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List entities = session.createCriteria(clazz).list();
        session.getTransaction().commit();
        return entities;
    }
}