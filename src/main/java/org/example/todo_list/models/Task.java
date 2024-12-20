package org.example.todo_list.models;

import javafx.scene.paint.Color;
import org.example.todo_list.db.ConnDB;

import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;

public class Task implements Comparable {
    private int idNum;
    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private int listID;
    private boolean completed;
    private Priority priority;

    private Color color;
    private ArrayList<Tag> taskTags = new ArrayList<>();
    private ArrayList<String> tagsName = new ArrayList<>();
        // Create a default tag that each list will start with and be replaced?
        //Or start empty? Only reason I wonder this is because of color setting in tags,
        //and how we want to use Color

    private ConnDB connDB = new ConnDB();

    public Task() {
        this.idNum = -1;
        this.title = "";
        this.startDateTime = LocalDateTime.now();
        this.endDateTime = LocalDateTime.now();
        this.description = "";
        this.priority = Priority.LOW;
        this.completed = false;
    }

    public Task(String title, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, Priority priority) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.priority = priority;
        this.completed = false;
    }

    public Task(int idNum, String title, LocalDateTime startDateTime, int listId, LocalDateTime endDateTime, String description, boolean completed, Priority priority) {
        this.idNum = idNum;
        this.title = title;
        this.startDateTime = startDateTime;
        this.listID = listId;
        this.endDateTime = endDateTime;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }


    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime==null?LocalDateTime.now():startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean status) {
        this.completed = status;
    }

    public boolean getCompleted() {return completed;}

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public ArrayList<Tag> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(ArrayList<Tag> taskTags) {
        this.taskTags = taskTags;
        try {
            connDB.setTaskTags(this);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public void saveToDatabase() throws SQLException {
        connDB.saveTaskChanges(this);
    }

    public void saveTaskCompletion() throws SQLException {
        connDB.updatesTaskCompletion(this);
    }

    public void saveTaskDescription() throws SQLException {
        connDB.updateTaskDescription(this);
    }

    public void saveTaskPriority() throws SQLException {
        connDB.updateTaskPriority(this);
    }

    public void saveTaskDueDate() throws SQLException {
        connDB.updateTaskDueDate(this);
    }

    public void deleteTask() throws SQLException {
        connDB.deleteTask(this);
    }
 //   public Task(int idNum, String title, LocalDateTime startDateTime, int listId, LocalDateTime endDateTime, String description, boolean completed,  int priority)
    public Task copy() {
        return new Task(idNum, title, startDateTime, listID, endDateTime, description, completed, priority);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", idNum=" + idNum +
                ", listID=" + listID +
                ", priority=" + priority +
                '}';
    }
}
