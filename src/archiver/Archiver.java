package archiver;

import archiver.command.CommandExecutor;
import archiver.exception.NoSuchZipFileException;
import archiver.gui.ArchiverGui;
import archiver.operations.Operation;

public class Archiver {
    public static void main(String[] args) {
        new ArchiverGui();
        Operation operation = null;

        do {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
            } catch (NoSuchZipFileException e) {
                ConsoleHelper.writeMessage("You didn't select an archive or you selected an invalid file.");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("An error occurred. Please check the entered data.");
            }
        } while (operation != Operation.EXIT);
    }

    private static Operation askOperation() throws Exception {
        ConsoleHelper.writeMessage("");
        ConsoleHelper.writeMessage("Select an operation:");
        ConsoleHelper.writeMessage(String.format("\t %d - Zip files into an archive", Operation.CREATE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Add file/s into an archive", Operation.ADD.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Remove file from the archive.", Operation.REMOVE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Extract files from zip archive", Operation.EXTRACT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - View content of the archive", Operation.CONTENT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Exit", Operation.EXIT.ordinal()));

        return Operation.values()[ConsoleHelper.readInt()];
    }
}
