package nicholas.exception;

/**
 * Represents an exception that is thrown when an unrecognized command is entered.
 */
public class NotTaskException extends DukeException {
    /**
     * Constructs a NotTaskException with a default error message.
     *
     * @param message The error message explaining the invalid command.
     */
    public NotTaskException(String message) {
        super("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
