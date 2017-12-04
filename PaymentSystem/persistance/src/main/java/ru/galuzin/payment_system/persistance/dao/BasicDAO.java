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
//    public void updateEntity(Object entity, boolean flag){
//        updateEntity(Object entity, boolean flag){
//    }

    public void updateEntity(Object entity, boolean flag){
        Session sess1 = HibernateSession.getSessionFactory().openSession();
        Transaction tx1=null;
        try {
            tx1 = sess1.beginTransaction();
            Account acc1 = (Account)entity;
//            System.out.println("acc1.person = " + acc1.person);
//            System.out.println("acc1.getPerson() = " + acc1.getPerson());
//            System.out.println("acc1.getPerson().getName() = " + acc1.getPerson().getName());
            entity = sess1.merge(entity);

//            EntityData ed = (EntityData)entity;
//            System.out.println("on update ed.getModified() = " + ed.getModified());
//            List<VersionData> versions = ed.getVersions();
//            System.out.println("versions = " + versions);

            //sess1.update(entity);
            tx1.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (tx1!=null) {
                tx1.rollback();
            }
        }
        finally {
            if ((tx1 != null) && tx1.isActive()) {
                try {
                    tx1.rollback();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sess1.close();
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