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
    private static Task copiedTask;
    public static void setCopiedTask(Task task) {
        copiedTask = task;
    }
    public static Task getCopiedTask() {
        return copiedTask;
    }

    @FXML
    private Button viewTaskBtn, personalTasksBtn, importantTasksBtn, homeStuffTasksBtn, addListBtn, allTasksBtn, criticalTasksBtn, daysTasksBtn, groupTasksBtn, homeBtn, monthTasksBtn, weekTasksBtn;

    @FXML
    private Label viewTasksLabel, myTAsksLabel, calendarLabel, tagsLabel, taskDueDate, taskName, taskPriority, taskLabel, welcomeLbl;

    @FXML
    private CheckBox checkBox;

    @FXML
    private GridPane gridPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuBarCreate, menuBarEdit, menuBarFile, menuBarGroup, menuBarHelp;

    @FXML
    private MenuItem copyMenuItem, pasteMenuItem;

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
        welcomeLbl.setText("Welcome " + username + "!"); //username? name? probably username removable comment

        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/TaskDetails.fxml"));
            Parent taskDetailsRoot = (Parent) loader.load();
            root.getChildren().add(taskDetailsRoot);
            taskDetailsCon = loader.getController();
            taskDetailsCon.root.setLayoutX(250);
            taskDetailsCon.root.setLayoutY(410);
            taskDetailsCon.root.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<TaskList> listsDataCopy = FXCollections.observableArrayList(listsData); // Create a copy of the list
        for (TaskList taskList : listsDataCopy) {
            taskData = connDB.loadingTasksData(taskList.getIdNum());
            taskList.setTasks(taskData);
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/List.fxml"));
                Parent listRoot = loader.load();
                listCon = loader.getController();
                listCon.setTaskList(taskList);
                listCon.parentController = this;
                listBox.getChildren().add(listRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Task task : taskList.getTasks()) {
                if (task.getListID() == taskList.getIdNum()) {
                try {
                    FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/Task.fxml"));
                    Parent taskRoot = loader.load();
                    TaskController taskCon = loader.getController();
                    taskCon.setTask(task);
                    taskCon.setParentController(listCon); // Set the grandParent controller
                    listCon.taskBox.getChildren().add(taskRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        }

       // copyMenuItem.setDisable(AppController.getFocusedTask() == null);
     //   pasteMenuItem.setDisable(getCopiedTask() == null);
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

    @FXML
    void pasteTaskPressed(ActionEvent event) {
        System.out.println("Paste MI Pressed");
        //disable menu item while copied task not set
//        if (getCopiedTask() == null) {
//            //disable menu item
//            System.out.println("No task copied");
//            return;
//          //  pasteMenuItem;
//        } else {
//            //enable (probably should be listener
//            Task copiedTask = getCopiedTask();
//
//            System.out.println("Focus ID " + AppController.getFocusedTask().getTask().getIdNum());
//            System.out.println("Copy ID " + copiedTask.getIdNum());
//            System.out.println("Copy List ID" + copiedTask.getListID());
//            System.out.println("Focus List ID" + AppController.getFocusedTask().getTask().getListID());
//                int tempTaskIDNum = getFocusedTask().getTask().getIdNum();
//                int tempListID = getFocusedTask().getTask().getListID();
//                //Set copied tasks ID values to new ID values of the location of the pasted task
//                copiedTask.setIdNum(tempTaskIDNum);
//                copiedTask.setListID(tempListID);
//                getFocusedTask().setTask(copiedTask);
//            System.out.println("----Post Paste ID vals----");
//            System.out.println("Focus ID " + AppController.getFocusedTask().getTask().getIdNum());
//            System.out.println("Copy ID " + copiedTask.getIdNum());
//            System.out.println("Copy List ID" + copiedTask.getListID());
//            System.out.println("Focus List ID" + AppController.getFocusedTask().getTask().getListID());
//                getTaskDetailsCon().updateTaskDetails(copiedTask);
//                //updateTaskDetails(copiedTask);
//                System.out.println("No task to paste.");
//            }
        //validate valid copy, paste in to focused list

    }

    @FXML
    void copyTaskPressed(ActionEvent event) {
        System.out.println("Copy MI Pressed");
        //disable menu item while invalid by listener
//        if (AppController.getFocusedTask().getTask() == null) {
//            return;
//        }
//        //validate focused task, copy focused task
//            AppController.setCopiedTask(getFocusedTask().getTask());
//            System.out.println("Task copied by Menu Item " + AppController.getCopiedTask());
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

    public static void showTaskDetails() {
        taskDetailsCon.root.setVisible(true);
        taskDetailsCon.updateTaskDetails(AppController.getFocusedTask().getTask());
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

    public static void setFocusedTask(TaskController taskCon) {
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