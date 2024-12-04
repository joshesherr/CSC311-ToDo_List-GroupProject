package org.example.todo_list.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.todo_list.models.Person;
import org.example.todo_list.models.Task;
import org.example.todo_list.models.TaskList;
import org.example.todo_list.view_models.RegisterController;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConnDB {

    final String MYSQL_SERVER_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/";
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/ToDoList";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";

    Person p = null;
    private final ObservableList<TaskList> listsData = FXCollections.observableArrayList();
    private final ObservableList<Task> taskData = FXCollections.observableArrayList();



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

    //----------------------------------USERS'S SECTION--------------------------------------------------------------------------
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

    /**
     * Insert a new user into the database
     * @param p
     */
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

    //----------------------------------TASK LISTS' SECTION--------------------------------------------------------------------------
    /**
     * Check if the list exist in the DB, update the list's name if it does, insert a new list if it doesn't
     * @param taskList
     */
    public void saveTaskListChanges(TaskList taskList) {
        if (taskList.getName() == null || taskList.getName().isEmpty()) {
            return;
        }
        if (taskList.getIdNum() != -1) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "UPDATE list SET list_name = ? WHERE id_num = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, taskList.getName());
                preparedStatement.setInt(2, taskList.getIdNum());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "INSERT INTO list (list_name) VALUES(?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, taskList.getName());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int listId = generatedKeys.getInt(1);
                    taskList.setIdNum(listId);
                }

                preparedStatement.close();


                sql = "INSERT INTO works_on (person_username, list_id) VALUES (?, ?)";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, taskList.getUsername());
                preparedStatement.setInt(2, taskList.getIdNum());
                preparedStatement.executeUpdate();

                preparedStatement.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Loading the user's lists information from the database into the ObservableList
     * @param username
     * @return ObservableList<TaskList>
     */
    public ObservableList<TaskList> loadingUsersLists(String username) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT list.id_num, list.list_name FROM works_on INNER JOIN list ON works_on.list_id = list.id_num WHERE works_on.person_username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_num = Integer.parseInt(resultSet.getString("id_num"));
                String list_name = resultSet.getString("list_name");
                listsData.add(new TaskList(id_num, list_name, username));
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listsData;
    }

    /**
     * Delete the list from the database, using the list's id number
     * @param taskList
     */
    public void deleteList(TaskList taskList) {
        int id_num = taskList.getIdNum();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // First step, delete the related row in the task table
            String sql = "DELETE FROM task WHERE list_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id_num);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            //Second step, delete the related row in the works_on table!
            sql = "DELETE FROM works_on WHERE list_id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id_num);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            //Third step, delete the related row in the list table!
            sql = "DELETE FROM list WHERE id_num = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id_num);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------TASKS' SECTION--------------------------------------------------------------------------

    /**
     * Updates task's name or creates a new task with its name, id_num, start_date, and corresponding list_id
     * @param task
     */
    public void saveTaskChanges(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return;
        }
        if (task.getIdNum() != -1) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "UPDATE task SET name = ? WHERE id_num = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setInt(2, task.getIdNum());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "INSERT INTO task (name, start_date, list_id) VALUES(?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, String.valueOf(task.getStartDateTime()));
                preparedStatement.setInt(3, task.getListID());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int taskId = generatedKeys.getInt(1);
                    task.setIdNum(taskId);
                }

                preparedStatement.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loadind task's information when the app starts.
     * @param list_id
     * @return ObservableList<Task>
     */
    public ObservableList<Task> loadingTasksData(int list_id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM task WHERE list_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, list_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if (resultSet == null) {
                return null;
            }

            while (resultSet.next()) {
                int id_num = resultSet.getInt("id_num");
                String name = resultSet.getString("name");
                String start_date = resultSet.getString("start_date");
                LocalDateTime startDate = start_date != null ? LocalDateTime.parse(start_date, formatter) : null;
                String end_date = resultSet.getString("end_date");
                LocalDateTime endDate = end_date != null ? LocalDateTime.parse(end_date, formatter) : null;
                String description = resultSet.getString("description");
                boolean completed = resultSet.getBoolean("completed");
                if (name != null) {
                    taskData.add(new Task(id_num, name, startDate, list_id, endDate, description, completed));
                }
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskData;
    }

    public void updatesTaskCompletion(Task task) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE task SET completed = ? WHERE id_num = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setBoolean(1, task.getCompleted());
            preparedStatement.setInt(2, task.getIdNum());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTaskDescription(Task task) {

    }



}
