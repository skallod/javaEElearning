package ru.galuzin.payment_system.persistance.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.payment_system.common_types.Account;
import ru.galuzin.payment_system.persistance.HibernateSession;

/**
 * Created by User on 23.02.2016.
 */
public class BasicDAO {
    private static final Logger LOG = LoggerFactory.getLogger(BasicDAO.class);

    public Serializable addEntity(Object entity){

        Session sess = HibernateSession.getSessionFactory().openSession();
        Transaction tx=null;
        Serializable id=null;
        try {
            tx = sess.beginTransaction();
            id =sess.save(entity);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw new RuntimeException(e);
        }
        finally {
            sess.close();
        }

        return id;
    }
    public void updateEntity(Object entity){
        updateEntity(entity,false);
    }

    public void updateEntity(Object entity, boolean flag){

        Session sess1 = HibernateSession.getSessionFactory().openSession();
        sess1.flush();
        Transaction tx1=null;
        Serializable id1=null;

        //Session sess2 = HibernateSession.getSessionFactory().openSession();
        sess1.flush();
        Transaction tx2=null;
        //Serializable id1=null;
        try {
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            tx1 = sess1.beginTransaction();
            //tx2 = sess1.beginTransaction();
            if(flag) {
                //sess1.delete(entity);
                ((Account)entity).setMoney(22.4d);
                sess1.update(entity);
                Thread.sleep(500);
                System.out.println("flush1");
                sess1.flush();
            }else{
                ((Account)entity).setMoney(22.4d);
                sess1.update(entity);
                Thread.sleep(1_000);
                System.out.println("flush2");
                sess1.flush();
            }
//            ((Account)entity).setMoney(11.1d);
//            sess1.update(entity);
            tx1.commit();
            //tx2.commit();
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName()+"after commit");
        }
        catch (Exception e) {
            if (tx1!=null) {
                tx1.rollback();
            }
            //if (tx2!=null) tx2.rollback();
            throw new RuntimeException(e);
        }
        finally {
            sess1.close();
            //sess2.close();
        }

    }

    public void deleteEtity(Object etitty){

        Session sess = HibernateSession.getSessionFactory().openSession();
        Transaction tx=null;
        Serializable id=null;
        try {
            tx = sess.beginTransaction();
            sess.delete(etitty);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) tx.rollback();
            throw new RuntimeException(e);
        }
        finally {
            sess.close();
        }

    }

    public Object getEntity (Class clazz, Serializable entityId){
        Session sess1 = HibernateSession.getSessionFactory().openSession();
//        Session sess2 = HibernateSession.getSessionFactory().openSession();
//        if(sess1==sess2) System.out.println("EQUALS");
        Transaction tx1=null;
//        Transaction tx2=null;
        Object entity1;
//        Object entity2;
        try {
            tx1 = sess1.beginTransaction();
//            tx2 = sess2.beginTransaction();
            entity1 = sess1.get(clazz, entityId);
//            entity2 = sess2.get(clazz, entityId);
            tx1.commit();
//            tx2.commit();
        }
        catch (Exception e) {
            if (tx1!=null) tx1.rollback();
//            if (tx2!=null) tx2.rollback();
            throw new RuntimeException(e);
        }
        finally {
            sess1.close();
//            sess2.close();
        }
        return entity1;
    }

    public java.util.Collection getAllEntities (Class clazz){
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