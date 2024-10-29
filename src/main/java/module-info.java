module org.example.csc311groupprojecttodo_list {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.todo_list to javafx.fxml;
    exports org.example.todo_list;
    exports org.example.todo_list.view_models;
    opens org.example.todo_list.view_models to javafx.fxml;
    exports org.example.todo_list.db;
    opens org.example.todo_list.db to javafx.fxml;
    exports org.example.todo_list.models;
    opens org.example.todo_list.models to javafx.fxml;
}