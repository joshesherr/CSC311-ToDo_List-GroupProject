package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import org.example.todo_list.SceneManager;
import org.example.todo_list.db.ConnDB;
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
    private ConnDB connDB = new ConnDB();


    @FXML
    private TextField listName;

    @FXML
    private HBox addTaskBox;

    /**
     * The TaskList instance this view is representing
     */
    public TaskList taskList;

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
        Tooltip tooltip = new Tooltip("Click to add a new task");
        Tooltip.install(addTaskBox, tooltip);

        optionsBtn.setOnMouseClicked(e -> {
            optionsMenu.show(optionsBtn, SceneManager.getInstance().getPrimaryStage().getX() + e.getSceneX(), SceneManager.getInstance().getPrimaryStage().getY()+ e.getSceneY());
        });
    }

    public void addTaskBtnPressed(ActionEvent actionEvent) {
        addTask();
    }

    @FXML
    void addTaskBoxPressed(MouseEvent event) {
        addTask();
    }

    @FXML
    void exitAddTaskBox(MouseEvent event) {
        addTaskBox.setCursor(Cursor.DEFAULT);        // Reset cursor to default when leaving
    }

    @FXML
    void hoverAddTaskBox(MouseEvent event) {
        addTaskBox.setCursor(Cursor.HAND);        // Change cursor to hand when hovering
    }

    @FXML
    void listClicked(MouseEvent event) {
//        parentController.setActiveListController(this);
    }

    @FXML
    void renameList(ActionEvent event) {
        listName.requestFocus();
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
            taskList.addTask(taskCon.getTask());
            updateProgress();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
    }


    /**
     * Will remove this list from the home screen.
     */
    public void removeSelf() {
        connDB.deleteList(this.taskList);
        parentController.removeList(this);
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
        listName.setText(taskList.getName());
        updateProgress();
    }

    /**
     * Update this list views progress bar.
     */
    public void updateProgress() {
        int listId = taskList.getIdNum();
        double progress = taskList.getProgress();
        progressBar.setProgress(progress);
        //progressBar.setProgress(taskList.getProgress());
    }
    public TextField getListNameTextField() {
        return listName;
    }
}
