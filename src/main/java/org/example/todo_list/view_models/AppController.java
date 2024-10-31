package org.example.todo_list.view_models;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.todo_list.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    public HBox listBox;
    SceneManager sceneManager = SceneManager.getInstance();

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

    /**
     * removes a list from the home screen.
     * @param list The list to remove.
     */
    public void removeList(Node list) {
        listBox.getChildren().remove(list);
    }

}