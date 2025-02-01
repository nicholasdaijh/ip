package nicholas.exception;

/**
 * Represents the base exception for the application.
 * All custom exceptions extend from this class.
 */
public class DukeException extends Exception {
    /**
     * Constructs a DukeException with a specific error message.
     *
     * @param message The error message to be displayed.
     */
    public DukeException(String message) {
        super(message);
    }
}
