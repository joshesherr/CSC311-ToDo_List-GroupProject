package org.example.todo_list.view_models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.todo_list.db.ConnDB;
import org.example.todo_list.db.UserSession;
import org.example.todo_list.models.Task;
import org.example.todo_list.models.TaskList;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    public HBox listBox;
    public AnchorPane root;
    SceneManager sceneManager = SceneManager.getInstance();
    private static TaskController focusedTaskCon=null;
    private static TaskDetailsController taskDetailsCon;
    private String username;
    private TaskList taskList = new TaskList();
    private ListController listCon;
    private TaskController taskCon;
    private ConnDB connDB = new ConnDB();
    private ObservableList<TaskList> listsData = FXCollections.observableArrayList();
    private ObservableList<Task> taskData = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username = UserSession.getInstance().getUsername();
        listsData = connDB.loadingUsersLists(username);
        System.out.println("Loaded lists: " + listsData.size());

        ObservableList<TaskList> listsDataCopy = FXCollections.observableArrayList(listsData); // Create a copy of the list

        for (TaskList taskList : listsDataCopy) {
            taskData = connDB.loadingTasksData(taskList.getIdNum());
            System.out.println("Loaded tasks for list " + taskList.getIdNum() + ": " + taskData.size());
            taskList.setTasks(taskData);
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/List.fxml"));
                Parent listRoot = loader.load();
                listCon = loader.getController();
                listCon.setTaskList(taskList);
                listCon.parentController = this;
                listBox.getChildren().add(listRoot);
                System.out.println("Added list to UI: " + taskList.getIdNum());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Task task : taskList.getTasks()) {
                if (task.getListID() == taskList.getIdNum()) {
                    System.out.println(task.getTitle());
                try {
                    FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/Task.fxml"));
                    Parent taskRoot = loader.load();
                    TaskController taskCon = loader.getController();
                    taskCon.setTask(task);
                    listCon.taskBox.getChildren().add(taskRoot);
                    System.out.println("Added task to UI: " + task.getIdNum());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        }
    }

    public void logOut(ActionEvent actionEvent) {
        sceneManager.showScene("LoginScene");
    }

    @FXML
    void exitProgram(ActionEvent event) {
        System.exit(0);
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
            listCon = loader.getController();
            listCon.parentController = this;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTaskDetails() {

        if (taskDetailsCon == null) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/TaskDetails.fxml"));
                Parent taskDetailsRoot = (Parent) loader.load();
                root.getChildren().add(taskDetailsRoot);
                taskDetailsCon = loader.getController();
                taskDetailsRoot.setLayoutX(235);
                taskDetailsRoot.setLayoutY(420);
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