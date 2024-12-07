package org.example.todo_list;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo/icon_32px.png")));
        stage.setTitle("Just Do");
        stage.setResizable(true);

        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.setPrimaryStage(stage);

        sceneManager.loadScene("RegisterScene"); //Load register view early for more seamless transitions
        sceneManager.showScene("LoginScene", 800, 680); //Show log in window
    }


    /**
     * The starting point for our cool app.
     */
    public static void main(String[] args) throws IOException, AWTException {
        BackgroundProcessManager.getInstance().startBackgroundProcess();
        launch();
    }
}