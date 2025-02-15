package nicholas.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import nicholas.exception.EmptyCommandException;
import nicholas.exception.NotTaskException;

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
        UserCommandHandler userCommandHandler = new UserCommandHandler();

        // Show greeting message
        userCommandHandler.getGreeting();

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        // Main loop to process user commands
        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                String[] commandParts = userCommandHandler.getCommandParts(userInput);
                String command = userCommandHandler.getCommand(commandParts);
                userCommandHandler.processUserCommand(userInput, commandParts, command);
            } catch (NotTaskException | EmptyCommandException e) {
                userCommandHandler.showErrorMessage(e);
            }
            userCommandHandler.updateStorage();
            userInput = scanner.nextLine();
        }
        // Show goodbye message
        userCommandHandler.getExitMessage();
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
     * @param userInput The raw input string from the user, containing the command and any arguments.
     * @return A string response that will be displayed to the user in the GUI
     *     indicating the result of the command or an error message.
     * @throws NotTaskException If an unrecognized command is entered by the user.
     * @throws EmptyCommandException If the user input is missing required arguments or is improperly formatted.
     */
    public String getGuiResponse(String userInput) throws FileNotFoundException {
        // Initialize components
        GuiResponseHandler guiResponseHandler = new GuiResponseHandler();
        guiResponseHandler.loadTasks();
        String response;
        try {
            String[] commandParts = guiResponseHandler.getCommandParts(userInput);
            String command = guiResponseHandler.getCommand(commandParts);
            response = guiResponseHandler.processUserCommand(commandParts, command);
            // Save updated tasks
            guiResponseHandler.updateStorage();
        } catch (NotTaskException | EmptyCommandException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
        return response;
    }
}


