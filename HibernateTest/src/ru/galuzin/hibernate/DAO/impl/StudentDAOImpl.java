package ru.galuzin.hibernate.DAO.impl;

import org.hibernate.Session;
import ru.galuzin.hibernate.DAO.StudentDAO;
import ru.galuzin.hibernate.test.HibernateUtil;
import ru.galuzin.hibernate.test.Student;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 29.01.2016.
 */
public class StudentDAOImpl implements StudentDAO {
    @Override
    public void addStudent(Student student) throws SQLException {
        Session session=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, e.getMessage(),"Hibernate error", JOptionPane.OK_OPTION);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public void updateStudent(Student student) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, e.getMessage(), "hibernate error", JOptionPane.OK_OPTION);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }

    @Override
    public Student getStudentById(int id) throws SQLException {
        Session session = null;
        Student student = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            student = (Student)session.load(Student.class, id);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "hibernate error", JOptionPane.OK_OPTION);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
        return student;
    }

    @Override
    public List getAllStudents() throws SQLException {
        Session session = null;
        List<Student> list = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            list  = session.createCriteria(Student.class).list();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "hibernate error", JOptionPane.OK_OPTION);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
        return list;
    }

    @Override
    public void deleteStudent(Student student) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            JOptionPane.showMessageDialog(null, e.getMessage(), "hibernate error", JOptionPane.OK_OPTION);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }

    }
}
