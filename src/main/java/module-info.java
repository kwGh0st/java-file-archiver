module archiver.filearchiver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens archiver.filearchiver to javafx.fxml;
    exports archiver.filearchiver;
}