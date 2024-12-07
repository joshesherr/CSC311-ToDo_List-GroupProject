package org.example.todo_list.comparators;

import org.example.todo_list.view_models.TaskController;

import java.util.Comparator;

public class SortByName implements Comparator<TaskController> {

    @Override
    public int compare(TaskController t1, TaskController t2) {
        return String.CASE_INSENSITIVE_ORDER.compare(t1.getTask().getTitle(), t2.getTask().getTitle());
    }
}
