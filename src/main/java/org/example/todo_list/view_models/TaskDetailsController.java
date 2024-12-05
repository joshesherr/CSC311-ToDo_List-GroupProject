package org.example.todo_list.view_models;

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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TaskDetailsController implements Initializable {

    @FXML
    public Button hideBtn;
    @FXML
    public TextField taskName, taskDueTime;
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
    public VBox root;
    @FXML
    private HBox addTagBtnBox, lowerTagBtnBox;
    @FXML
    private ComboBox<Priority> priorityComboBox;

    private ArrayList<Tag> listTags = new ArrayList<>();
    private static final int MAX_TAGS_PER_ROW = 3;
    // This is a temp List ^^^^^^^ Must be adjusted once code is refined a bit
    //Removing this will break below code until proper adjustments made

    @FXML
    void addTagClicked(ActionEvent event) {

        //IMPORTANT!!!!!!!!!!!!!!!!!!!!! Not full implementation for following reasons:
        //However a task is currently stored needs a list of tags -> this code must be updated
        // The TaskList in List class will do the following features:
        //Replace local tag arraylist with the one in the created List
        //Max size can be decided on later or set by user, but we should make a max just for creation
        if (listTags.size() < TaskEnums.MAX_TAGS) {
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

                    if (!newTag.getName().isEmpty() && !listTags.contains(newTag)) {
                        listTags.add(newTag);  // Add the tag to the list/task

                        //If button is last in MAX_SIZE, change add tag to final label's name

                        // Display the tags name
                        Button newBtn = new Button(newTag.getName());
                        newBtn.getStyleClass().add("button-style");
                        newBtn.setId(addTagBtn.getId());

                        // Add action listener for editing tag (moved to a separate method)
                        editTag(newBtn, newTag);

                        //Could do a for each loop here and create new buttons based on Tag Name values stored in list
                        //that always adds button last, or maybe an observable list too
                        //Or it could just be by index or size using add(), revisit
                        if (addTagBtnBox.getChildren().size() < MAX_TAGS_PER_ROW) {
                            addTagBtnBox.getChildren().addFirst(newBtn);  // Replace button with tag btn if last tag
                        } else {
                            lowerTagBtnBox.getChildren().addFirst(newBtn);
                        }
                        //This code will break if user wants to remove excess tags, no way to remove tags yet
                        //If tags are removed and list is < MAX_TAGS, reenable button -> How to do that if button is disabled
                        //Revisit the issue later
                        if (listTags.size() >= TaskEnums.MAX_TAGS) {
                            addTagBtn.setDisable(true);
                            addTagBtn.setVisible(false);
                            addTagBtnBox.getChildren().removeLast(); // same as managed i believe
                        }
                    }
            });
        }
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
            listTags.remove(tag);
    //        System.out.println("listTags size: " + listTags.size()); Debugging statement
            // Remove the button from its parent container
            HBox parentBox = (HBox) button.getParent();
            parentBox.getChildren().remove(button);

            //if deleting tags brings you below 5, add tag button back to appropriate box
            if (listTags.size() < TaskEnums.MAX_TAGS) {
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
            if (!trimmedTagInput.isEmpty() && !listTags.contains(new Tag(trimmedTagInput))) {
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
            mouseAnchorX = e.getX();
            mouseAnchorY = e.getY();
        });
        root.setOnMouseDragged(e->{//Make Detail window draggable
            applyWindowLimits(e.getSceneX()-mouseAnchorX , e.getSceneY()-mouseAnchorY);
        });

        //Any time one of these Nodes are changed, the task instance will update.
        //THIS WORKS SLOW!!!
//        taskName.textProperty().addListener((ov, oldValue, newValue) -> {
//            AppController.getFocusedTask().taskNameField.setText(newValue);//Task will be updated from TaskControllers Listener on taskNameField
//        });

        //Any time one of these Nodes are changed, the task instance will update.
        taskName.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            AppController.getFocusedTask().taskNameField.setText(taskName.getText());
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

        priorityComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                task = AppController.getFocusedTask().getTask();
                task.setPriority(newValue.getLevel());
                try {
                    task.saveTaskPriority();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                task.setColor(newValue.getColor());

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

    public void updateTaskDetails(Task task) {
        //task = AppController.getFocusedTask().getTask();
        taskName.setText( task.getTitle() );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = task.getEndDateTime().toLocalDate().format(formatter);
        taskDueDate.setValue(LocalDate.parse(formattedDate, formatter));
        //taskPriority.setText( String.valueOf(task.getPriority()) );
        taskDescription.setText( task.getDescription() );
        taskDueTime.setText(String.valueOf(task.getEndDateTime()).substring(11));
        //priorityComboBox.setValue(Priority.values()[task.getPriority()]);

        try {
            task.saveToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        applyWindowLimits(root.getLayoutX(), root.getLayoutY());
    }

    public void clearTaskDetails() {
        taskName.setText("");
        taskDueDate.setValue(LocalDate.now());
        //taskPriority.setText("");
        //clearTaskButtons needed
        taskDescription.setText("");
    }

    public void hideDetails() {
        root.setVisible(false);
    }

    public void deleteTask() {
        AppController.getFocusedTask().removeSelf();
        hideDetails();
    }

    public void copyTask() {
        AppController.setCopiedTask(AppController.getFocusedTask().getTask().copy());
    }

    public void pasteTask() {
        Task copiedTask = AppController.getCopiedTask();
        if (copiedTask != null) {
            updateTaskDetails(copiedTask);
        } else {
            System.out.println("No task to paste.");
        }
    }
}

