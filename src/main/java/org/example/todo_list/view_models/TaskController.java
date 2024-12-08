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
        try {
            this.task.deleteTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        parentController.removeTask(this);
    }

    public void removeSelfForPriority() {
        parentController.removeTask(this);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        task = new Task();

        //When a task is selected set it as the focused task in AppController.
        taskNameField.focusedProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    task.setListID(parentController.taskList.getIdNum());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            AppController.setFocusedTask(this);
        });

        taskToggleCheck.selectedProperty().addListener((ov, oldValue, newValue) -> {
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
        Priority priority = task.getPriority();
        taskNameField.setText(task.getTitle());
        taskToggleCheck.setSelected(task.getCompleted());
        System.out.println("---New Task---");
        System.out.println("Parent List ID: " + task.getListID() + ", Task ID: " + task.getIdNum());
        System.out.println("Task name: " + task.getTitle());
        System.out.println("Setting task priority color: " + priority.getColor());
        System.out.println("Setting task priority: " + priority);
        this.updatePriorityColor(priority.getColor());
    }

    public void updatePriorityColor(Color color) {
        if (priorityColorRect != null) {
            priorityColorRect.setFill(color);
        }
    }
}
