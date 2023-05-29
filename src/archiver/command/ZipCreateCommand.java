package archiver.command;

import archiver.ConsoleHelper;
import archiver.operations.CreateZipOperation;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Creating an archive.");
        ConsoleHelper.writeMessage("Enter the destination path ");
        Path destinationPath = Paths.get(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Enter the source path");
        Path source = Paths.get(ConsoleHelper.readString());
        new CreateZipOperation(destinationPath).createZip(source);
    }
}
