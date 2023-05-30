package archiver.command;

import archiver.ConsoleHelper;
import archiver.operations.AddZipOperation;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Adding file/s to the archive");
        ConsoleHelper.writeMessage("Enter the destination path ");
        Path destinationPath = Paths.get(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Enter the source path");
        Path source = Paths.get(ConsoleHelper.readString());
        new AddZipOperation(destinationPath).addFile(source);
    }
}
