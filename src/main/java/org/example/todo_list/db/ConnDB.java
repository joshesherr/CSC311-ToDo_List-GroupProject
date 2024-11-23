package org.example.todo_list.db;

import org.example.todo_list.models.Person;
import org.example.todo_list.models.Task;
import org.example.todo_list.models.TaskList;
import java.time.*;

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

    //TODO: How does person connect to tasks? Figure this out -> Whats the right way to pass this method
    public void insertList(TaskList tl) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //List storage Questions
            //Need to decide on variables and values -> Or just go for functionality first
            //How can i store multiple tasks, sharedUser ID's, and tags here?
            //-> SharedUsers probably should happen later, dont happen when task list is created?
            //-> Tags can possibly be stored in a related table that has 5 VARCHARS for each
            //Current method does not store anything beyond title and progress bar value
            String sql = "INSERT INTO task_list (title, progress) VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tl.getTitle());
            preparedStatement.setString(2, String.valueOf(tl.getProgress()));
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

    //TODO: Decide if DateTimes stored as objects or strings with a formatter?
    public void insertTask(Task task) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //-> Tags can possibly be stored in a related table that has 5 VARCHARS for each
            String sql = "INSERT INTO task_list (title, startDateTime, endDateTime, description, priority, completed) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setObject(2, LocalDateTime.now());
            preparedStatement.setObject(3, task.getEndDateTime());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setString(5, String.valueOf(task.getPriority()));    //Priority currently being converted to a string
            preparedStatement.setBoolean(6, task.isCompleted());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("A new Task " + task.getTitle() + " was created successfully.");
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param id id of user being edited
     * @param p user of person class being edited, info gotten from this person
     */
    public void editUser(int id, Person p) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET password=?, first_name=?, last_name=?, email=?, position=?, WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p.getPassword());
            preparedStatement.setString(2, p.getFirstName());
            preparedStatement.setString(3, p.getLastName());
            preparedStatement.setString(4, p.getEmail());
            preparedStatement.setString(5, p.getPosition());
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                System.out.println("A new User was inserted to the DB successfully.");
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

    //Method to retrieve id from database where it is auto-incremented via email search.
    /**
     * @param p user being searched
    // * @param id of user to be returned
     */
    public int retrieveId(Person p) {
        connectToDatabase();
        int id;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT id FROM users WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Searched user with email [" + p.getEmail() + "] found. [UsernameID : "
                +  p.getUsername_ID() + "] returned successfully.");
        //lg.makeLog(String.valueOf(id));
        return id;
    }

}
