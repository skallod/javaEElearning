package ru.galuzin.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {

    static Connection getConnection() {
        try {

            DriverManager.registerDriver(new org.h2.Driver());
            //jdbc:h2:mem:
            return DriverManager.getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE", "sa", "");

//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//
//            String url = "jdbc:mysql://" +       //db type
//                    "localhost:" +               //host name
//                    "3306/" +                    //port
//                    "db_example?" +              //db name
//                    "user=tully&" +              //login
//                    "password=tully&" +          //password
//
//                    "useSSL=false";             //do not use Secure Sockets Layer
//
//
//            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
