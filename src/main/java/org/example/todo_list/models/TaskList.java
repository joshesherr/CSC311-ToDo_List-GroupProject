package org.example.todo_list.models;

import org.example.todo_list.db.ConnDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class TaskList {
    private String username;
    private int idNum = -1;
    private String name;
    private ArrayList<String> shareUsers;
    private PriorityQueue<Task> tasks;
    private double progress=0.0;
    private ArrayList<Tag> listTags = new ArrayList<>(); //This may actually be not needed here but in Task instead
    //and how we want to use Color

    private ConnDB connDB = new ConnDB();

    public TaskList() {
        this.name ="";
        this.tasks = new PriorityQueue<>();
    }

    public TaskList(String taskName, String username) {
        this.name = taskName;
        this.username = username;
        this.tasks = new PriorityQueue<>();
    }

    public TaskList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public int getIdNum() {
        return idNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public PriorityQueue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(PriorityQueue<Task> tasks) {
        this.tasks = tasks;
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

    public void saveToDatabase() throws SQLException {
        connDB.saveTaskListChanges(this);
    }
}
