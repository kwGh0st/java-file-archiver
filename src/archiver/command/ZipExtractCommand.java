package archiver.command;

import archiver.ConsoleHelper;
import archiver.operations.ExtractAllZipOperation;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand implements Command {

    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Select archive to unpacked");
        Path archive = Paths.get(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Select output folder");
        Path outputFolder = Paths.get(ConsoleHelper.readString());
        new ExtractAllZipOperation(archive).extractAll(outputFolder);
        ConsoleHelper.writeMessage("Archive unpacked");
    }

}
