package archiver.operations;

import archiver.ConsoleHelper;
import archiver.exception.NoSuchZipFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class RemoveZipOperation extends ZipFileService {
    public RemoveZipOperation(Path zipFile) {
        super(zipFile);
    }

    public void removeFile(Path path) throws NoSuchZipFileException, IOException {
        removeFiles(Collections.singletonList(path));
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
                        ConsoleHelper.writeMessage(String.format("File %s removed from the archive.", entry.getName()));
                    }

                    entry = zipInputStream.getNextEntry();
                }
            }
        }

        Files.move(tmpFile, getZipFile(), StandardCopyOption.REPLACE_EXISTING);
    }
}
