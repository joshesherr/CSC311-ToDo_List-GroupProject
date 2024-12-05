package org.example.todo_list.models;

import javafx.scene.paint.Color;

import java.util.Objects;

public class Tag {

    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tag tag)) return false;
        return tag.getName().equalsIgnoreCase(this.name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
