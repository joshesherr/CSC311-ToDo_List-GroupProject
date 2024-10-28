module org.example.csc311groupprojecttodo_list {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.todo_list to javafx.fxml;
    exports org.example.todo_list;
    exports org.example.todo_list.fxml_controllers;
    opens org.example.todo_list.fxml_controllers to javafx.fxml;
}