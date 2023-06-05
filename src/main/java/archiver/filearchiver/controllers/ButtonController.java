package archiver.filearchiver.controllers;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.files.FileProperties;
import archiver.filearchiver.model.ShowContent;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
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
    void onExtractAllButtonClick(ActionEvent event) {

    }

    @FXML
    void onCreateZipButtonClick(ActionEvent event) {

    }

    @FXML
    void onRemoveZipButtonClick(ActionEvent event) {

    }

    @FXML
    void onAddFilesButtonClick(ActionEvent event) {

    }



}
