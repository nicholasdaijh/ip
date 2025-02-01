package nicholas.ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;






import nicholas.exception.NotTaskException;
import nicholas.exception.EmptyCommandException;
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
                    int markIndex = Integer.parseInt(commandParts[1]) - 1;
                    taskList.markTaskAsDone(markIndex);
                    ui.showTaskMarked(taskList.getTasks().get(markIndex));
                    break;

                case "unmark":
                    int unmarkIndex = Integer.parseInt(commandParts[1]) - 1;
                    taskList.markTaskAsUndone(unmarkIndex);
                    ui.showTaskUnmarked(taskList.getTasks().get(unmarkIndex));
                    break;

                case "list":
                    ui.showTaskList(taskList.getTasks().toArray(new Task[0]), taskList.size());
                    break;

                case "delete":
                    int deleteIndex = Integer.parseInt(commandParts[1]) - 1;
                    Task taskToDelete = taskList.getTasks().get(deleteIndex);
                    taskList.deleteTask(deleteIndex);
                    ui.showTaskDeleted(taskToDelete, taskList.size());
                    break;

                case "find":
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
}