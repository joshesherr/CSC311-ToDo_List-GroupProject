package org.example.todo_list.view_models;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.todo_list.db.ConnDB;
import org.example.todo_list.models.Person;

import java.sql.*;

public class RegisterController {
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/ToDoList";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";

    private ConnDB DBconnection = new ConnDB();
    private Person p;

    @FXML
    private TextField usernameRF, firstNameRF, lastNameRF, emailRF, passwordRF;

    @FXML
    private Button goToSignIn, registerBtn;

    @FXML
    private VBox loginContainer;

    @FXML
    private AnchorPane root;

    @FXML
    private Label signInLabel, errorMsg;



    /**
     * Set up the initial state of the GUI, add listeners to input fields, and disable the register button until all fields are valid.
     */
    @FXML
    public void initialize() {
        // Add listeners to input fields
        usernameRF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        firstNameRF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        lastNameRF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        emailRF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        passwordRF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());

        // Initially disable the register button
        registerBtn.setDisable(true);

    }

    private void validateInputs() {
        String username = usernameRF.getText();
        String firstName = firstNameRF.getText();
        String lastName = lastNameRF.getText();
        String emailText = emailRF.getText();
        String passwordText = passwordRF.getText();

        if (username.isEmpty()) {
            Platform.runLater(() -> errorMsg.setText("Username field must not be empty"));
            return;
        }

        if (!firstName.isEmpty() && (!isNameValid(firstName))) {
            Platform.runLater(() -> errorMsg.setText("Invalid first name."));
            return;
        } else Platform.runLater(() -> errorMsg.setText(""));


        if (!lastName.isEmpty() && (!isNameValid(lastName))) {
            Platform.runLater(() -> errorMsg.setText("Invalid last name."));
            return;
        } else Platform.runLater(() -> errorMsg.setText(""));


        if (!emailText.isEmpty() && (!isEmailValid(emailText))) {
            Platform.runLater(() -> errorMsg.setText("Invalid email."));
            return;
        } else errorMsg.setText("");

        if (!passwordText.isEmpty() && (!isPasswordValid(passwordText))) {
            Platform.runLater(() -> errorMsg.setText("Invalid password."));
            return;
        } else Platform.runLater(() -> errorMsg.setText(""));



        boolean isValid = isNameValid(firstName) &&
                isNameValid(lastName) &&
                isEmailValid(emailText) &&
                isPasswordValid(passwordText);

        registerBtn.setDisable(!isValid);
    }

    private boolean isPasswordValid(String passwordText) {
        final String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{4,}$";
        return passwordText.matches(regex);
    }


    /**
     * Check if the email is valid (should be in the format <word>@farmingdale.edu)
     * @param email
     * @return
     */
    private boolean isEmailValid(String email) {
        final String regex = "(\\w+)@([a-zA-Z]+).([a-zA-Z]{3})";
        return email.matches(regex);
    }

    /**
     * Check if the name is valid (should be between 2 and 25 characters long)
     * @param name
     * @return
     */
    private boolean isNameValid(String name) {
        final String regex = "([a-zA-Z]{2,25})";
        return name.matches(regex);
    }

    @FXML
    void createAccount(ActionEvent event) {
        p = new Person(usernameRF.getText(), firstNameRF.getText(), lastNameRF.getText(), emailRF.getText(), passwordRF.getText());

        DBconnection.connectToServer();
        DBconnection.createTablePerson();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Check if the username already exists
            String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, p.getUsername_ID());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                Platform.runLater(() -> errorMsg.setText("Username already exists"));
                return;
            }
            DBconnection.insertUser(p);
            checkStmt.close();
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToSignIn(ActionEvent event) {

    }
}
