package archiver.files;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Path rootPath;
    private List<Path> fileList;

    public FileManager(Path rootPath) {
        this.rootPath = rootPath;
        this.fileList = new ArrayList<>();
    }

    public List<Path> getFileList() {
        return fileList;
    }
}
