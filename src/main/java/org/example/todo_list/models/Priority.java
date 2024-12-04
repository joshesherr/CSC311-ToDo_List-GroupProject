package org.example.todo_list.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Experimenting with different form of enums for task priority
public enum Priority {
    NONE(0, Color.FLORALWHITE), //is none a relevant default case, or default to low?
    LOW(1, Color.GREEN),
    MEDIUM(2, Color.YELLOW),
    HIGH(3, Color.ORANGE),
    CRITICAL(4, Color.RED);

    private final int level;
    private final Color color;
    //public static final int MAX_TAGS = 5;

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

    //mainly for testing/debugging purposes right now
    @Override
    public String toString() {
        //Return enum name with only first letter capitalized
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
