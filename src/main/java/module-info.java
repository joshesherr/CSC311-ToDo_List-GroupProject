module org.example.csc311groupprojecttodo_list {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.csc311groupprojecttodo_list to javafx.fxml;
    exports org.example.csc311groupprojecttodo_list;
}