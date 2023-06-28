package archiver.filearchiver.model.operations;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.model.ZipFileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class RemoveFiles extends ZipFileManager {
    public RemoveFiles(Path zipFile) {
        super(zipFile);
    }

    public void removeFiles(List<Path> pathList) throws NoSuchZipFileException, IOException {
        if (!Files.isRegularFile(getZipFile())) throw new NoSuchZipFileException();

        Path tmpFile = Files.createTempFile(null, null);


        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tmpFile))) {
            try (ZipInputStream zipInputStream =  new ZipInputStream(Files.newInputStream(getZipFile()))) {
                ZipEntry entry = zipInputStream.getNextEntry();

                while (entry != null) {

                    Path archivedFile = Paths.get(entry.getName());

                    if (!pathList.contains(archivedFile)) {
                        String fileName = entry.getName();
                        zipOutputStream.putNextEntry(new ZipEntry(fileName));

                        copyData(zipInputStream, zipOutputStream);

                        zipInputStream.closeEntry();
                        zipOutputStream.closeEntry();

                    } else {
                        System.out.printf("File %s removed from the archive.%n", entry.getName());
                    }

                    entry = zipInputStream.getNextEntry();
                }
            }
        }

        Files.move(tmpFile, getZipFile(), StandardCopyOption.REPLACE_EXISTING);
    }
}
