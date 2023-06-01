package archiver.command;

import archiver.ConsoleHelper;

public class ZipExitCommand implements Command {
    @Override
    public void execute() {
        ConsoleHelper.writeMessage("Closing program...");

        ConsoleHelper.writeMessage("Program closed.");
    }
}
