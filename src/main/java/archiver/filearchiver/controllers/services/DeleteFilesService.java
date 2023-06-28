package archiver.filearchiver.controllers.services;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.model.operations.RemoveFiles;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeleteFilesService {
    private final Button select;
    private final ListView<Path> filesToDelete;
    private final Button deleteButton;
    private final FileChooser fileChooser;
    private static Path zipSourceToRemove;

    public DeleteFilesService(Button select, ListView<Path> filesToDelete, Button deleteButton, FileChooser fileChooser) {
        this.select = select;
        this.filesToDelete = filesToDelete;
        this.deleteButton = deleteButton;
        this.fileChooser = fileChooser;
    }

    public void start() throws IOException {
        Path source = fileChooser.showOpenDialog(select.getContextMenu()).toPath();
        zipSourceToRemove = source;
        List<Path> pathList = readFile(source);
        if (filesToDelete.getItems().size() > 0) filesToDelete.getItems().clear();
        setFilesToDelete(pathList);
    }

    private List<Path> readFile(Path source) throws IOException {
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

        return pathList;
    }

    private void setFilesToDelete(List<Path> fileList) {
        filesToDelete.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (Path path : fileList) {
            filesToDelete.getItems().add(path);
        }
    }

    public void removeSelected() throws NoSuchZipFileException, IOException {
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
        if (view.getItems().size() > 0) view.getItems().clear();

        for (Path path : result) {
            view.getItems().add(path);
        }
    }


}
