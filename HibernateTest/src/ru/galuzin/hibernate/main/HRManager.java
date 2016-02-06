package ru.galuzin.hibernate.main;

import java.util.List;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import ru.galuzin.hibernate.test.Employee;
public class HRManager {
    public static void main(String[] args){
        Configuration config = new Configuration().configure();//"E:/Galuzin/Eleron/TobolASB/ProjectRepo/HibernateTest/profiles/Conf/old.Hibernate.cfg.xml"
        System.out.println("config = " + config);
        config.addAnnotatedClass(Employee.class);
        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        List<Employee> employee = session.createQuery("from Employee").list();
        session.getTransaction().commit();
        for (Employee emp: employee){
            System.out.println("Got " + emp.getEmpno() + ", "
            + emp.getEname() + ", "+ emp.getJobTitle());
        }
    }
}