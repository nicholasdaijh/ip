import java.util.Scanner;
import java.util.Arrays;

public class Nicholas {
    public static void main(String[] args) {
        //Store tasks
        Task[] tasks = new Task[100];
        int taskCount = 0;
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Nicholas!");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");
        //Scanner to receive user's input
        Scanner scanner = new Scanner(System.in);
        //store user's input
        String userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            //if asked to mark or unmark tasks
            if (userInput.length() >= 4 && userInput.substring(0, 4).equalsIgnoreCase("mark")) {
                Mark mark = new Mark(true, userInput);
                int taskIndex = mark.getTaskIndex() + 1;
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[X] " + tasks[taskIndex].getDescription());
                System.out.println("____________________________________________________________");
                tasks[taskIndex].markAsDone();
                userInput = scanner.nextLine();
                continue;
            }
            if (userInput.length() >= 6 && userInput.substring(0, 6).equalsIgnoreCase("unmark")) {
                Mark mark = new Mark(false, userInput);
                int taskIndex = mark.getTaskIndex() + 1;
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("[ ] " + tasks[taskIndex].getDescription());
                System.out.println("____________________________________________________________");
                tasks[taskIndex].markAsUndone();
                userInput = scanner.nextLine();
                continue;
            }
            //if asked to list out the tasks
            if (userInput.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    int index = i + 1;
                    System.out.println(index + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].getDescription());
                }
                System.out.println("____________________________________________________________");
                userInput = scanner.nextLine();
                continue;
            }
            //Add normal task
            System.out.println("____________________________________________________________");
            System.out.println("added: " + userInput);
            System.out.println("____________________________________________________________\n");
            tasks[taskCount] = new Task(userInput);
            taskCount++;
            userInput = scanner.nextLine();
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
