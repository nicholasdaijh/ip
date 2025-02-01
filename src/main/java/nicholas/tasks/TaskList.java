package nicholas.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks.
 * This class provides methods to add, remove, and update tasks.
 */
public class TaskList {
    private List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list based on the given index.
     *
     * @param taskIndex The index of the task to be deleted.
     */
    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.remove(taskIndex);
        }
    }

    /**
     * Marks a task as done based on the given index.
     *
     * @param taskIndex The index of the task to be marked as done.
     */
    public void markTaskAsDone(int taskIndex) {
        tasks.get(taskIndex).markAsDone();
    }

    /**
     * Marks a task as not done based on the given index.
     *
     * @param taskIndex The index of the task to be marked as not done.
     */
    public void markTaskAsUndone(int taskIndex) {
        tasks.get(taskIndex).markAsUndone();
    }

    /**
     * Returns the list of tasks.
     *
     * @return A list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }
}
