package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TaskController {
    public TextField taskNameField;
    public VBox root;
    public ListController parentController;
    public ContextMenu optionsMenu;
    public Button optionsBtn;

    //Todo Show options right above button. currently it is completely off screen
    public void showOptions(ActionEvent actionEvent) {
        optionsMenu.show(optionsBtn, optionsBtn.getLayoutX(), optionsBtn.getLayoutY());
    }

    /**
     * Will remove this task from this list it's currently in.
     */
    public void removeSelf() {
        parentController.removeTask(root);
    }

}
