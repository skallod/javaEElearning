package ru.galuzin.hibernate.DAO.impl;

import org.hibernate.Session;
import ru.galuzin.hibernate.test.HibernateUtil;
import ru.galuzin.hibernate.test.Test;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 31.01.2016.
 */
public class TestDAOImpl {
    public List getAllTests() throws SQLException {
        Session session = null;
        List<Test> list = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            list  = session.createCriteria(Test.class).list();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "hibernate error", JOptionPane.OK_OPTION);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
        return list;
    }
}
