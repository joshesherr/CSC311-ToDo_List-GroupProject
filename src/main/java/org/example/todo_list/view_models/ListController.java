package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.todo_list.SceneManager;

import java.io.IOException;

public class ListController {
    public Button addTaskBtn;
    public Button optionsBtn;
    public VBox taskBox;
    public ContextMenu optionsMenu;
    public VBox root;
    public AppController parentController;

    public void addTaskBtnPressed(ActionEvent actionEvent) {
        addTask();
    }

    /**
     * Create a new task for this list.
     */
    public void addTask() {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/Task.fxml"));
            taskBox.getChildren().addFirst((Parent) loader.load());
            TaskController taskCon = loader.getController();
            taskCon.parentController = this;
            taskCon.taskNameField.requestFocus();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a task from this list.
     * @param task The task to delete.
     */
    public void removeTask(Node task) {
        taskBox.getChildren().remove(task);
    }

    //Todo Show options right above button. currently it is completely off screen
    public void showOptions(ActionEvent actionEvent) {
        optionsMenu.show(optionsBtn, optionsBtn.getLayoutX(), optionsBtn.getLayoutY());
    }

    /**
     * Will remove this list from the home screen.
     */
    public void removeSelf() {
        parentController.removeList(root);
    }

}
