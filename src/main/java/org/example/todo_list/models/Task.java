package org.example.todo_list.models;

import java.time.*;

public class Task {

    private String title;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private boolean completed;
    private int priority;

    public Task() {
        this.title = "";
        this.startDateTime = LocalDateTime.now();
        this.endDateTime = null;
        this.description = "";
        this.priority = Tasks.PRIORITY_LOW;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
