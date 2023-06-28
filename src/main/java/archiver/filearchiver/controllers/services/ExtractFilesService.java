package archiver.filearchiver.controllers.services;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.model.operations.ExtractZip;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class ExtractFilesService {
    private final ListView<String> fileList;
    private final Button extractAll;

    public ExtractFilesService(ListView<String> fileList, Button extractAll) throws NoSuchZipFileException, IOException {
        this.fileList = fileList;
        this.extractAll = extractAll;
        start();
    }

    private void start() throws NoSuchZipFileException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select zip file to extract.");
        Path source = fileChooser.showOpenDialog(extractAll.getContextMenu()).toPath();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select destination directory");
        Path directory = directoryChooser.showDialog(extractAll.getContextMenu()).toPath();
        new ExtractZip(source).extractAll(directory);

        if (fileList.getItems().size() > 0) fileList.getItems().remove(0, fileList.getItems().size());

        for (File file : Objects.requireNonNull(directory.toFile().listFiles())) {
            fileList.getItems().add(file.getAbsolutePath());
        }
    }
}
