package org.example.todo_list.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.todo_list.models.Person;
import org.example.todo_list.models.TaskList;
import org.example.todo_list.view_models.RegisterController;

import java.sql.*;

public class ConnDB {

    final String MYSQL_SERVER_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/ToDoList";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";

    Person p = null;
    private final ObservableList<TaskList> listsData = FXCollections.observableArrayList();



    /**
     * Connect to the database and create the database and table if not created, accept the sql query for creating the table
     */
    public  void connectToServer() {
        try {
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS ToDoList");
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create the table if not created, accept the sql query for creating the table
     */
    public void createTablePerson() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "username VARCHAR(100) NOT NULL PRIMARY KEY,"
                    + "first_name VARCHAR(200) NOT NULL,"
                    + "last_name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE,"
                    + "password VARCHAR(200))";
            statement.executeUpdate(sql);
            //check if we have users in the table users
            statement = conn.createStatement();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(Person p) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (username, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p.getUsername_ID());
            preparedStatement.setString(2, p.getFirstName());
            preparedStatement.setString(3, p.getLastName());
            preparedStatement.setString(4, p.getEmail());
            preparedStatement.setString(5, p.getPassword());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void insertTaskList(TaskList taskList) {
    }

    public void createTableTask() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS tasks (" + "id VARCHAR(100) NOT NULL PRIMARY KEY,"
                    + "start_date DATE NOT NULL,"
                    + "end_date DATE NOT NULL,"
                    + "description VARCHAR(500))";

            statement.executeUpdate(sql);
            statement.executeUpdate(sql);
            //check if we have users in the table users
            statement = conn.createStatement();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




//    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//    String sql = "SELECT * FROM users WHERE username = ?";
//    PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//
//    ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//        usernameMatch = resultSet.getString("username");
//        passwordMatch = resultSet.getString("password");
//    }
//            preparedStatement.close();
//            conn.close();

    public ObservableList<TaskList> queryAllLists(String username) {
        connectToServer();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM everything_about_list WHERE person_username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String list_name = resultSet.getString("list_name");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listsData;
    }

//    public ObservableList<Person> getData() {
//        connectToDatabase();
//        try {
//            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            String sql = "SELECT * FROM users ";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (!resultSet.isBeforeFirst()) {
//                lg.makeLog("No data");
//            }
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                String first_name = resultSet.getString("first_name");
//                String last_name = resultSet.getString("last_name");
//                String department = resultSet.getString("department");
//                String major = resultSet.getString("major");
//                String email = resultSet.getString("email");
//                String imageURL = resultSet.getString("imageURL");
//                data.add(new Person(id, first_name, last_name, department, major, email, imageURL));
//            }
//            preparedStatement.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }

}
