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
 * Handles user commands and responses for the GUI interface of the task manager.
 * This class is responsible for processing user inputs, executing the corresponding
 * commands, and returning appropriate responses.
 */
public class GuiResponseHandler {
    private Storage storage;
    private Parser parser;
    private TaskList taskList;
    private List<Task> loadedTasks;

    /**
     * Initializes a new instance of the GuiResponseHandler with default components.
     */
    public GuiResponseHandler() {
        storage = new Storage("tasks.txt");
        parser = new Parser();
        taskList = new TaskList();
    }

    /**
     * Processes the user command and executes the corresponding action.
     *
     * @param commandParts The parsed parts of the user command.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If the command is empty.
     * @throws NotTaskException If the command is not recognized.
     */
    public String processUserCommand(
            String[] commandParts, String command) throws EmptyCommandException, NotTaskException {
        switch (command) {
        case "mark":
            return handleMarkCommand(commandParts, command);
        case "unmark":
            return handleUnmarkCommand(commandParts, command);
        case "list":
            return handleListCommand();
        case "delete":
            return handleDeleteCommand(commandParts, command);
        case "find":
            return handleFindCommand(commandParts, command);
        case "upgrade":
            return handleUpgradePriorityCommand(commandParts, command);
        case "downgrade":
            return handleDowngradePriorityCommand(commandParts, command);
        case "todo":
            return handleTodoCommand(commandParts, command);
        case "deadline":
            return handleDeadlineCommand(commandParts, command);
        case "event":
            return handleEventCommand(commandParts, command);
        case "bye":
            return handleExitCommand();
        default:
            throw new NotTaskException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Loads tasks from storage into memory.
     *
     * @throws FileNotFoundException If the file cannot be found.
     */
    public void loadTasks() throws FileNotFoundException {
        try {
            loadedTasks = storage.loadTasks();
            for (Task task : loadedTasks) {
                taskList.addTask(task);
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Parses the user input into command parts.
     *
     * @param userInput The raw user input.
     * @return An array containing command parts.
     */
    public String[] getCommandParts(String userInput) {
        return parser.parseCommand(userInput);
    }

    /**
     * Extracts the command keyword from the parsed command parts.
     *
     * @param commandParts The parsed command parts.
     * @return The command keyword in lowercase.
     */
    public String getCommand(String[] commandParts) {
        return commandParts[0].toLowerCase();
    }

    /**
     * Handles the "mark" command to mark a task as done.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If no task index is provided.
     */
    public String handleMarkCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int markIndex = Integer.parseInt(commandParts[1]) - 1;
        taskList.markTaskAsDone(markIndex);
        response = response.append("Nice! I've marked this task as done:\n").append(taskList.getTasks().get(markIndex));
        return response.toString();
    }

    /**
     * Handles the "unmark" command to mark a task as not done.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If no task index is provided.
     */
    public String handleUnmarkCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int unmarkIndex = Integer.parseInt(commandParts[1]) - 1;
        taskList.markTaskAsUndone(unmarkIndex);
        response.append("OK, I've unmarked this task:\n").append(taskList.getTasks().get(unmarkIndex));
        return response.toString();
    }

    /**
     * Handles the "list" command to display all tasks.
     *
     * @return The list of tasks as a formatted string.
     */
    public String handleListCommand() throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        if (taskList.size() == 0) {
            response.append("Your task list is empty.");
        } else {
            response.append("Here are your tasks:\n");
            for (int i = 0; i < taskList.size(); i++) {
                response.append((i + 1)).append(". ").append(taskList.getTasks().get(i)).append("\n");
            }
        }
        return response.toString();
    }

    /**
     * Handles the "delete" command to remove a task.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If no task index is provided.
     */
    public String handleDeleteCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int deleteIndex = Integer.parseInt(commandParts[1]) - 1;
        Task taskToDelete = taskList.getTasks().get(deleteIndex);
        taskList.deleteTask(deleteIndex);
        response.append("Noted. I've removed this task:\n").append(taskToDelete);
        return response.toString();
    }

    /**
     * Handles the "find" command to search for tasks containing a keyword.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The list of matching tasks.
     * @throws EmptyCommandException If no search keyword is provided.
     */
    public String handleFindCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        response.append("Here are the matching tasks containing '").append(commandParts[1]).append("':\n");
        int count = 1;
        for (Task task : taskList.getTasks()) {
            if (task.getDescription().contains(commandParts[1])) {
                response.append(count).append(". ").append(task).append("\n");
                count++;
            }
        }
        if (count == 1) {
            response.append("No matching tasks found.");
        }
        return response.toString();
    }

