package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextFlow;
import org.example.todo_list.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController2 implements Initializable {

    SceneManager sceneManager = SceneManager.getInstance();

    @FXML
    private Button viewTaskBtn, personalTasksBtn, importantTasksBtn, homeStuffTasksBtn, addTaskBtn, allTasksBtn, criticalTasksBtn, daysTasksBtn, groupTasksBtn, homeBtn, monthTasksBtn, weekTasksBtn;

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
    private TextFlow taskDescription;

    @FXML
    private ProgressBar taskProgressBar;

    @FXML
    private Polygon taskTag;

    @FXML
    private VBox taskContainer, listTaskContainer;

    public void logOut(ActionEvent actionEvent) {
        sceneManager.showScene("LoginScene");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void addTask(ActionEvent event) {
        sceneManager.loadScene("addTaskScene");
        sceneManager.showScene("addTaskScene");
    }
}