package nicholas.tasks;

import nicholas.ui.Parser;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructs an Event task with a description, start time, end time, and priority.
     *
     * @param description The description of the event.
     * @param from The start time.
     * @param to The end time.
     */
    public Event(String description, String from, String to) {
        super(description);
        Parser parser = new Parser();
        this.from = parser.parseDate(from);
        this.to = parser.parseDate(to);
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
