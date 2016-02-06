package ru.galuzin.hibernate.main;

import java.sql.*;

class EmployeeList {
    public static void main(String argv[]) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
// Load the JDBC driver - Class
// This can be skipped for Derby, but derbyclient.jar
//has to be in the CLASSPATH
            System.out.println(System.getProperties());
// Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/database1?charSet=UTF8","postgres","12345678");
// Create an SQL query
            String sqlQuery ="SELECT * from Employee";
// Create an instance of the Statement object
            stmt = conn.createStatement();
// Execute SQL and get obtain the ResultSet object
            rs = stmt.executeQuery(sqlQuery);
// Process the result set - print Employees
            while (rs.next()) {
                int empNo = rs.getInt("EMPNO");
                String eName = rs.getString("ENAME");
                String job = rs.getString("JOB_TITLE");
                System.out.println("" + empNo +","+eName +","+job);
            }
        } catch (SQLException se) {
            System.out.println("SQLError:"+se.getMessage()
                    +"code:"+se.getErrorCode());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
// clean up the system resources
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}