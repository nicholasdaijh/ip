package nicholas.tasks;

public abstract class Task {
    private String description;
    private boolean isDone;

    // Constructor to initialize a task with a description.
    public Task(String description) {
        this.description = description;
        this.isDone = false;  // Tasks are initially not done.
    }

    // Abstract method to get the task's status icon (overridden in subclasses).
    public String getStatusIcon() {
        if (this.isDone) {
            return "X";
        } else {
            return " ";
        }
    }

    // Get the description of the task.
    public String getDescription() {
        return this.description;
    }

    // Mark the task as done.
    public void markAsDone() {
        this.isDone = true;
    }

    // Mark the task as undone.
    public void markAsUndone() {
        this.isDone = false;
    }

    // Override toString method to return a string representation of the task.
    @Override
    public String toString() {
        return "[" + getTaskType() + "]" + "[" + getStatusIcon() + "] " + description;
    }

    // Method to get the type of the task. To be implemented in subclasses.
    public abstract String getTaskType();
}
