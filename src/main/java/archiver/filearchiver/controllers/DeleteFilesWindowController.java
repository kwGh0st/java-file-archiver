package archiver.filearchiver.controllers;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.model.operations.RemoveFiles;
import archiver.filearchiver.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeleteFilesWindowController extends Controller implements Initializable {
    @FXML
    private Button select;
    @FXML
    private ListView<Path> filesToDelete;
    @FXML
    private Button deleteButton;

    private FileChooser fileChooser;

    private static Path zipSourceToRemove;

    public DeleteFilesWindowController(String fxmlName, ViewFactory viewFactory) {
        super(fxmlName, viewFactory);
    }


    @FXML
    private void onSelectButtonClick() throws IOException {

        Path source = fileChooser.showOpenDialog(select.getScene().getWindow()).toPath();
        zipSourceToRemove = source;

        List<Path> pathList = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(source))) {
            ZipEntry entry = zipInputStream.getNextEntry();

            while (entry != null) {
                String fileName = entry.getName();
                pathList.add(Paths.get(fileName));
                zipInputStream.closeEntry();

                entry = zipInputStream.getNextEntry();
            }
        }

        setFilesToDelete(pathList);
    }

    private void setFilesToDelete(List<Path> fileList) {
        filesToDelete.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (Path path : fileList) {
            filesToDelete.getItems().add(path);
        }
    }

    @FXML
    private void onDeleteButtonClick() throws NoSuchZipFileException, IOException {
        List<Path> files = filesToDelete.getSelectionModel().getSelectedItems();
        new RemoveFiles(zipSourceToRemove).removeFiles(files);
        updateList(files, filesToDelete);
    }

    private void updateList(List<Path> pathList, ListView<Path> view) {
        List<Path> result = new ArrayList<>();
        for (Path path : view.getItems()) {
            if (!pathList.contains(path)) {
                result.add(path);
            }
        }
        if (view.getItems().size() > 0) view.getItems().remove(0, view.getItems().size());

        for (Path path : result) {
            view.getItems().add(path);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpFileChooser();
    }

    private void setUpFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select source archive.");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip File", "*.zip"));
    }
}
