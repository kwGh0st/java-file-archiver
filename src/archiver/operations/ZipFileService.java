package archiver.operations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class ZipFileService {
    private final Path zipFile;

    public ZipFileService(Path zipFile) {
        this.zipFile = zipFile;
    }

    public Path getZipFile() {
        return zipFile;
    }

    protected void copyData(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;

        while ((len = in.read()) > 0) {
            out.write(buffer, 0, len);
        }
    }

    protected void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws IOException {
        Path fullPath = filePath.resolve(fileName);
        try (InputStream inputStream = Files.newInputStream(fullPath)) {
            ZipEntry entry = new ZipEntry(fileName.toString());

            zipOutputStream.putNextEntry(entry);

            copyData(inputStream, zipOutputStream);

            zipOutputStream.closeEntry();
        }
    }
}
