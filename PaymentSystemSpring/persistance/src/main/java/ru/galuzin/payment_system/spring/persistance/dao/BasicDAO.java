package ru.galuzin.payment_system.spring.persistance.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import ru.galuzin.payment_system.spring.persistance.HibernateSession;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 23.02.2016.
 */
public class BasicDAO {
    private static final Logger LOG = LoggerFactory.getLogger(BasicDAO.class);

    private HibernateTemplate template;

    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }

    public Serializable addEntity(Object entity){
        Serializable id = template.save(entity);
//        Session sess = HibernateSession.getSessionFactory().openSession();
//        Transaction tx=null;
//        Serializable id=null;
//        try {
//            tx = sess.beginTransaction();
//            id =sess.save(entity);
//            tx.commit();
//        }
//        catch (Exception e) {
//            if (tx!=null) tx.rollback();
//            throw new RuntimeException(e);
//        }
//        finally {
//            sess.close();
//        }
//
        return id;
    }

    public void updateEntity(Object entity){
        template.saveOrUpdate(entity);
//        Session sess = HibernateSession.getSessionFactory().openSession();
//        Transaction tx=null;
//        Serializable id=null;
//        try {
//            tx = sess.beginTransaction();
//            sess.saveOrUpdate(entity);
//            tx.commit();
//        }
//        catch (Exception e) {
//            if (tx!=null) tx.rollback();
//            throw new RuntimeException(e);
//        }
//        finally {
//            sess.close();
//        }

    }

    public void deleteEntity(Object entity){
        template.delete(entity);
//        Session sess = HibernateSession.getSessionFactory().openSession();
//        Transaction tx=null;
//        Serializable id=null;
//        try {
//            tx = sess.beginTransaction();
//            sess.delete(etitty);
//            tx.commit();
//        }
//        catch (Exception e) {
//            if (tx!=null) tx.rollback();
//            throw new RuntimeException(e);
//        }
//        finally {
//            sess.close();
//        }

    }

    public Object getEntity (Class clazz, Serializable entityId){
        Object entity = template.load(clazz,entityId);
//        Session sess = HibernateSession.getSessionFactory().openSession();
//        Transaction tx=null;
//        Object entity;
//        try {
//            tx = sess.beginTransaction();
//            entity = sess.get(clazz, entityId);
//            tx.commit();
//        }
//        catch (Exception e) {
//            if (tx!=null) tx.rollback();
//            throw new RuntimeException(e);
//        }
//        finally {
//            sess.close();
//        }
        return entity;
    }

    public java.util.Collection getAllEntities (Class clazz){
//        return template.findByExample(clazz);
        Session sess = HibernateSession.getSessionFactory().openSession();
        Transaction tx=null;
        List entities = null;
        try {
            tx = sess.beginTransaction();
            entities = sess.createCriteria(clazz).list();
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw new RuntimeException(e);
        }
        finally {
            sess.close();
        }

        return entities;
    }
}