package org.example.todo_list;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = SceneManager.getInstance();

        Image icon = new Image(getClass().getResourceAsStream("images/icon2.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Just Do");

        sceneManager.setPrimaryStage(stage);
        //Load log in and register views early
        sceneManager.loadScene("LoginScene");
        sceneManager.loadScene("RegisterScene");
        //Show log in window
        sceneManager.showScene("LoginScene");
    }

    public static void main(String[] args) {
        launch();
    }
}