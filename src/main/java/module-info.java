module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires transitive javafx.graphics;

    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.display to javafx.fxml;
    opens com.example.demo.level to javafx.fxml;
    opens com.example.demo.mechanics to javafx.fxml;
    opens com.example.demo.model to javafx.fxml;
    opens com.example.demo.projectiles to javafx.fxml;
    opens com.example.demo.services to javafx.fxml;
    opens com.example.demo.utilities to javafx.fxml;
    opens com.example.demo.view to javafx.fxml;

    exports com.example.demo.controller;
    exports com.example.demo.display;
    exports com.example.demo.level;
    exports com.example.demo.mechanics;
    exports com.example.demo.model;
    exports com.example.demo.projectiles;
    exports com.example.demo.services;
    exports com.example.demo.utilities;
    exports com.example.demo.view;
}