package archiver.filearchiver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FileArchiverGui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FileArchiverGui.class.getResource("file-archiver.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 380);
        stage.setTitle("File Archiver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}