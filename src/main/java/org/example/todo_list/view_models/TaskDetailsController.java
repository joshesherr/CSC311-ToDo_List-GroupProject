package org.example.todo_list.view_models;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.todo_list.SceneManager;
import org.example.todo_list.models.Priority;
import org.example.todo_list.models.Tag;
import org.example.todo_list.models.Task;
import org.example.todo_list.models.TaskEnums;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskDetailsController implements Initializable {

    @FXML
    public Button hideBtn;
    @FXML
    public TextField taskNameDetailsTF, taskDueTime;
    @FXML
    public DatePicker taskDueDate;

    SceneManager sceneManager;
    public AppController parentController;
    private Task task;
    private TaskController taskCon;

    @FXML
    private Button addTagBtn, createTaskBtn, personalTaskBtn, shareTaskBtn;
    @FXML
    private TextArea taskDescription;
    @FXML
    public VBox root, tagVBox;

    @FXML
    private HBox addTagBtnBox, lowerTagBtnBox;
    @FXML
    private ComboBox<Priority> priorityComboBox;

    private ArrayList<Tag> taskTags = new ArrayList<>();

    private static final int MAX_TAGS_PER_ROW = 3;

    @FXML
    void addTagClicked(ActionEvent event) {
        Task task = AppController.getFocusedTask().getTask();
        taskTags = task.getTaskTags();
        // Get size of task tags
        if (taskTags.size() < TaskEnums.MAX_TAGS) {
            // When the button is clicked, show a pop-up dialog to input the tag
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add New Tag");
                dialog.setHeaderText(null);
                dialog.setContentText("Enter tag:");

                // Show the dialog and capture the user input
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(tagInput -> {
                    //Set the name of the tag from the dialog field
                    Tag newTag = new Tag(tagInput.trim());

                    //if new tag is not empty string or already existing tag
                    if (!newTag.getName().isEmpty() && !taskTags.contains(newTag)) {
                        taskTags.add(newTag);  // Add the tag to the list/task
                        task.setTaskTags(taskTags); //set focused task to new list
                        //If button is last in MAX_SIZE, change add tag to final label's name
                        Button newBtn = new Button(newTag.getName()); // Display the tags name
                        newBtn.getStyleClass().add("button-style");
                        //apply an id to each created button for style purposes, should be style class probably
                        newBtn.setId(addTagBtn.getId());

                        // Add action listener for editing tag (moved to a separate method)
                        editTag(newBtn, newTag);

                        //
                        if (addTagBtnBox.getChildren().size() < MAX_TAGS_PER_ROW) {
                            addTagBtnBox.getChildren().addFirst(newBtn);  // Replace button with tag btn if last tag
                        } else {
                            lowerTagBtnBox.getChildren().addFirst(newBtn);
                        }
                        //If tags are removed and list is < MAX_TAGS, add button back to appropriate box
                        if (taskTags.size() >= TaskEnums.MAX_TAGS) {
                            addTagBtn.setDisable(true);
                            addTagBtn.setVisible(false);
                            addTagBtnBox.getChildren().removeLast();
                        }
                    }
            });
        }
        task.setTaskTags(taskTags);

        //removable test prnt loop
        for (Tag tag : task.getTaskTags()) {
            System.out.println("Tasks actual tag: " + taskTags.toString());
        }
    }

    void repopulateTagButtons() {
        // Retrieve the focused task tags
        Task task = AppController.getFocusedTask().getTask();
        taskTags = task.getTaskTags();

        //Reset tag button state
        resetTagButtons();

        System.out.println(taskTags.size());
        if (taskTags == null || taskTags.isEmpty()) {
            //handle empty tag list case, return (remove print when done)
            System.out.println("No tags to display for task: " + task.getTitle());
            return;
        }

        //if new tag is not empty string or already existing tag
        // Repopulate buttons if there are tags
        for (Tag tag : taskTags) {
            System.out.println("Adding tag: " + tag.getName()); // Debugging
            //If target Hbox
            HBox targetBox = (addTagBtnBox.getChildren().size() < MAX_TAGS_PER_ROW)
                    ? addTagBtnBox
                    : lowerTagBtnBox;
            generateTagButton(targetBox, tag);
        }

        // Remove add tag button if task has max tags
        if (taskTags.size() >= TaskEnums.MAX_TAGS) {
            addTagBtn.setDisable(true);
            addTagBtn.setVisible(false);
            addTagBtnBox.getChildren().remove(addTagBtn);
        }
    }

    //for a new task specifically, may need to remove setter
    void resetTagButtons() {
        //Reset Hbox + tag button state initially and add addTagBtn
        addTagBtnBox.getChildren().clear();
        lowerTagBtnBox.getChildren().clear();
        if (!addTagBtnBox.getChildren().contains(addTagBtn)) {
            addTagBtnBox.getChildren().add(addTagBtn);
        }
        addTagBtn.setDisable(false);
        addTagBtn.setVisible(true);

        Task task = AppController.getFocusedTask().getTask();
        task.setTaskTags(taskTags); // Reset task tags
    }

    private void generateTagButton(HBox hBox, Tag tag) {
        Button tagButton = new Button(tag.getName());
        tagButton.getStyleClass().add("button-style");

        // Add edit functionality for the button
        editTag(tagButton, tag);

        // Add the button to the HBox
        hBox.getChildren().add(tagButton);
    }


    /**
     * Adds functionality to the button that allows the user to edit the tag name.
     * @param button The button associated with the tag.
     * @param tag The tag object to be edited.
     */
    private void editTag(Button button, Tag tag) {

        ContextMenu tagContext = new ContextMenu();
        // Add an action listener to edit the tag when the button is clicked
        MenuItem editTag = new MenuItem("Edit Tag");
        editTag.setOnAction(e -> openEditDialog(button, tag));

        MenuItem deleteTag = deleteTag(button, tag);

        // Add delete and edit option to the context menu
        tagContext.getItems().addAll(editTag, deleteTag);

        // Attach context menu to the new tag button
        button.setOnContextMenuRequested(event -> tagContext.show(button, event.getScreenX(), event.getScreenY()));

        //Left click opens tag editing, in addition to context menu
        button.setOnAction(e -> openEditDialog(button, tag));
    }

    //Extracted method for deleteTag functionality
    private MenuItem deleteTag(Button button, Tag tag) {
        MenuItem deleteTag = new MenuItem("Delete Tag");
        // Delete tag action event
        deleteTag.setOnAction(event -> {
            // Remove the tag from the tag list
            taskTags.remove(tag);
            // Remove the button from its parent container
            HBox parentBox = (HBox) button.getParent();
            parentBox.getChildren().remove(button);

            //if deleting tags brings you below 5, add tag button back to appropriate box
            if (taskTags.size() < TaskEnums.MAX_TAGS) {
                addTagBtn.setDisable(false);
                addTagBtn.setVisible(true);

                if (addTagBtnBox.getChildren().size() < MAX_TAGS_PER_ROW) {
                    if (!addTagBtnBox.getChildren().contains(addTagBtn)) {
                        addTagBtnBox.getChildren().add(addTagBtn);
                    }
                } else {
                    if (!lowerTagBtnBox.getChildren().contains(addTagBtn)) {
                        lowerTagBtnBox.getChildren().add(addTagBtn);
                    }
                }
            }

        });
        return deleteTag;
    }

    private void openEditDialog(Button button, Tag tag) {
        // Open dialog to edit tag
        TextInputDialog editDialog = new TextInputDialog(tag.getName());
        editDialog.setTitle("Edit Tag");
        editDialog.setHeaderText(null);
        editDialog.setContentText("Edit tag:");

        // Capture new input and update the tag name
        Optional<String> editResult = editDialog.showAndWait();
        editResult.ifPresent(editedTagInput -> {
            String trimmedTagInput = editedTagInput.trim();
            if (!trimmedTagInput.isEmpty() && !taskTags.contains(new Tag(trimmedTagInput))) {
                // Update the tag name
                tag.setName(trimmedTagInput);

                // Update the button text to reflect the new tag name
                button.setText(trimmedTagInput);
            }
        });
    }

    //Mouse dragging mouse anchors
    private double mouseAnchorX;
    private double mouseAnchorY;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //taskName.setText(task.getTitle());

        sceneManager = SceneManager.getInstance();
        root.setOnMousePressed(e->{
            mouseAnchorY = e.getY();
            System.out.println(e.getX());
            mouseAnchorX = e.getX();
            System.out.println(e.getY());
        });
        root.setOnMouseDragged(e->{//Make Detail window draggable
            applyWindowLimits(e.getSceneX()-mouseAnchorX , e.getSceneY()-mouseAnchorY);
        });

        //Any time one of these Nodes are changed, the task instance will update.
        taskNameDetailsTF.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                AppController.getFocusedTask().taskNameField.setText(taskNameDetailsTF.getText());
            }
        }));

        taskDescription.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) task =  AppController.getFocusedTask().getTask();
            if (!newValue) {
                task.setDescription(taskDescription.getText());
                try {
                    task.saveTaskDescription();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }));

        taskDueTime.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                task =  AppController.getFocusedTask().getTask();
            } else {
                if (!taskDueTime.getText().isEmpty()) {
                    String endDateTime = taskDueDate.getValue() + " " + taskDueTime.getText();
                    // Ensure the time string includes seconds
                    if (String.valueOf(task.getEndDateTime()).substring(11).length() == 5) {
                        endDateTime += ":00";
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    task.setEndDateTime(LocalDateTime.parse(endDateTime, formatter));
                    try {
                        task.saveTaskDueDate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        taskDueDate.valueProperty().addListener((ov, oldValue, newValue) -> {
            task = AppController.getFocusedTask().getTask();
            String endDateTime;
            if (!taskDueTime.getText().isEmpty()) {
                endDateTime = newValue + " " + String.valueOf(task.getEndDateTime()).substring(11);
                // Ensure the time string includes seconds
                if (String.valueOf(task.getEndDateTime()).substring(11).length() == 5) {
                    endDateTime += ":00";
                }
            } else {
                endDateTime = newValue + " 23:59:59";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            task.setEndDateTime(LocalDateTime.parse(endDateTime, formatter));
            try {
                task.saveTaskDueDate();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        });

        //Keep details winodw within the bounds of the primary stage.
        sceneManager.getPrimaryStage().widthProperty().addListener(e->applyWindowLimits(root.getLayoutX(), root.getLayoutY()));
        sceneManager.getPrimaryStage().heightProperty().addListener(e->applyWindowLimits(root.getLayoutX(), root.getLayoutY()));

        // Populate ComboBox with Priority enum values
        priorityComboBox.getItems().addAll(Priority.values());

        // Default value
        priorityComboBox.setValue(Priority.LOW);

        priorityComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                task = AppController.getFocusedTask().getTask();
                task.setPriority(newValue.getLevel());
                task.setColor(newValue.getColor());
                try {
                    task.saveTaskPriority();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Update the rectangle in TaskController
                taskCon = AppController.getFocusedTask();
                if (taskCon != null) {
                    taskCon.updatePriorityColor(task.getColor());
                }
            }
        });

        //Todo populate the tag field with the proper tags.
    }

    private void applyWindowLimits(double x, double y) {
        root.setLayoutX( x<0?0.0:(Math.min(x, SceneManager.getInstance().getPrimaryStage().getWidth()-16-root.getWidth())));//set limits of window dragging
        root.setLayoutY( y<0?0.0:(Math.min(y, SceneManager.getInstance().getPrimaryStage().getHeight()-40-root.getHeight())));
    }

    public void updateTaskDetails() {
        Task task = AppController.getFocusedTask().getTask();

        // Debugging: Ensure task data is valid
        System.out.println("Selected Task: " + task.getTitle());
        System.out.println("Tags: " + task.getTaskTags());
    }

    public void updateTaskDetails(Task task) {
        taskNameDetailsTF.setText( task.getTitle() );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = task.getEndDateTime().toLocalDate().format(formatter);
        taskDueDate.setValue(LocalDate.parse(formattedDate, formatter));
        taskDescription.setText( task.getDescription() );
        taskDueTime.setText(String.valueOf(task.getEndDateTime()).substring(11));
        priorityComboBox.setValue(task.getPriorityEnum());
        System.out.println("Task prio set to : " + task.getPriorityEnum().toString() + "priority int : " + task.getPriority());
        //update existing tasks buttons with task tags OR handle new task case
        repopulateTagButtons();

        try {
            System.out.println("Saving to DB");
            task.saveToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        applyWindowLimits(root.getLayoutX(), root.getLayoutY());
    }

    public void clearTaskDetails() {
        taskNameDetailsTF.setText("");
        taskDueDate.setValue(LocalDate.now());
        resetTagButtons();  //reset task tag buttons
        taskDescription.setText("");
    }


    public void hideDetails() {
        root.setVisible(false);
    }

    @FXML
    public void deleteTask() {
        AppController.getFocusedTask().removeSelf();
        hideDetails();
    }

    @FXML
    public void copyTask() {
        AppController.setCopiedTask(AppController.getFocusedTask().getTask().copy());
    }

    @FXML
    void pasteTask(ActionEvent event) {
        Task copiedTask = AppController.getCopiedTask();

        if (copiedTask != null) {
//            System.out.println("Focus ID " + AppController.getFocusedTask().getTask().getIdNum());
//            System.out.println("Copy ID " + copiedTask.getIdNum());
//            System.out.println("Copy List ID" + copiedTask.getListID());
//            System.out.println("Focus List ID" + AppController.getFocusedTask().getTask().getListID());
            int tempTaskIDNum = AppController.getFocusedTask().getTask().getIdNum();
            int tempListID = AppController.getFocusedTask().getTask().getListID();
            //Set copied tasks ID values to new ID values of the location of the pasted task
            copiedTask.setIdNum(tempTaskIDNum);
            copiedTask.setListID(tempListID);
            AppController.getFocusedTask().setTask(copiedTask);
//            System.out.println("----Post Paste ID vals----");
//            System.out.println("Focus ID " + AppController.getFocusedTask().getTask().getIdNum());
//            System.out.println("Copy ID " + copiedTask.getIdNum());
//            System.out.println("Copy List ID" + copiedTask.getListID());
//            System.out.println("Focus List ID" + AppController.getFocusedTask().getTask().getListID());
            updateTaskDetails(copiedTask);
        } else {
            System.out.println("No task to paste.");
        }
    }
}

