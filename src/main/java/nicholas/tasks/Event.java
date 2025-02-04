package nicholas.tasks;

import nicholas.ui.Parser;

/**
 * Represents an Event task that has a start and end time.
 * The event task contains a description, a start time, and an end time.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     * The start and end times are parsed into a formatted date.
     *
     * @param description The description of the event.
     * @param from The start time of the event in "yyyy-MM-dd HHmm" format.
     * @param to The end time of the event in "yyyy-MM-dd HHmm" format.
     */
    public Event(String description, String from, String to) {
        // Call parent constructor
        super(description);
        Parser parser = new Parser();
        this.from = parser.parseDate(from);
        this.to = parser.parseDate(to);
    }

    /**
     * Returns the type of the task.
     *
     * @return "E", representing an Event task.
     */
    @Override
    public String getTaskType() {
        // 'E' for Event task
        return "E";
    }

    /**
     * Returns the string representation of the Event task,
     * including its status, description, and time range.
     *
     * @return A formatted string of the Event task.
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
