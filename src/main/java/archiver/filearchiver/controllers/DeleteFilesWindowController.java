package archiver.filearchiver.controllers;

import archiver.filearchiver.controllers.services.DeleteFilesService;
import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;


public class DeleteFilesWindowController extends Controller implements Initializable {
    @FXML
    private Button select;
    @FXML
    private ListView<Path> filesToDelete;
    @FXML
    private Button deleteButton;
    private FileChooser fileChooser;
    private DeleteFilesService deleteFilesService;



    public DeleteFilesWindowController(String fxmlName, ViewFactory viewFactory) {
        super(fxmlName, viewFactory);
    }


    @FXML
    private void onSelectButtonClick() throws IOException {
        deleteFilesService.start();
    }



    @FXML
    private void onDeleteButtonClick() throws NoSuchZipFileException, IOException {
        deleteFilesService.removeSelected();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpFileChooser();
        setUpDeleteFilesService();
    }

    private void setUpDeleteFilesService() {
        deleteFilesService = new DeleteFilesService(select, filesToDelete, deleteButton, fileChooser);
    }

    private void setUpFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select source archive.");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip File", "*.zip"));
    }
}
