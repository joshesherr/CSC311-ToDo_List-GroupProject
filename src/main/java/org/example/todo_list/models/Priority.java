package org.example.todo_list.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Experimenting with different form of enums for task priority
public enum Priority {
    LOW(1, Color.GREEN),
    MEDIUM(2, Color.YELLOW),
    HIGH(3, Color.ORANGE),
    CRITICAL(4, Color.RED);

    private final int level;
    private final Color color;

    Priority(int level, Color color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public Color getColor() {
        return color;
    }

    public void applyColorToTask(Rectangle rectangle) {
        rectangle.setFill(this.color);
    }

    //mainly for testing/debugging purposes right now
    @Override
    public String toString() {
        return "Priority{" + String.valueOf(this.getLevel()) +
                ", priorityLevel=" + level +
                ", colorPriority='" + color + '\'' +
                '}';
    }
}
