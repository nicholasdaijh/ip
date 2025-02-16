package nicholas.tasks;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description and priority.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getTaskType() {
        return "T";
    }
}
