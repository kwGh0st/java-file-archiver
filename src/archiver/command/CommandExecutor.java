package archiver.command;

import archiver.operations.Operation;

import java.util.HashMap;

public class CommandExecutor {
    private static final HashMap<Operation, Command> allCommands = new HashMap<>();

    static {
        allCommands.put(Operation.ADD, new ZipAddCommand());
        allCommands.put(Operation.CREATE, new ZipCreateCommand());
        allCommands.put(Operation.REMOVE, new ZipRemoveCommand());
        allCommands.put(Operation.CONTENT, new ZipContentCommand());
        allCommands.put(Operation.EXTRACT, new ZipExtractCommand());
        allCommands.put(Operation.EXIT, new ZipExitCommand());
    }

    public static void execute(Operation operation) throws Exception {
        allCommands.get(operation).execute();
    }


}
