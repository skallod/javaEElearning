package ru.galuzin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by User on 30.01.2016.
 */
public class PostgresJDBCtest {
    private static String dbUrl = "jdbc:postgresql://127.0.0.1:5432/db1";

    public static void main(String[] args) throws Exception{
        Class.forName("org.postgresql.Driver").newInstance();
        Connection connection= DriverManager.getConnection(dbUrl,"postgres","12345678");
        Statement stmt = connection.createStatement();
//            stmt.execute("create TABLE restaurants (id int not NULL , restName varchar(100), cityName varchar(100), CONSTRAINT id_pkey PRIMARY KEY (id))");
//            stmt.execute("insert into " + tableName + " values (" + id + ",'" + restName + "','" + cityName +"')");
//        stmt.execute("CREATE TABLE Student(id int NOT NULL,name varchar(100) NOT NULL, age int NOT NULL, CONSTRAINT pk_Student PRIMARY KEY(id))");
//        CREATE TABLE Test(tid NUMBER(10) NOT NULL,tname varchar2(100) NOT NULL, CONSTRAINT pk_Test PRIMARY KEY(tid));
//        CREATE TABLE Statistics(stid NUMBER(10) NOT NULL, id NUMBER(10) NOT NULL, tid NUMBER(10) NOT NULL, CONSTRAINT pk_Statistics PRIMARY KEY(stid), CONSTRAINT fk_Student FOREIGN KEY(id) REFERENCES Student(id), CONSTRAINT fk_Test FOREIGN KEY(tid) REFERENCES Test(tid));
        stmt.close();
    }
}
