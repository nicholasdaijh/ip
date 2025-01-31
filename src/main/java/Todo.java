public class Todo extends Task {

    public Todo(String description) {
        super(description);  // Call parent constructor
    }

    @Override
    public String getTaskType() {
        return "T";  // 'T' for Todo task
    }
}
