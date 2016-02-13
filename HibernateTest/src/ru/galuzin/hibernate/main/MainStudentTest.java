package ru.galuzin.hibernate.main;

import ru.galuzin.hibernate.DAO.Factory;
import ru.galuzin.hibernate.test.HibernateUtil;
import ru.galuzin.hibernate.test.Statistics;
import ru.galuzin.hibernate.test.Student;
import ru.galuzin.hibernate.test.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 29.01.2016.
 */
public class MainStudentTest {
    public static void main(String[] args) throws Exception {

//        firstLesson();

        secondLesson();

        HibernateUtil.quit();

    }
    
    public static void firstLesson(){
        Student student = new Student();
        student.setName("Leon6");
        student.setAge(30L);

        Student student2 = new Student();
        student2.setName("Dimon6"); 2
        student2.setAge(35L);

        try {
            Factory.getInstance().getStudentDAO().addStudent(student);
            Factory.getInstance().getStudentDAO().addStudent(student2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //different querys sql, hql, criteria
    public static void secondLesson() throws Exception{
        List<Student> list= Factory.getInstance().getStudentDAO().getAllStudents();
        for(Student stud : list){
            System.out.println("stud = " + stud);
        }
        List<Test> list2= Factory.getInstance().getTestDAO().getAllTests();
        for(Test stud : list2){
            System.out.println("test = " + stud);
        }
        List<Statistics> list3 = Factory.getInstance().getStatisticsDAO().getAllStatistics();
        for(Statistics stcs: list3){
            System.out.println("stcs = " + stcs);
        }
    }
        
}
