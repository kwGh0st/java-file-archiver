package archiver.filearchiver.model;

import archiver.filearchiver.exception.NoSuchZipFileException;
import archiver.filearchiver.exception.PathNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class AddFiles extends ZipFileManager {

    public AddFiles(Path zipFile) {
        super(zipFile);
    }

    public void addFile(Path file) throws NoSuchZipFileException, IOException, PathNotFoundException {

        if (Files.isDirectory(file)) {
            for (File f : Objects.requireNonNull(file.toFile().listFiles())) {
                addFiles(Collections.singletonList(f.toPath()));
            }
            return;
        }

        addFiles(Collections.singletonList(file));
    }

    public void addFiles(List<Path> files) throws NoSuchZipFileException, IOException, PathNotFoundException {
        if (!Files.isRegularFile(getZipFile())) throw new NoSuchZipFileException();

        Path tmpFile = Files.createTempFile(null, null);
        List<Path> archiveFiles = new ArrayList<>();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tmpFile))) {
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(getZipFile()))) {
                ZipEntry entry = zipInputStream.getNextEntry();

                while (entry != null) {
                    String fileName = entry.getName();
                    archiveFiles.add(Paths.get(fileName));

                    zipOutputStream.putNextEntry(new ZipEntry(fileName));
                    copyData(zipInputStream, zipOutputStream);

                    zipOutputStream.closeEntry();
                    zipInputStream.closeEntry();

                    entry = zipInputStream.getNextEntry();
                }
            }
            for (Path file : files) {
                if (Files.isRegularFile(file)) {
                    if (archiveFiles.contains(file.getFileName())) {
                    } else {
                        addNewZipEntry(zipOutputStream, file.getParent(), file.getFileName());
                    }
                } else {
                    throw new PathNotFoundException();
                }
            }
        }
        Files.move(tmpFile, getZipFile(), StandardCopyOption.REPLACE_EXISTING);
    }


}
