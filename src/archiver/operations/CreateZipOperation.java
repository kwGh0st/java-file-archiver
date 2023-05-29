package archiver.operations;

import java.nio.file.Path;

public class CreateZipOperation extends ZipFileService {

    public CreateZipOperation(Path zipFile) {
        super(zipFile);
    }
}
