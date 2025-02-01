package nicholas.ui;

import nicholas.tasks.Task;

public class Ui {
    private static final String LINE= "____________________________________________________________";
    public void showGreeting() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Nicholas!");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    public void showTaskMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.toString());
        System.out.println(LINE);
    }

    public void showTaskList(Task[] tasks, int taskCount) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            int index = i + 1;
            System.out.println(index + "." + tasks[i].toString());
        }
        System.out.println(LINE);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    public void showErrorMessage(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }
}
