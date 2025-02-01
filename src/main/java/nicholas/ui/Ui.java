package nicholas.ui;

import nicholas.tasks.Task;

/**
 * Handles user interface interactions, including displaying messages and formatting output.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    /**
     * Displays the greeting message when the application starts.
     */
    public void showGreeting() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Nicholas!");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Displays the farewell message when the application exits.
     */
    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after adding the new task.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    /**
     * Displays a message confirming that a task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    /**
     * Displays the list of tasks currently stored.
     *
     * @param tasks An array of tasks to display.
     * @param taskCount The total number of tasks in the list.
     */
    public void showTaskList(Task[] tasks, int taskCount) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            int index = i + 1;
            System.out.println(index + "." + tasks[i].toString());
        }
        System.out.println(LINE);
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    /**
     * Displays a list of tasks that contain a given prefix in their description.
     *
     * @param tasks      An array of Task objects to search through.
     * @param taskPrefix The prefix to search for in the task descriptions.
     * @param taskCount  The total number of tasks in the list.
     */
    public void showTaskFind(Task[] tasks, String taskPrefix, int taskCount) {
        System.out.println(LINE);
        System.out.println("Here are the matching tasks in your list:");
        int matchTaskCount = 1;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].toString().contains(taskPrefix)) {
                System.out.println(matchTaskCount + "." + tasks[i].toString());
                matchTaskCount++;
            }
        }
        System.out.println(LINE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void showErrorMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }
}
