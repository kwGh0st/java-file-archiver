package archiver.operations;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

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
}
