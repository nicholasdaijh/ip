package nicholas.tasks;

/**
 * Represents a task that can be marked as done or not done.
 * This is an abstract class that serves as a base for different types of tasks.
 */
public abstract class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;  // Tasks are initially not done.
    }

    /**
     * Returns the status icon of the task.
     * "X" if the task is done, otherwise an empty space.
     *
     * @return A string representing the status of the task.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Returns the description of the task.
     *
     * @return The task's description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task,
     * including its type, status, and description.
     *
     * @return A formatted string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + getTaskType() + "]" + "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the type of the task.
     * This method is abstract and must be implemented in subclasses.
     *
     * @return A string representing the type of the task.
     */
    public abstract String getTaskType();
}
