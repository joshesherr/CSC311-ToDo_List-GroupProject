package org.example.todo_list;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static final SceneManager INSTANCE  = new SceneManager();;
    /**
     * Holds loaded scenes that can be shown.
     */
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private Stage primaryStage;

    public static SceneManager getInstance(){
        return INSTANCE;
    }
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Loads a fxml file to be shown later.
     * @param fxmlSceneName the name of the fxml file you wish to load (without '.fxml')
     */
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

    /**
     * Shows a new view. The view must have been loaded previously.
     * @param sceneName the name of the fxml file you wish to show (without '.fxml')
     */
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
