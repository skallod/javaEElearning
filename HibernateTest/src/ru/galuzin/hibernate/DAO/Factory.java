package ru.galuzin.hibernate.DAO;


import ru.galuzin.hibernate.DAO.impl.StatisticsDAOImpl;
import ru.galuzin.hibernate.DAO.impl.StudentDAOImpl;
import ru.galuzin.hibernate.DAO.impl.TestDAOImpl;

/**
 * Created by User on 29.01.2016.
 */
public class Factory {

    private static final Factory instance = new Factory();
    private StudentDAO studentDAO = new StudentDAOImpl();
    private TestDAOImpl testDAO = new TestDAOImpl();
    private StatisticsDAOImpl statisticsDAO = new StatisticsDAOImpl();

    public static Factory getInstance(){return instance;}

    public StudentDAO getStudentDAO(){
        return studentDAO;
    }

    public TestDAOImpl getTestDAO() {
        return testDAO;
    }

    public StatisticsDAOImpl getStatisticsDAO() {
        return statisticsDAO;
    }
}
