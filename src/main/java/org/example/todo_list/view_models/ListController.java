package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import org.example.todo_list.SceneManager;
import org.example.todo_list.db.UserSession;
import org.example.todo_list.models.TaskList;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public Button addTaskBtn;
    public Button optionsBtn;
    public VBox taskBox;
    public ContextMenu optionsMenu;
    public VBox root;
    public AppController parentController;
    public ProgressBar progressBar;
    private String username;


    @FXML
    private TextField listName;

    /**
     * The TaskList instance this view is representing
     */
    private TaskList taskList;

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
            taskList.addTask( taskCon.getTask() );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        updateProgress();
    }

    /**
     * Delete a task from this list.
     * @param task The task to delete.
     */
    public void removeTask(TaskController task) {
        taskList.removeTask(task.getTask());
        taskBox.getChildren().remove(task.root);
        updateProgress();
    }

    //Todo Show options right above button. currently it is completely off screen
    public void showOptions(ActionEvent e) {
        System.out.println(e);
        //
    }

    /**
     * Will remove this list from the home screen.
     */
    public void removeSelf() {
        parentController.removeList(this);
    }

    /**
     * Update this list views progress bar.
     */
    public void updateProgress() {
        progressBar.setProgress(taskList.getProgress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username = UserSession.getInstance().getUsername();
        taskList = new TaskList();

        listName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    taskList.setName(listName.getText());
                    taskList.setUsername(username);
                    taskList.saveToDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        optionsBtn.setOnMouseClicked(e -> {
            optionsMenu.show(optionsBtn, SceneManager.getInstance().getPrimaryStage().getX() + e.getSceneX(), SceneManager.getInstance().getPrimaryStage().getY()+ e.getSceneY());
        });
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
        listName.setText(taskList.getName());
        updateProgress();
    }
}
