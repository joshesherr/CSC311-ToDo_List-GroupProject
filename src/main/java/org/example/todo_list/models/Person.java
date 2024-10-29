package org.example.todo_list.models;

public class Person {
    private String username_ID;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String position;

    public Person() {}

    public Person(String username_ID, String password, String firstName, String lastName, String email, String position) {
        this.username_ID = username_ID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
    }

    public String getUsername_ID() {
        return username_ID;
    }

    public void setUsername_ID(String username_ID) {
        this.username_ID = username_ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
