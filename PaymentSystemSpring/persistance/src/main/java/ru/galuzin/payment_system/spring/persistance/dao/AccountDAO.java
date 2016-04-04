//package ru.galuzin.payment_system.spring.persistance.dao;
//
//import org.hibernate.Session;
//import ru.galuzin.payment_system.common_types.Account;
//import ru.galuzin.payment_system.persistance.HibernateSession;
//
//import javax.swing.*;
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//
///**
// * Created by LEONID on 14.02.2016.
// */
//public class AccountDAO {
//
//    public void addAccount(Account account){
//        Session session = null;
//        try {
//            session=HibernateSession.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.save(account);
//            session.getTransaction().commit();
//        }catch (Exception e){
//            session.getTransaction().rollback();
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, e.getMessage(),"Hibernate error", JOptionPane.OK_OPTION);
//        }finally {
//            if(session!=null && session.isOpen()){
//                session.close();
//            }
//        }
//
//    }
//
//
//    public void updateAccount(Account account){
//        Session session = null;
//        try {
//            session=HibernateSession.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.update(account);
//            session.getTransaction().commit();
//        }catch (Exception e){
//            session.getTransaction().rollback();
//            JOptionPane.showMessageDialog(null, e.getMessage(),"Hibernate error", JOptionPane.OK_OPTION);
//        }finally {
//            if(session!=null && session.isOpen()){
//                session.close();
//            }
//        }
//
//    }
//
//    public Account getAccountById(Long id){
//        Session session = null;
//        Account account = null;
//        try {
//            session=HibernateSession.getSessionFactory().openSession();
//            account = (Account)session.load(Account.class, id);
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(null, e.getMessage(),"Hibernate error", JOptionPane.OK_OPTION);
//        }finally {
//            if(session!=null && session.isOpen()){
//                session.close();
//            }
//        }
//        return account;
//    }
//
//    public Collection<Account> getAllAccounts(){
//        Session session = null;
//        List<Account> accountList = null;
//        try {
//            session=HibernateSession.getSessionFactory().openSession();
//            accountList = session.createCriteria(Account.class).list();
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(null, e.getMessage(),"Hibernate error", JOptionPane.OK_OPTION);
//        }finally {
//            if(session!=null && session.isOpen()){
//                session.close();
//            }
//        }
//        return accountList;
//    }
//
//
//    public void deleteAccount(Account account){
//        Session session = null;
//        try {
//            session=HibernateSession.getSessionFactory().openSession();
//            session.beginTransaction();
//            session.delete(account);
//            session.getTransaction().commit();
//        }catch (Exception e){
//            session.getTransaction().rollback();
//            JOptionPane.showMessageDialog(null, e.getMessage(),"Hibernate error", JOptionPane.OK_OPTION);
//        }finally {
//            if(session!=null && session.isOpen()){
//                session.close();
//            }
//        }
//
//    }
//
//}
