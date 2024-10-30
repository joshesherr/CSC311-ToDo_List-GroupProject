package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.todo_list.SceneManager;

public class AppController {

    SceneManager sceneManager = SceneManager.getInstance();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void logOut(ActionEvent actionEvent) {
        sceneManager.showScene("LoginScene");
    }
}