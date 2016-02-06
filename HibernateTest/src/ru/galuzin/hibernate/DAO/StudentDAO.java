package ru.galuzin.hibernate.DAO;

import ru.galuzin.hibernate.test.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 29.01.2016.
 */
public interface StudentDAO {
    public void addStudent(Student student) throws SQLException;
    public void updateStudent(Student student) throws SQLException;
    public Student getStudentById(int id) throws SQLException;
    public List getAllStudents() throws SQLException;
    public void deleteStudent(Student student) throws SQLException;
}