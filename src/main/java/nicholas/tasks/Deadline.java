package nicholas.tasks;

import nicholas.ui.Parser;

/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Constructs a Deadline task with a description, due date, and priority.
     *
     * @param description The description of the deadline task.
     * @param by The due date.
     */
    public Deadline(String description, String by) {
        super(description);
        Parser parser = new Parser();
        this.by = parser.parseDate(by);
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
