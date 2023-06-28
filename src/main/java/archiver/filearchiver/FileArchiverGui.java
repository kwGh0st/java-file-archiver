package archiver.filearchiver;

import archiver.filearchiver.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class FileArchiverGui extends Application {
    @Override
    public void start(Stage stage) {
        new ViewFactory().showMainWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}