package org.example.todo_list.comparators;

import org.example.todo_list.view_models.TaskController;

import java.util.Comparator;

public class SortByDate implements Comparator<TaskController> {

    @Override
    public int compare(TaskController t1, TaskController t2) {
        if (t1.getTask().getEndDateTime().isBefore(t2.getTask().getEndDateTime())) return 1; //t1 is closer to current time.
        if (t1.getTask().getEndDateTime().isAfter(t2.getTask().getEndDateTime())) return -1; //t2 is closer to current time.
        return 0;
    }
}
