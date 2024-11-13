package org.example.todo_list.models;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class TaskList {

    private String title;
    private ArrayList<String> shareUsers;
    private PriorityQueue<Task> tasks;
    private double progress=0.0;
    private ArrayList<Tag> listTags = new ArrayList<>(); //This may actually be not needed here but in Task instead
    // Create a default tag that each list will start with and be replaced?
    //Or start empty? Only reason I wonder this is because of color setting in tags,
    //and how we want to use Color

    public TaskList() {
        this.title = "";
        this.tasks = new PriorityQueue<>();
    }
    public TaskList(String title) {
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
    public double getProgress() {
        int taskCount = tasks.size();
        int completedTaskCount=0;
        for (Task task : tasks) {
            if(task.isCompleted()) completedTaskCount++;
        }

        return (double) completedTaskCount / taskCount;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Tag> getListTags() {
        return listTags;
    }

    public void setListTags(ArrayList<Tag> listTags) {
        this.listTags = listTags;
    }
}
