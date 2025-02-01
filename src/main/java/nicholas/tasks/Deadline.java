package nicholas.tasks;

import nicholas.ui.Parser;

public class Deadline extends Task {
    private String by;  // Deadline date or time

    public Deadline(String description, String by) {
        super(description);  // Call parent constructor
        Parser parser = new Parser();
        this.by = parser.parseDate(by);
    }


    @Override
    public String getTaskType() {
        return "D";  // 'D' for Deadline task
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
