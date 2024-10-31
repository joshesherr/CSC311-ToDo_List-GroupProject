package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.todo_list.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    SceneManager sceneManager = SceneManager.getInstance();

    public void logOut(ActionEvent actionEvent) {
        sceneManager.showScene("LoginScene");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}