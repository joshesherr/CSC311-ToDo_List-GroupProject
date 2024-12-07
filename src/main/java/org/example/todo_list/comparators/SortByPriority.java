package org.example.todo_list.comparators;

import org.example.todo_list.view_models.TaskController;

import java.util.Comparator;

public class SortByPriority implements Comparator<TaskController> {

    @Override
    public int compare(TaskController t1, TaskController t2) {
        return Integer.compare(t2.getTask().getPriority().getLevel(), t1.getTask().getPriority().getLevel());
    }
}
