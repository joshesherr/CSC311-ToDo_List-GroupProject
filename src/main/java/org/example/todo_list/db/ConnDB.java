package org.example.todo_list.db;

import org.example.todo_list.models.Person;

import java.sql.*;

public class ConnDB {

    final String SQL_SERVER_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/ToDoList";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";
    final static String DB_NAME = "CSC311_ToDoList";

    Person p = null;

    /**
     * Connect to the database and create the database and table if not created, accept the sql query for creating the table
   //  * @param sql
     */
    public  void connectToDatabase(/*String sql*/) {
        try {
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(SQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " +DB_NAME+ "");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            //TODO : Table users or Table persons?
            String sql = "CREATE TABLE IF NOT EXISTS users (" + "username_id INT( 10 ) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "password VARCHAR(200),"
                    + "first_name VARCHAR(200) NOT NULL,"
                    + "last_name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "position VARCHAR(200),";
         //           + "imageURL VARCHAR(200))";

            //check if we have users in the table users
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void insertUser(Person person) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (password, first_name, last_name, email, position) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getPassword());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setString(5, person.getPosition());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
//                lg.makeLog("A new user was inserted successfully.");
                System.out.println("A new List was inserted successfully.");
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertList(Person person) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //decide on variables and values
            String sql = "INSERT INTO task_list (first_name, last_name, department, major, email, imageURL) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
//            preparedStatement.setString(3, person.getDepartment());
//            preparedStatement.setString(4, person.getMajor());
//            preparedStatement.setString(5, person.getEmail());
//            preparedStatement.setString(6, person.getImageURL());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("A new List was inserted successfully.");
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
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
