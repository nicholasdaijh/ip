package nicholas.tasks;

/**
 * Represents a Todo task.
 * A Todo task is a basic task with only a description and no date or time constraints.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the Todo task.
     */
    public Todo(String description) {
        super(description);  // Call parent constructor
    }

    /**
     * Returns the type of the task.
     *
     * @return "T", representing a Todo task.
     */
    @Override
    public String getTaskType() {
        return "T";  // 'T' for Todo task
    }
}
