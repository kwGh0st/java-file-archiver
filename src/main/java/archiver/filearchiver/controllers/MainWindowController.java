package archiver.filearchiver.controllers;

import archiver.filearchiver.controllers.services.AddFilesService;
import archiver.filearchiver.controllers.services.CreateZipService;
import archiver.filearchiver.controllers.services.ExtractFilesService;
import archiver.filearchiver.controllers.services.LoadFileService;
import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.exception.PathNotFoundException;
import archiver.filearchiver.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.IOException;

public class MainWindowController extends Controller {

    @FXML
    private Button loadFile;
    @FXML
    private Button extractAll;
    @FXML
    private Button createZip;
    @FXML
    private Button removeFiles;
    @FXML
    private Button addFiles;
    @FXML
    private ListView<String> fileList;

    public MainWindowController(String fxmlName, ViewFactory viewFactory) {
        super(fxmlName, viewFactory);
    }


    @FXML
    void onLoadFileButtonClick() throws NoSuchZipFileException, IOException {
        new LoadFileService(loadFile, fileList);
    }

    @FXML
    void onCreateZipButtonClick() throws Exception {
        new CreateZipService(fileList, createZip);
    }

    @FXML
    void onAddFilesButtonClick() throws NoSuchZipFileException, IOException, PathNotFoundException {
        new AddFilesService(fileList, addFiles);
    }

    @FXML
    void onExtractAllButtonClick() throws NoSuchZipFileException, IOException {
        new ExtractFilesService(fileList, extractAll);
    }

    @FXML
    void onRemoveFilesButtonClick() {
        getViewFactory().showDeleteFilesWindow();
    }
}
