package org.example.todo_list.models;

import javafx.scene.paint.Color;

//enums for task priority levels and color application in gui
public enum Priority {
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

    //For display in combobox
    @Override
    public String toString() {
        //Return enum name with only first letter capitalized
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
