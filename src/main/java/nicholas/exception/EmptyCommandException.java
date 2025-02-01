package nicholas.exception;

public class EmptyCommandException extends DukeException{
    public EmptyCommandException(String commandType) {
        super("OOPS!!! The description of a " + commandType + " cannot be empty.");
    }
}
