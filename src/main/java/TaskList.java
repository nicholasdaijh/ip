import java.util.*;

public class TaskList {
    private List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.remove(taskIndex);
        }
    }

    public void markTaskAsDone(int taskIndex) {
        tasks.get(taskIndex).markAsDone();
    }

    public void markTaskAsUndone(int taskIndex) {
        tasks.get(taskIndex).markAsUndone();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}
