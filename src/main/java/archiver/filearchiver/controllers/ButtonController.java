package archiver.filearchiver.controllers;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.exception.PathNotFoundException;
import archiver.filearchiver.files.FileProperties;
import archiver.filearchiver.model.AddFiles;
import archiver.filearchiver.model.CreateZip;
import archiver.filearchiver.model.ShowContent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ButtonController {
    @FXML
    private Button showContent;
    @FXML
    private Button extractAll;
    @FXML
    private Button createZip;
    @FXML
    private Button removeZip;
    @FXML
    private Button addFiles;
    @FXML
    private ListView<String> fileList;

    @FXML
    void onShowContentButtonClick(ActionEvent event) throws NoSuchZipFileException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select zip file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Zip Files", "*.zip"));

        Path destinationPath = fileChooser.showOpenDialog(showContent.getContextMenu()).toPath();
        ShowContent showContent = new ShowContent(destinationPath);

        if (fileList.getItems().size() > 0) fileList.getItems().remove(0, fileList.getItems().size());

        for (FileProperties content : showContent.getFileList()) {
            fileList.getItems().add(content.toString());
        }
    }

    @FXML
    void onCreateZipButtonClick(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select destination zip file");
        Path destinationPath = fileChooser.showOpenDialog(createZip.getContextMenu()).toPath();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select directory you want to zip");
        Path source = directoryChooser.showDialog(createZip.getContextMenu()).toPath();
        new CreateZip(destinationPath).createZip(source);

    }

    @FXML
    void onAddFilesButtonClick(ActionEvent event) throws NoSuchZipFileException, IOException, PathNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select destination zip file");
        Path destinationPath = fileChooser.showOpenDialog(addFiles.getContextMenu()).toPath();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select file/s you want to add.");
        List<File> files = fileChooser.showOpenMultipleDialog(addFiles.getContextMenu());
        List<Path> pathList = new ArrayList<>();

        for (File file : files) {
            pathList.add(file.toPath());
        }

        new AddFiles(destinationPath).addFiles(pathList);
    }

    @FXML
    void onExtractAllButtonClick(ActionEvent event) {

    }

    @FXML
    void onRemoveZipButtonClick(ActionEvent event) {

    }



}
