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
    private int priority;
    private Color color;
    private ArrayList<Tag> taskTags = new ArrayList<>();
        // Create a default tag that each list will start with and be replaced?
        //Or start empty? Only reason I wonder this is because of color setting in tags,
        //and how we want to use Color

    private ConnDB connDB = new ConnDB();

    public Task() {
        this.idNum = -1;
        this.title = "";
        this.startDateTime = LocalDateTime.now();
        this.endDateTime = null;
        this.description = "";
        this.priority = TaskEnums.PRIORITY_LOW;
        this.completed = false;
    }

    public Task(String title, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, int priority) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.priority = priority;
        this.completed = false;
    }

    public Task(int idNum, String title, LocalDateTime startDateTime, int listId, LocalDateTime endDateTime, String description, boolean completed) {
        this.idNum = idNum;
        this.title = title;
        this.startDateTime = startDateTime;
        this.listID = listId;
        this.endDateTime = endDateTime;
        this.description = description;
        this.completed = completed;
    }

    public Task(String title) {
        this.title = title;
        this.startDateTime = LocalDateTime.now();
        this.completed = false;
    }
/*
        Unimplemented as of yet, still testing priority enum idea and best way to implement
    public Task(String title, LocalDateTime startDateTime, LocalDateTime endDateTime, String description, Priority priority) {
        this.title = title;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.priority = priority.getPriorityLevel();
        this.completed = false;
    }
*/

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
        return endDateTime==null?LocalDateTime.now():endDateTime;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<Tag> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(ArrayList<Tag> taskTags) {
        this.taskTags = taskTags;
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

    public Task copy() {
        return new Task(title, startDateTime, endDateTime, description, priority);
    }
}
