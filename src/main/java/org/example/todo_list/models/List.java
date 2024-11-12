package org.example.todo_list.models;

import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class List {

    private String title;
    private ArrayList<String> shareUsers;
    private PriorityQueue<Task> tasks;
    private float progress=0.0f;
    private ArrayList<Tag> listTags = new ArrayList<>(); //This may actually be not needed here but in Task instead
    // Create a default tag that each list will start with and be replaced?
    //Or start empty? Only reason I wonder this is because of color setting in tags,
    //and how we want to use Color

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

    public ArrayList<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(ArrayList<Tag> listTags) {
        this.listTags = listTags;
    }
}
