package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.todo_list.SceneManager;

import java.io.IOException;

public class ListController {
    public Button addTaskBtn;
    public Button optionsBtn;
    public VBox taskBox;


    public void addTask(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/Task.fxml"));

            taskBox.getChildren().addFirst((Parent) loader.load());

            TaskController taskCon = loader.getController();

            taskCon.taskNameField.requestFocus();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOptions(ActionEvent actionEvent) {
    }


    //Todo make List name Editable.

}
