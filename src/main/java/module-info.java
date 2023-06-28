module archiver.filearchiver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens archiver.filearchiver to javafx.fxml;
    opens archiver.filearchiver.controllers to javafx.fxml;

    exports archiver.filearchiver;
    exports archiver.filearchiver.controllers;
    exports archiver.filearchiver.view;


}