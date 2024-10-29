package org.example.todo_list;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static SceneManager INSTANCE;
    private Stage primaryStage;

    private HashMap<String, Scene> scenes = new HashMap<>();
    public static SceneManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SceneManager();
        }
        return INSTANCE;
    }
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void loadScene(String fxmlSceneName) {
        try {
            // Load the FXML file and create a Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/"+fxmlSceneName+".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Store the scene by name
            scenes.put(fxmlSceneName, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showScene(String sceneName) {
        Scene scene = scenes.get(sceneName);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("Scene not found: " + sceneName);
        }
    }
}
