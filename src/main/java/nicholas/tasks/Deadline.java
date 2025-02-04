package nicholas.tasks;

import nicholas.ui.Parser;

/**
 * Represents a Deadline task that has a due date or time.
 * A Deadline task contains a description and a deadline date.
 */
public class Deadline extends Task {
    // Deadline date or time
    private String by;
    /**
     * Constructs a Deadline task with the given description and due date.
     * The due date is parsed into a formatted date.
     *
     * @param description The description of the deadline task.
     * @param by The due date in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        // Call parent constructor
        super(description);
        Parser parser = new Parser();
        this.by = parser.parseDate(by);
    }

    /**
     * Returns the type of the task.
     *
     * @return "D", representing a Deadline task.
     */
    @Override
    public String getTaskType() {
        // 'D' for Deadline task
        return "D";
    }

    /**
     * Returns the string representation of the Deadline task,
     * including its status, description, and due date.
     *
     * @return A formatted string of the Deadline task.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
