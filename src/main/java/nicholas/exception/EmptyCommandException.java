package nicholas.exception;

/**
 * Represents an exception that is thrown when a user enters a command without a description.
 */
public class EmptyCommandException extends DukeException {
    /**
     * Constructs an EmptyCommandException with a specific command type.
     *
     * @param commandType The type of command that is missing a description.
     */
    public EmptyCommandException(String commandType) {
        super("OOPS!!! The description of a " + commandType + " cannot be empty.");
    }
}
