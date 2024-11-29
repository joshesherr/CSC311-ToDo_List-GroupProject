package org.example.todo_list.models;

//Experimenting with different form of enums for task priority
public enum Priority {
    PRIORITY_LOW(1, "green"),
    PRIORITY_MEDIUM(2, "yellow"),
    PRIORITY_HIGH(3, "orange"),
    PRIORITY_HIGHEST(4, "red");

    //Is this better this way, or should color be its own enum? Whats easier/less confusing?
    // Color.GREEN, Color.RED, Color.YELLOW, Color.Orange;


    private final int priorityLevel;
    private final String colorPriority;
    //Color colorPriority might be preferable

    Priority(int priorityLevel, String colorPriority) {
        this.priorityLevel = priorityLevel;
        this.colorPriority = colorPriority;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public String getColorPriority() {
        return colorPriority;
    }

    @Override
    public String toString() {
        return "Priority{" +
                "priorityLevel=" + priorityLevel +
                ", colorPriority='" + colorPriority + '\'' +
                '}';
    }
}
