package archiver.filearchiver.controllers.services;


import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.files.FileProperties;
import archiver.filearchiver.model.operations.ShowContent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.nio.file.Path;

public class LoadFileService {
    private final Button loadFile;
    private final ListView<String> fileList;

    public LoadFileService(Button loadFile, ListView<String> fileList) throws NoSuchZipFileException, IOException {
        this.loadFile = loadFile;
        this.fileList = fileList;
        start();
    }

    private void start() throws NoSuchZipFileException, IOException {
        fileList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select zip file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Zip Files", "*.zip"));

        Path destinationPath = fileChooser.showOpenDialog(loadFile.getContextMenu()).toPath();
        ShowContent showContent = new ShowContent(destinationPath);

        if (fileList.getItems().size() > 0) fileList.getItems().remove(0, fileList.getItems().size());

        for (FileProperties content : showContent.getFileList()) {
            fileList.getItems().add(content.toString());
        }
    }


}
