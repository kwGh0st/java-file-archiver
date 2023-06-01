package archiver.command;

import archiver.ConsoleHelper;
import archiver.files.FileProperties;
import archiver.operations.ContentZipOperation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ZipContentCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Enter the path to the archive.");
        Path path = Paths.get(ConsoleHelper.readString());
        List<FileProperties> files = new ContentZipOperation(path).getFileList();

        ConsoleHelper.writeMessage("Showing content...");
        for (FileProperties file : files) {
            System.out.println(file.toString());
        }
    }
}
