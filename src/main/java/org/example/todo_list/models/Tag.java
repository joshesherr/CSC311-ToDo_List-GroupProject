package org.example.todo_list.models;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Tag {

    private String name;
    private Color color;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //Equals currently checks for color, we should implement a way to guarantee users can easily match colors for
    //lists and tasks in the same group
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tag tag)) return false;
        //Im not sure how to check equality of colors atm, this may need tweaking
        return tag.getName().equalsIgnoreCase(this.name) && Objects.equals(color, tag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}
