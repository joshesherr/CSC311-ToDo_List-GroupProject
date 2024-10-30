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
    public boolean loadScene(String fxmlSceneName) {
        try {
            // Load the FXML file and create a Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/"+fxmlSceneName+".fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Store the scene by name
            scenes.put(fxmlSceneName, scene);
            return true;
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Failed to load scene "+fxmlSceneName);
            return false;
        }
    }

    /**
     * Shows a new view. If the view is already loaded it will try to load it.
     * @param sceneName the name of the fxml file you wish to show (without '.fxml')
     */
    public void showScene(String sceneName) {
        //If scene isn't loaded try to load it. Do not continue if scene can't be loaded.
        if (!scenes.containsKey(sceneName)) if (!loadScene(sceneName)) return;

        Scene scene = scenes.get(sceneName);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
