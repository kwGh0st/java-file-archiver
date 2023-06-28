package archiver.filearchiver.view;

import archiver.filearchiver.controllers.Controller;
import archiver.filearchiver.controllers.DeleteFilesWindowController;
import archiver.filearchiver.controllers.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    public void showMainWindow() {
        MainWindowController controller = new MainWindowController("file-archiver.fxml", this);
        initStage(controller);
    }

    public void showDeleteFilesWindow() {
        DeleteFilesWindowController controller = new DeleteFilesWindowController("delete-list.fxml", this);
        initStage(controller);
    }

    private void initStage(Controller controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        loader.setController(controller);
        Parent parent;

        try {
            parent = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


}
