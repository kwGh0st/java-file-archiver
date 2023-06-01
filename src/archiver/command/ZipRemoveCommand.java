package archiver.command;

import archiver.ConsoleHelper;
import archiver.operations.RemoveZipOperation;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipRemoveCommand implements Command {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Enter the path to the archive.");
        Path archive = Paths.get(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Enter name of the file you want to remove");
        Path fileName = Paths.get(ConsoleHelper.readString());
        new RemoveZipOperation(archive).removeFile(fileName);
    }
}
