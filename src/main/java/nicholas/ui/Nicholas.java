package nicholas.ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import nicholas.exception.EmptyCommandException;
import nicholas.exception.NotTaskException;
import nicholas.tasks.Task;
import nicholas.tasks.TaskList;
import nicholas.tasks.Todo;

/**
 * The main class for the Nicholas task manager application.
 * It initializes components, loads tasks, processes user input, and manages tasks.
 */
public class Nicholas {

    /**
     * The entry point of the application.
     * It initializes components, loads tasks from file, and processes user commands in a loop.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an error occurs while reading/writing the task file.
     */
    public static void main(String[] args) throws IOException {
        // Initialize components
        Ui ui = new Ui();
        Storage storage = new Storage("tasks.txt");
        Parser parser = new Parser();
        TaskList taskList = new TaskList();

        // Load tasks from file
        List<Task> loadedTasks = storage.loadTasks();
        for (Task task : loadedTasks) {
            taskList.addTask(task);
        }

        // Show greeting message
        ui.showGreeting();

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        // Main loop to process user commands
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                String[] commandParts = parser.parseCommand(userInput);
                String command = commandParts[0].toLowerCase();

                switch (command) {
                case "mark":
                    if ((userInput.length() == 4) || commandParts.length < 2
                            || commandParts[1].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    int markIndex = Integer.parseInt(commandParts[1]) - 1;
                    taskList.markTaskAsDone(markIndex);
                    ui.showTaskMarked(taskList.getTasks().get(markIndex));
                    break;
                case "unmark":
                    if ((userInput.length() == 6) || commandParts.length < 2
                            || commandParts[1].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    int unmarkIndex = Integer.parseInt(commandParts[1]) - 1;
                    taskList.markTaskAsUndone(unmarkIndex);
                    ui.showTaskUnmarked(taskList.getTasks().get(unmarkIndex));
                    break;
                case "list":
                    ui.showTaskList(taskList.getTasks().toArray(new Task[0]), taskList.size());
                    break;
                case "delete":
                    if ((userInput.length() == 6) || commandParts.length < 2
                            || commandParts[1].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    int deleteIndex = Integer.parseInt(commandParts[1]) - 1;
                    Task taskToDelete = taskList.getTasks().get(deleteIndex);
                    taskList.deleteTask(deleteIndex);
                    ui.showTaskDeleted(taskToDelete, taskList.size());
                    break;
                case "find":
                    if ((userInput.length() == 4) || commandParts.length < 2
                            || commandParts[1].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    ui.showTaskFind(taskList.getTasks().toArray(new Task[0]), commandParts[1], taskList.size());
                    break;
                case "todo":
                    if ((userInput.length() == 4) || commandParts.length < 2
                            || commandParts[1].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    String todoDescription = commandParts[1].trim();
                    Task todoTask = new Todo(todoDescription);
                    taskList.addTask(todoTask);
                    ui.showTaskAdded(todoTask, taskList.size());
                    break;
                case "deadline":
                    if ((userInput.length() == 8) || commandParts.length < 2
                            || commandParts[1].split("/by")[0].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    Task deadlineTask = parser.parseDeadline(commandParts[1]);
                    taskList.addTask(deadlineTask);
                    ui.showTaskAdded(deadlineTask, taskList.size());
                    break;
                case "event":
                    if ((userInput.length() == 5) || commandParts.length < 2
                            || commandParts[1].split("/from")[0].trim().isEmpty()) {
                        throw new EmptyCommandException(command);
                    }
                    Task eventTask = parser.parseEvent(commandParts[1]);
                    taskList.addTask(eventTask);
                    ui.showTaskAdded(eventTask, taskList.size());
                    break;
                default:
                    throw new NotTaskException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (NotTaskException | EmptyCommandException e) {
                ui.showErrorMessage(e.getMessage());
            }

            storage.emptyFile("tasks.txt");
            storage.saveTasks(taskList.getTasks());
            userInput = scanner.nextLine();
        }

        // Save tasks to file before exiting
        storage.saveTasks(taskList.getTasks());

        // Show goodbye message
        ui.showBye();
    }

    /**
     * Handles user input, processes commands related to task management, and returns the appropriate GUI response.
     *
     * This method takes the user input, interprets it as a command, modifies the task list accordingly
     * (adding, marking, unmarking, deleting, finding, etc.), and returns a string to be displayed on the GUI.
     * It also handles any errors and exceptions during the
     * process, providing an error message when necessary. The method interacts with various classes,
     * including `Ui`, `Storage`, `Parser`, and `TaskList`, and updates the tasks stored in a file.
     *
     * @param input The raw input string from the user, containing the command and any arguments.
     * @return A string response that will be displayed to the user in the GUI
     *     indicating the result of the command or an error message.
     * @throws NumberFormatException If an input containing a number cannot be parsed correctly.
     * @throws NotTaskException If an unrecognized command is entered by the user.
     * @throws EmptyCommandException If the user input is missing required arguments or is improperly formatted.
     */
    public String getGuiResponse(String input) {
        Storage storage = new Storage("tasks.txt");
        Parser parser = new Parser();
        TaskList taskList = new TaskList();

        // Load tasks from file
        List<Task> loadedTasks;
        try {
            loadedTasks = storage.loadTasks();
            for (Task task : loadedTasks) {
                taskList.addTask(task);
            }
        } catch (IOException e) {
            return "Error loading tasks: " + e.getMessage();
        }

        StringBuilder response = new StringBuilder();

        try {
            String[] commandParts = parser.parseCommand(input);
            String command = commandParts[0].toLowerCase();

            switch (command) {
            case "mark":
                if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
                    throw new EmptyCommandException(command);
                }
                int markIndex = Integer.parseInt(commandParts[1]) - 1;
                taskList.markTaskAsDone(markIndex);
                response.append("Nice! I've marked this task as done:\n").append(taskList.getTasks().get(markIndex));
                break;
            case "unmark":
                if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
                    throw new EmptyCommandException(command);
                }
                int unmarkIndex = Integer.parseInt(commandParts[1]) - 1;
                taskList.markTaskAsUndone(unmarkIndex);
                response.append("OK, I've unmarked this task:\n").append(taskList.getTasks().get(unmarkIndex));
                break;
            case "list":
                if (taskList.size() == 0) {
                    response.append("Your task list is empty.");
                } else {
                    response.append("Here are your tasks:\n");
                    for (int i = 0; i < taskList.size(); i++) {
                        response.append((i + 1)).append(". ").append(taskList.getTasks().get(i)).append("\n");
                    }
                }
                break;
            case "delete":
                if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
                    throw new EmptyCommandException(command);
                }
                int deleteIndex = Integer.parseInt(commandParts[1]) - 1;
                Task taskToDelete = taskList.getTasks().get(deleteIndex);
                taskList.deleteTask(deleteIndex);
                response.append("Noted. I've removed this task:\n").append(taskToDelete);
                break;
            case "find":
                if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
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
                break;
            case "todo":
                if (commandParts.length < 2 || commandParts[1].trim().isEmpty()) {
                    throw new EmptyCommandException(command);
                }
                Task todoTask = new Todo(commandParts[1].trim());
                taskList.addTask(todoTask);
                response.append("Got it. I've added this task:\n").append(todoTask);
                break;
            case "deadline":
                if (commandParts.length < 2 || commandParts[1].split("/by")[0].trim().isEmpty()) {
                    throw new EmptyCommandException(command);
                }
                Task deadlineTask = parser.parseDeadline(commandParts[1]);
                taskList.addTask(deadlineTask);
                response.append("Got it. I've added this task:\n").append(deadlineTask);
                break;
            case "event":
                if (commandParts.length < 2 || commandParts[1].split("/from")[0].trim().isEmpty()) {
                    throw new EmptyCommandException(command);
                }
                Task eventTask = parser.parseEvent(commandParts[1]);
                taskList.addTask(eventTask);
                response.append("Got it. I've added this task:\n").append(eventTask);
                break;
            case "bye":
                response.append("Bye! Hope to see you again soon!");
                break;
            default:
                throw new NotTaskException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            // Save updated tasks
            storage.emptyFile("tasks.txt");
            storage.saveTasks(taskList.getTasks());

        } catch (NotTaskException | EmptyCommandException e) {
            response.append("Error: ").append(e.getMessage());
        } catch (NumberFormatException e) {
            response.append("Error: Please enter a valid number.");
        } catch (Exception e) {
            response.append("Unexpected error: ").append(e.getMessage());
        }

        return response.toString();
    }
}


