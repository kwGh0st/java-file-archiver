package archiver.filearchiver.model.operations;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.model.ZipFileManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractZip extends ZipFileManager {

    public ExtractZip(Path zipFile) {
        super(zipFile);
    }

    public void extractAll(Path outputFolder) throws NoSuchZipFileException, IOException {
        if (!Files.isRegularFile(getZipFile())) {
            throw new NoSuchZipFileException();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(getZipFile()))) {
            if (Files.notExists(outputFolder))
                Files.createDirectories(outputFolder);


            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                Path fileFullName = outputFolder.resolve(fileName);

                Path parent = fileFullName.getParent();
                if (Files.notExists(parent))
                    Files.createDirectories(parent);

                try (OutputStream outputStream = Files.newOutputStream(fileFullName)) {
                    copyData(zipInputStream, outputStream);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }
}
