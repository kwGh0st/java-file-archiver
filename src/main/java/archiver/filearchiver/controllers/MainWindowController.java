package archiver.filearchiver.controllers;

import archiver.filearchiver.controllers.services.AddFilesService;
import archiver.filearchiver.controllers.services.CreateZipService;
import archiver.filearchiver.controllers.services.ExtractFilesService;
import archiver.filearchiver.controllers.services.LoadFileService;
import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.exception.PathNotFoundException;
import archiver.filearchiver.model.operations.RemoveFiles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainWindowController {
    @FXML
    private Button select;
    @FXML
    private ListView<Path> filesToDelete;
    @FXML
    private Button deleteButton;

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
    private static Path zipSourceToRemove;

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
    void onRemoveFilesButtonClick() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("delete-list.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 380);
        Stage stage = new Stage();
        stage.setTitle("Remove Files");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onSelectButtonClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select source archive.");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip File", "*.zip"));
        Path source = fileChooser.showOpenDialog(select.getContextMenu()).toPath();
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
}