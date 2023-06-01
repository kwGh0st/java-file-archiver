package archiver.operations;

import archiver.exception.NoSuchZipFileException;
import archiver.files.FileProperties;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ContentZipOperation extends ZipFileService {
    public ContentZipOperation(Path zipFile) {
        super(zipFile);
    }

    public List<FileProperties> getFileList() throws IOException, NoSuchZipFileException {
        if (!Files.isRegularFile(getZipFile())) throw new NoSuchZipFileException();

        List<FileProperties> files = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(getZipFile()))) {
            ZipEntry entry = zipInputStream.getNextEntry();

            while (entry != null) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                copyData(zipInputStream, outputStream);
                files.add(new FileProperties(entry.getName(), entry.getSize(), entry.getCompressedSize(), entry.getMethod()));

                entry = zipInputStream.getNextEntry();
            }
        }

        return files;
    }
}
