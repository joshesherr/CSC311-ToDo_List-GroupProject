package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import org.example.todo_list.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    public HBox listBox;
    public AnchorPane root;
    SceneManager sceneManager = SceneManager.getInstance();
    private static TaskController focusedTaskCon=null;
    private static TaskDetailsController taskDetailsCon;

    @FXML
    private Button viewTaskBtn, personalTasksBtn, importantTasksBtn, homeStuffTasksBtn, addListBtn, allTasksBtn, criticalTasksBtn, daysTasksBtn, groupTasksBtn, homeBtn, monthTasksBtn, weekTasksBtn;

    @FXML
    private Label viewTasksLabel, myTAsksLabel, calendarLabel, tagsLabel, taskDueDate, taskName, taskPriority, taskLabel;

    @FXML
    private CheckBox checkBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuBarCreate, menuBarEdit, menuBarFile, menuBarGroup, menuBarHelp;

    @FXML
    private BorderPane innerTaskBP, outerBP;

    @FXML
    private TextArea taskDescription;

    @FXML
    private ProgressBar taskProgressBar;

    @FXML
    private VBox taskContainer, listTaskContainer;

    @FXML
    private ScrollPane scrollPane;

    public void logOut(ActionEvent actionEvent) {
        sceneManager.showScene("LoginScene");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Todo name lists on creation
    public void addListBtnPressed(ActionEvent actionEvent) {
        addList();
    }

    /**
     * Adds a new list to this home screen.
     */
    public void addList() {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/List.fxml"));
            listBox.getChildren().addFirst((Parent) loader.load());
            ListController listCon = loader.getController();
            listCon.parentController = this;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTaskDetails() {

        if (taskDetailsCon == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/TaskDetails.fxml"));
                root.getChildren().add((Parent) loader.load());
                taskDetailsCon = loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        taskDetailsCon.root.setVisible(true);
        taskDetailsCon.updateTaskDetails();

    }

    /**
     * removes a list from the home screen.
     * @param list The list to remove.
     */
    public void removeList(ListController list) {
        listBox.getChildren().remove(list.root);
    }

    public static TaskController getFocusedTask() {
        return focusedTaskCon;
    }
    public void setFocusedTask(TaskController taskCon) {
        focusedTaskCon = taskCon;
        showTaskDetails();
    }
    public static TaskDetailsController getTaskDetailsCon() {
        return taskDetailsCon;
    }
    public static void setTaskDetailsCon(TaskDetailsController taskDetailsCon) {
        AppController.taskDetailsCon = taskDetailsCon;
    }
}