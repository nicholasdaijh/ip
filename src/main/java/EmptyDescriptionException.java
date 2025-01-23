public class EmptyDescriptionException extends DukeException{
    public EmptyDescriptionException(String task) {
        super("OOPS!!! The description of a " + task + " cannot be empty.");
    }
}
