package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.todo_list.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    SceneManager sceneManager;

    @FXML
    private TextField emailTF, passwordTF;

    @FXML
    private VBox loginContainer;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane root;

    @FXML
    private Button signInBtn, signUpBtn;

    @FXML
    private Label signInLabel;

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

    public void signIn(ActionEvent actionEvent) {
        //Todo check and validate user info from Database.
    }

    public void createAccount(ActionEvent actionEvent) {
        //Todo create an account and save user info into Database.
    }
}
