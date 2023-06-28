package archiver.filearchiver.controllers.services;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.exception.PathNotFoundException;
import archiver.filearchiver.files.FileProperties;
import archiver.filearchiver.model.operations.AddFiles;
import archiver.filearchiver.model.operations.ShowContent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AddFilesService {
    private final ListView<String> fileList;
    private final Button addFiles;

    public AddFilesService(ListView<String> fileList, Button addFiles) throws NoSuchZipFileException, IOException, PathNotFoundException {
        this.fileList = fileList;
        this.addFiles = addFiles;
        start();
    }

    private void start() throws NoSuchZipFileException, IOException, PathNotFoundException {
        fileList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        ShowContent showContent = new ShowContent(destinationPath);

        if (fileList.getItems().size() > 0) fileList.getItems().remove(0, fileList.getItems().size());

        for (FileProperties content : showContent.getFileList()) {
            fileList.getItems().add(content.toString());
        }
    }
}
