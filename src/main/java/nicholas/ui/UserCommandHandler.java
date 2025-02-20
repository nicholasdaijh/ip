package nicholas.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import nicholas.exception.EmptyCommandException;
import nicholas.exception.NotTaskException;
import nicholas.tasks.Task;
import nicholas.tasks.TaskList;
import nicholas.tasks.Todo;

/**
 * The UserCommandHandler class is responsible for processing user commands,
 * handling the interaction with the UI, managing the task list, and updating
 * the storage file. It processes various commands such as marking/unmarking tasks,
 * listing tasks, adding tasks, and handling errors.
 */
public class UserCommandHandler {
    private Ui ui;
    private Storage storage;
    private Parser parser;
    private TaskList taskList;

    /**
     * Constructs a UserCommandHandler instance, initializing the UI, storage,
     * parser, and task list. It also loads the tasks from the storage file.
     */
    public UserCommandHandler() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        taskList = new TaskList();

        // Load tasks from file
        try {
            List<Task> loadedTasks = storage.loadTasks();
            for (Task task : loadedTasks) {
                taskList.addTask(task);
            }
        } catch (FileNotFoundException e) {
            ui.showErrorMessage("Error: The task file was not found.");
        }
    }

    /**
     * Processes a user input command by calling the appropriate handler method
     * based on the given command.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command after parsing
     * @param command the command string (e.g., "mark", "unmark", "list")
     * @throws NotTaskException if the command is not recognized
     * @throws EmptyCommandException if the command is missing required arguments
     */
    public void processUserCommand(
            String userInput, String[] commandParts, String command) throws NotTaskException, EmptyCommandException {
        switch (command) {
        case "mark":
            handleMarkCommand(userInput, commandParts, command);
            break;
        case "unmark":
            handleUnmarkCommand(userInput, commandParts, command);
            break;
        case "list":
            handleListCommand();
            break;
        case "delete":
            handleDeleteCommand(userInput, commandParts, command);
            break;
        case "find":
            handleFindCommand(userInput, commandParts, command);
            break;
        case "upgrade":
            handleUpgradePriorityCommand(userInput, commandParts, command);
            break;
        case "downgrade":
            handleDowngradePriorityCommand(userInput, commandParts, command);
            break;
        case "todo":
            handleTodoCommand(userInput, commandParts, command);
            break;
        case "deadline":
            handleDeadlineCommand(userInput, commandParts, command);
            break;
        case "event":
            handleEventCommand(userInput, commandParts, command);
            break;
        default:
            throw new NotTaskException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Displays the greeting message from the UI.
     */
    public void getGreeting() {
        ui.showGreeting();
    }

    /**
     * Displays the exit message from the UI.
     */
    public void getExitMessage() {
        ui.showBye();
    }

    /**
     * Parses the user input into command parts.
     *
     * @param userInput the raw input from the user
     * @return an array of strings representing the command and its arguments
     */
    public String[] getCommandParts(String userInput) {
        return parser.parseCommand(userInput);
    }

    /**
     * Extracts the command from the command parts.
     *
     * @param commandParts the parts of the command
     * @return the command as a string
     */
    public String getCommand(String[] commandParts) {
        return commandParts[0].toLowerCase();
    }

    /**
     * Handles the "mark" command to mark a task as done.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "mark" command string
     * @throws EmptyCommandException if the command is missing task index
     */
    public void handleMarkCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 4;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int markIndex = Integer.parseInt(commandParts[1]) - 1;
        taskList.markTaskAsDone(markIndex);
        ui.showTaskMarked(taskList.getTasks().get(markIndex));
    }

    /**
     * Handles the "unmark" command to mark a task as undone.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "unmark" command string
     * @throws EmptyCommandException if the command is missing task index
     */
    public void handleUnmarkCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 6;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int unmarkIndex = Integer.parseInt(commandParts[1]) - 1;
        taskList.markTaskAsUndone(unmarkIndex);
        ui.showTaskUnmarked(taskList.getTasks().get(unmarkIndex));
    }

    /**
     * Displays the list of tasks.
     */
    public void handleListCommand() {
        ui.showTaskList(taskList.getTasks().toArray(new Task[0]), taskList.size());
    }

    /**
     * Handles the "delete" command to delete a task from the task list.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "delete" command string
     * @throws EmptyCommandException if the command is missing task index
     */
    public void handleDeleteCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 6;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int deleteIndex = Integer.parseInt(commandParts[1]) - 1;
        Task taskToDelete = taskList.getTasks().get(deleteIndex);
        taskList.deleteTask(deleteIndex);
        ui.showTaskDeleted(taskToDelete, taskList.size());
    }

    /**
     * Handles the "find" command to search tasks based on the provided keyword.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "find" command string
     * @throws EmptyCommandException if the command is missing search keyword
     */
    public void handleFindCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 4;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        ui.showTaskFind(taskList.getTasks().toArray(new Task[0]), commandParts[1], taskList.size());
    }

    /**
     * Handles the "upgrade" command to increase the priority of a specified task.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "upgrade" command string
     * @throws EmptyCommandException if the command is missing the task index
     */
    public void handleUpgradePriorityCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 7;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int upgradeIndex = Integer.parseInt(commandParts[1]) - 1;
        Task taskToUpgrade = taskList.getTasks().get(upgradeIndex);
        taskList.upgradeTask(upgradeIndex);
        ui.showTaskUpgraded(taskToUpgrade);
    }

    /**
     * Handles the "downgrade" command to decrease the priority of a specified task.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "downgrade" command string
     * @throws EmptyCommandException if the command is missing the task index
     */
    public void handleDowngradePriorityCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 9;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int downgradeIndex = Integer.parseInt(commandParts[1]) - 1;
        Task taskToDowngrade = taskList.getTasks().get(downgradeIndex);
        taskList.downgradeTask(downgradeIndex);
        ui.showTaskDowngraded(taskToDowngrade);
    }

    /**
     * Handles the "todo" command to add a new Todo task.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "todo" command string
     * @throws EmptyCommandException if the command is missing the task description
     */
    public void handleTodoCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 4;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        String todoDescription = commandParts[1].trim();
        Task todoTask = new Todo(todoDescription);
        taskList.addTask(todoTask);
        ui.showTaskAdded(todoTask, taskList.size());
    }

    /**
     * Handles the "deadline" command to add a new Deadline task.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "deadline" command string
     * @throws EmptyCommandException if the command is missing required deadline details
     */
    public void handleDeadlineCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 8;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].split("/by")[0].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        Task deadlineTask = parser.parseDeadline(commandParts[1]);
        taskList.addTask(deadlineTask);
        ui.showTaskAdded(deadlineTask, taskList.size());
    }

    /**
     * Handles the "event" command to add a new Event task.
     *
     * @param userInput the raw input from the user
     * @param commandParts the parts of the command
     * @param command the "event" command string
     * @throws EmptyCommandException if the command is missing required event details
     */
    public void handleEventCommand(
            String userInput, String[] commandParts, String command) throws EmptyCommandException {
        boolean isInputLengthEqualToCommandLength = userInput.length() == 5;
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand;
        if (isWrongCommandPartsLength) {
            isEmptyCommand = true;
        } else {
            isEmptyCommand = commandParts[1].split("/from")[0].trim().isEmpty();
        }
        if (isInputLengthEqualToCommandLength || isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        Task eventTask = parser.parseEvent(commandParts[1]);
        taskList.addTask(eventTask);
        ui.showTaskAdded(eventTask, taskList.size());
    }

    /**
     * Displays an error message in the UI.
     *
     * @param e the exception whose message will be displayed
     */
    public void showErrorMessage(Exception e) {
        ui.showErrorMessage(e.getMessage());
    }

    /**
     * Updates the storage file by saving the current task list to the file.
     *
     * @throws IOException if an error occurs while saving to the storage file
     */
    public void updateStorage() throws IOException {
        storage.emptyFile("tasks.txt");
        storage.saveTasks(taskList.getTasks());
    }
}
