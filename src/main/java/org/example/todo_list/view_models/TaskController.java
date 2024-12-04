package org.example.todo_list.view_models;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.todo_list.SceneManager;
import org.example.todo_list.models.Priority;
import org.example.todo_list.models.Task;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TaskController implements Initializable {
    public TextField taskNameField;
    public VBox root;
    private AppController grandParentController;
    public ListController parentController;
    public ContextMenu optionsMenu;
    public Button optionsBtn;
    public ToggleButton taskToggleCheck;

    @FXML
    private Rectangle priorityColorRect;

    /**
     * The Task instance this view is representing
     */
    private Task task;

    public void setParentController(ListController parentController) {
        this.parentController = parentController;
    }

    //Todo Show options right above button. currently it is completely off screen
    public void showOptions(ActionEvent actionEvent) {
        optionsMenu.show(optionsBtn, optionsBtn.getLayoutX(), optionsBtn.getLayoutY());
    }

    /**
     * Will remove this task from this list it's currently in.
     */
    public void removeSelf() {
        parentController.removeTask(this);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        task = new Task();
//        taskToggleCheck.setSelected(task.getCompleted());


        //When a task is selected set it as the focused task in AppController.
        taskNameField.focusedProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) parentController.parentController.setFocusedTask(this);
            if (!newValue) {
                try {
                    task.setTitle(taskNameField.getText());
                    task.setListID(parentController.taskList.getIdNum());
                    System.out.println("Task name: " + task.getTitle());
                    System.out.println("List's ID: " + task.getListID());
                    task.saveToDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        taskNameField.textProperty().addListener((ov, oldValue, newValue) -> {
            task.setTitle(newValue);
            if (AppController.getTaskDetailsCon() != null) {
                AppController.getTaskDetailsCon().updateTaskDetails();
            }


        });

        taskToggleCheck.selectedProperty().addListener((ov, oldValue, newValue) -> {
            System.out.println(newValue);
            task.setCompleted(newValue);
            if (parentController != null) {
                parentController.updateProgress();
            }
            try {
                task.saveTaskCompletion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
        taskNameField.setText(task.getTitle());
        taskToggleCheck.setSelected(task.getCompleted());
    }

    public void updatePriorityColor(Color color) {
        if (priorityColorRect != null) {
            priorityColorRect.setFill(color);
        }
    }
}