    /**
     * Handles the "upgrade" command to increase the priority of a task.
     * <p>
     * If the task's priority is {@code LOW}, it is upgraded to {@code MEDIUM}.
     * If the task's priority is {@code MEDIUM}, it is upgraded to {@code HIGH}.
     * If the task is already at {@code HIGH} priority, no changes are made.
     * </p>
     *
     * @param commandParts The parsed command parts containing the task index.
     * @param command The command keyword.
     * @return The response message confirming the priority upgrade.
     * @throws EmptyCommandException If no task index is provided.
     */
    public String handleUpgradePriorityCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int upgradeIndex = Integer.parseInt(commandParts[1]) - 1;
        taskList.upgradeTask(upgradeIndex);
        response = response.append("OK, I've upgraded this task:\n").append(taskList.getTasks().get(upgradeIndex));
        return response.toString();
    }

    /**
     * Handles the "downgrade" command to decrease the priority of a task.
     * <p>
     * If the task's priority is {@code HIGH}, it is downgraded to {@code MEDIUM}.
     * If the task's priority is {@code MEDIUM}, it is downgraded to {@code LOW}.
     * If the task is already at {@code LOW} priority, no changes are made.
     * </p>
     *
     * @param commandParts The parsed command parts containing the task index.
     * @param command The command keyword.
     * @return The response message confirming the priority downgrade.
     * @throws EmptyCommandException If no task index is provided.
     */
    public String handleDowngradePriorityCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        int downgradeIndex = Integer.parseInt(commandParts[1]) - 1;
        taskList.downgradeTask(downgradeIndex);
        response = response.append("OK, I've downgraded this task:\n").append(taskList.getTasks().get(downgradeIndex));
        return response.toString();
    }

    /**
     * Handles the "todo" command to add a new Todo task.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If the task description is empty.
     */
    public String handleTodoCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        Task todoTask = new Todo(commandParts[1].trim());
        taskList.addTask(todoTask);
        response.append("Got it. I've added this task:\n").append(todoTask);
        return response.toString();
    }

    /**
     * Handles the "deadline" command to add a new Deadline task.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If the task description is empty.
     */
    public String handleDeadlineCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].split("/by")[0].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        Task deadlineTask = parser.parseDeadline(commandParts[1]);
        taskList.addTask(deadlineTask);
        response.append("Got it. I've added this task:\n").append(deadlineTask);
        return response.toString();
    }

    /**
     * Handles the "event" command to add a new Event task.
     *
     * @param commandParts The parsed command parts.
     * @param command The command keyword.
     * @return The response message.
     * @throws EmptyCommandException If the task description is empty.
     */
    public String handleEventCommand(String[] commandParts, String command) throws EmptyCommandException {
        StringBuilder response = new StringBuilder();
        boolean isWrongCommandPartsLength = commandParts.length < 2;
        boolean isEmptyCommand = commandParts[1].split("/from")[0].trim().isEmpty();
        if (isWrongCommandPartsLength || isEmptyCommand) {
            throw new EmptyCommandException(command);
        }
        Task eventTask = parser.parseEvent(commandParts[1]);
        taskList.addTask(eventTask);
        response.append("Got it. I've added this task:\n").append(eventTask);
        return response.toString();
    }

    /**
     * Handles the "bye" command to exit the application.
     *
     * @return The exit message.
     */
    public String handleExitCommand() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Updates the storage file with the current tasks.
     *
     * @throws IOException If an error occurs while writing to the file.
     */
    public void updateStorage() throws IOException {
        storage.emptyFile("tasks.txt");
        storage.saveTasks(taskList.getTasks());
    }
}
