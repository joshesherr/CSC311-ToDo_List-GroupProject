package org.example.todo_list.view_models;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AddTaskController {

    @FXML
    private Button addTagBtn, createTaskBtn, personalTaskBtn, shareTaskBtn;

    @FXML
    private VBox createTaskContainer;

    @FXML
    private Label descriptionLabel, dueDateLabel, nameLabel;

    @FXML
    private TextField dueDateTextField, nameTextField;

    @FXML
    private AnchorPane root;

}

