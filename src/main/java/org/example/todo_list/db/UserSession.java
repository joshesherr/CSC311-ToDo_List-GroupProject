package org.example.todo_list.db;

public class UserSession {
    private static UserSession userSession;
    private String username;

    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
