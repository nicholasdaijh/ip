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
     * Upgrades the priority of a task at the specified index.
     * <p>
     * If the task's priority is {@code LOW}, it is upgraded to {@code MEDIUM}.
     * If the task's priority is {@code MEDIUM}, it is upgraded to {@code HIGH}.
     * If the task is already at {@code HIGH} priority, no changes are made.
     * </p>
     *
     * @param taskIndex The index of the task to be upgraded.
     * @throws IndexOutOfBoundsException if the task index is out of range.
     */
    public void upgradeTask(int taskIndex) {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + taskIndex);
        }

        Task taskToUpgrade = tasks.get(taskIndex);

        if (taskToUpgrade.getPriority() == Priority.LOW) {
            taskToUpgrade.setPriority(Priority.MEDIUM);
        } else if (taskToUpgrade.getPriority() == Priority.MEDIUM) {
            taskToUpgrade.setPriority(Priority.HIGH);
        }
    }

    /**
     * Downgrades the priority of a task at the specified index.
     * <p>
     * If the task's priority is {@code HIGH}, it is downgraded to {@code MEDIUM}.
     * If the task's priority is {@code MEDIUM}, it is downgraded to {@code LOW}.
     * If the task is already at {@code LOW} priority, no changes are made.
     * </p>
     *
     * @param taskIndex The index of the task to be downgraded.
     * @throws IndexOutOfBoundsException if the task index is out of range.
     */
    public void downgradeTask(int taskIndex) {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + taskIndex);
        }

        Task taskToDowngrade = tasks.get(taskIndex);

        if (taskToDowngrade.getPriority() == Priority.HIGH) {
            taskToDowngrade.setPriority(Priority.MEDIUM);
        } else if (taskToDowngrade.getPriority() == Priority.MEDIUM) {
            taskToDowngrade.setPriority(Priority.LOW);
        }
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
