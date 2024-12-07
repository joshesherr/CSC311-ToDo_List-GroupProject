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
import org.example.todo_list.comparators.SortByDate;
import org.example.todo_list.comparators.SortByName;
import org.example.todo_list.comparators.SortByPriority;
import org.example.todo_list.db.ConnDB;
import org.example.todo_list.db.UserSession;
import org.example.todo_list.models.Task;
import org.example.todo_list.models.TaskList;
import org.example.todo_list.models.Priority;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    public HBox listBox;
    @FXML
    public AnchorPane root;
    SceneManager sceneManager = SceneManager.getInstance();
    private static TaskController focusedTaskCon=null;
    private static TaskDetailsController taskDetailsCon;
    private String username;
    private ListController listCon;
    private TaskController taskCon;
    private ConnDB connDB = new ConnDB();
    private ObservableList<TaskList> listsData = FXCollections.observableArrayList();
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    private static Task copiedTask;

    @FXML
    private ToggleGroup priorityShown;

    public static void setCopiedTask(Task task) {
        copiedTask = task;
    }
    public static Task getCopiedTask() {
        return copiedTask;
    }
    public static AppController INSTANCE;

    public static AppController getInstance() {
        return INSTANCE;
    }


    @FXML
    private Button viewTaskBtn, personalTasksBtn, importantTasksBtn, homeStuffTasksBtn, addListBtn, allTasksBtn, criticalTasksBtn, daysTasksBtn,
            groupTasksBtn, homeBtn, monthTasksBtn, weekTasksBtn, prioHighBtn;

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
    private VBox listTaskContainer;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        INSTANCE = this;

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

        loadTasksFromDB();
       // copyMenuItem.setDisable(AppController.getFocusedTask() == null);
     //   pasteMenuItem.setDisable(getCopiedTask() == null);
    }

    public void loadTasksFromDB() {
        username = UserSession.getInstance().getUsername();
        listsData = connDB.loadingUsersLists(username);
        welcomeLbl.setText("Welcome " + username + "!");
        ObservableList<TaskList> listsDataCopy = FXCollections.observableArrayList(listsData); // Create a copy of the list

        for (TaskList taskList : listsData) {
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
    }

    @FXML
    void copyTaskPressed(ActionEvent event) {
        System.out.println("Copy MI Pressed");
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


    void showOnlyPriority(int priorityLevel) {
        ObservableList<Task> filteredTasks = FXCollections.observableArrayList();
        for (Task task : taskData) {
            if (task.getPriority().getLevel() == priorityLevel) {
                filteredTasks.add(task);
                System.out.println("Filtered out: [" + task.getTitle() + "] Prio: [" + task.getPriority() +"]");
            }
        }
        // Clear the current task container
        listBox.getChildren().clear();
        loadingTaskByPriority(filteredTasks);
    }

    @FXML
    private void prioLow() {
        showOnlyPriority(Priority.LOW.getLevel());
    }

    @FXML
    private void prioMed() {
        showOnlyPriority(Priority.MEDIUM.getLevel());
    }

    @FXML
    private void prioHigh() {
        showOnlyPriority(Priority.HIGH.getLevel());
    }

    @FXML
    private void prioCrit() {
        showOnlyPriority(Priority.CRITICAL.getLevel());
    }


    @FXML
    void homeClicked(ActionEvent event) {
        // Clear the current task containers and reset to original application state
        priorityShown.getSelectedToggle().setSelected(false);
        listBox.getChildren().clear();
        loadingTaskByPriority(taskData);
    }

    public void loadingTaskByPriority(ObservableList<Task> filteredTasks) {
        for (TaskList taskList : listsData) {
            // Use stream to check if there are any tasks for this list
            boolean hasTasksForList = filteredTasks.stream()
                    .anyMatch(task -> task.getListID() == taskList.getIdNum());

            // If there are no tasks for this list, skip it
            if (!hasTasksForList) {
                continue;
            }

            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("views/components/List.fxml"));
                Parent listRoot = loader.load();
                listCon = loader.getController();
                listCon.setTaskList(taskList);
                listCon.parentController = this;
                listBox.getChildren().add(listRoot);

                // Add filtered tasks to the task container within the list
                for (Task task : filteredTasks) {
                    if (task.getListID() == taskList.getIdNum()) {
                        FXMLLoader taskLoader = new FXMLLoader(SceneManager.class.getResource("views/components/Task.fxml"));
                        Parent taskRoot = taskLoader.load();
                        TaskController taskCon = taskLoader.getController();
                        taskCon.setTask(task);
                        taskCon.setParentController(listCon); // Set the grandParent controller
                        listCon.taskBox.getChildren().add(taskRoot); // Add task to the task container
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  
    /**
     * Sorts tasks depending on the comparator.
     * @param comparator The task sorting comparator to use.
     */
    public void sortBy(Comparator comparator) {
        listBox.getChildren().forEach((list)->{
            List<TaskController> tasks = new ArrayList<>();
            ListController listCon = (ListController) list.getUserData();
            listCon.taskBox.getChildren().forEach((t)->{
                tasks.add((TaskController) t.getUserData());
            });
            tasks.sort(comparator);
            listCon.taskBox.getChildren().clear();
            for (TaskController task : tasks) {
                listCon.taskBox.getChildren().add(task.root);
            }
        });
    }

    public void sortByPriority() {
        sortBy(new SortByPriority());
    }

    public void sortByDate() {
        sortBy(new SortByDate());
    }

    public void sortByName() {
        sortBy(new SortByName());
    }
}