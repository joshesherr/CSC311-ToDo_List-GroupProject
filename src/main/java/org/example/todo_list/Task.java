package org.example.todo_list;

import java.time.*;

public class Task {

    private LocalDateTime taskStartDateTime;
    private LocalDateTime taskEndDateTime;
    private String taskDescription;
    private boolean taskStatus;
    private int taskPriority;

    public Task() {
        this.taskStartDateTime = LocalDateTime.now();
        this.taskEndDateTime = null;
        this.taskDescription = "";
        this.taskPriority = Tasks.PRIORITY_LOW;
        this.taskStatus = false;
    }

    public Task(LocalDateTime taskStartDateTime, LocalDateTime taskEndDateTime, String taskDescription, int taskPriority) {
        this.taskStartDateTime = taskStartDateTime;
        this.taskEndDateTime = taskEndDateTime;
        this.taskDescription = taskDescription;
        this.taskPriority = taskPriority;
        this.taskStatus = false;
    }
}
