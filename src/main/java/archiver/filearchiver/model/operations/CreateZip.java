package archiver.filearchiver.model.operations;

import archiver.filearchiver.exception.PathNotFoundException;
import archiver.filearchiver.files.FileManager;
import archiver.filearchiver.model.ZipFileManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class CreateZip extends ZipFileManager {

    public CreateZip(Path zipFile) {
        super(zipFile);
    }

    public void createZip(Path source) throws Exception {
        Path zipDirectory = source.getParent();

        if (Files.notExists(zipDirectory)) Files.createDirectories(zipDirectory);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(getZipFile()))) {

            if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();

                for (Path fileName : fileNames) {
                    addNewZipEntry(zipOutputStream, source, fileName);
                }

            } else if (Files.isRegularFile(source)) {
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());

            } else {

                throw new PathNotFoundException();
            }
        }
    }
}
