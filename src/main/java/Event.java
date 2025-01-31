public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);  // Call parent constructor
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskType() {
        return "E";  // 'E' for Event task
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
