package org.example.todo_list.models;

import javafx.scene.paint.Color;

import java.time.*;
import java.util.ArrayList;

public class Task implements Comparable {

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private boolean completed;
    private int priority;
    private Color color;
    private ArrayList<Tag> taskTags = new ArrayList<>();
        // Create a default tag that each list will start with and be replaced?
        //Or start empty? Only reason I wonder this is because of color setting in tags,
        //and how we want to use Color

    public Task() {
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
}
