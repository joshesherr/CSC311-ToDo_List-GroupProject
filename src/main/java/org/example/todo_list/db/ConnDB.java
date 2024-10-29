package org.example.todo_list.db;

import org.example.todo_list.models.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnDB {

    final String MYSQL_SERVER_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/ToDoList";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";

    Person p = null;

    /**
     * Connect to the database and create the database and table if not created, accept the sql query for creating the table
     * @param sql
     */
    public  void connectToDatabase(String sql) {
        try {
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS ToDoList");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();

            statement.executeUpdate(sql);

            //check if we have users in the table users
            statement = conn.createStatement();

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Create the table if not created, accept the sql query for creating the table
     * @param sql
     * @return
     */
    public void createTable(String sql) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            //check if we have users in the table users
            statement = conn.createStatement();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
