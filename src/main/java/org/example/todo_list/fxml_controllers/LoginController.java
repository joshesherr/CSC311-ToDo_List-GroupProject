package org.example.todo_list.fxml_controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LoginController {

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

}
