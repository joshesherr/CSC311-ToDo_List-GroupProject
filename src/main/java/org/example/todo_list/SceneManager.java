package org.example.todo_list;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static final SceneManager INSTANCE  = new SceneManager();
    /**
     * Holds loaded scenes that can be shown.
     */
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private Stage primaryStage;

    public static SceneManager getInstance(){
        return INSTANCE;
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public void open() {
        primaryStage.show();
        primaryStage.requestFocus();
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
            e.printStackTrace();
            System.out.println("Failed to load scene "+fxmlSceneName+".fxml");
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

        primaryStage.setMaximized(true);

        if (!primaryStage.isMaximized()) {
            // Get the screen bounds to center the stage if it is not maximized
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getVisualBounds().getWidth();
            double screenHeight = screen.getVisualBounds().getHeight();

            // Calculate the position to center the stage
            double xPosition = (screenWidth - primaryStage.getWidth()) / 2;
            double yPosition = (screenHeight - primaryStage.getHeight()) / 2;

            // Set the stage position to the center of the screen
            primaryStage.setX(xPosition);
            primaryStage.setY(yPosition);
        }
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
