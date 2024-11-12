package org.example.todo_list.models;

import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class List {

    private String title;
    private ArrayList<String> shareUsers;
    private PriorityQueue<Task> tasks;
    private float progress=0.0f;

    public List() {
        this.title = "";
    }
    public List(String title) {
        this.title = title;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return The percent of completeness of this List.
     */
    public float getProgress() {
        int taskCount = tasks.size();
        int completedTaskCount=0;
        for (Task task : tasks) {
            if(task.isCompleted()) completedTaskCount++;
        }

        return (float) completedTaskCount / taskCount;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

}
