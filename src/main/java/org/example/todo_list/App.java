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
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon2.png")));
        stage.setTitle("Just Do");

        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage);
        
        sceneManager.loadScene("RegisterScene"); //Load register view early for more seamless transitions

        sceneManager.showScene("LoginScene"); //Show log in window
    }

    public static void main(String[] args) {
        launch();
    }
}