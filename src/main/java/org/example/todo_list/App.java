package org.example.todo_list;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/LoginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        scene.getStylesheets().add(App.class.getResource("/org/example/todo_list/styling/loginStyling.css").toString());
        stage.setTitle("JustDo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}