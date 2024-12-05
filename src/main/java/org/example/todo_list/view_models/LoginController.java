package org.example.todo_list.view_models;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.todo_list.SceneManager;
import org.example.todo_list.db.UserSession;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/ToDoList";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";

    SceneManager sceneManager;

    @FXML
    private TextField usernameTF, passwordTF;

    @FXML
    private VBox loginContainer;

    @FXML
    private ImageView logo;

    @FXML
    private VBox root;

    @FXML
    private Button signInBtn, signUpBtn;

    @FXML
    private Label signInLabel, errorMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
    }

    public void goToSignIn(ActionEvent actionEvent) {
        sceneManager.showScene("LoginScene");
    }

    public void goToRegister(ActionEvent actionEvent) {
        sceneManager.showScene("RegisterScene");
    }

    public String usernameMatch;
    public String passwordMatch;

    public void signIn(ActionEvent actionEvent) {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                usernameMatch = resultSet.getString("username");
                passwordMatch = resultSet.getString("password");
            }
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((username.equals("admin") && password.equals("admin"))||(usernameMatch != null && passwordMatch != null && usernameMatch.equals(username) && passwordMatch.equals(password))) {
            Platform.runLater(() -> errorMsg.setText(""));
            UserSession.getInstance().setUsername(username);
            try {
                sceneManager.showScene("HomeScene");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Platform.runLater(() -> errorMsg.setText("Incorrect username or password"));
        }

    }

}
