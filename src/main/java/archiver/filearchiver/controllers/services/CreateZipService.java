package archiver.filearchiver.controllers.services;

import archiver.filearchiver.files.FileProperties;
import archiver.filearchiver.model.CreateZip;
import archiver.filearchiver.model.ShowContent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.nio.file.Path;

public class CreateZipService {
    private final ListView<String> fileList;
    private final Button createZip;

    public CreateZipService(ListView<String> fileList, Button createZip) throws Exception {
        this.fileList = fileList;
        this.createZip = createZip;
        start();
    }

    private void start() throws Exception {
        fileList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select destination zip file");
        Path destinationPath = fileChooser.showOpenDialog(createZip.getContextMenu()).toPath();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select directory you want to zip");
        Path source = directoryChooser.showDialog(createZip.getContextMenu()).toPath();
        new CreateZip(destinationPath).createZip(source);

        ShowContent showContent = new ShowContent(destinationPath);

        if (fileList.getItems().size() > 0) fileList.getItems().remove(0, fileList.getItems().size());

        for (FileProperties content : showContent.getFileList()) {
            fileList.getItems().add(content.toString());
        }
    }
}
