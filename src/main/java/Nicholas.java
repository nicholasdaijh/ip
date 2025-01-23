import java.util.Scanner;

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
            try {
            //if asked to mark or unmark tasks
            if (userInput.length() >= 4 && userInput.substring(0, 4).equalsIgnoreCase("mark")) {
                Mark mark = new Mark(true, userInput);
                int taskIndex = mark.getTaskIndex() - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[taskIndex].toString());
                System.out.println("____________________________________________________________");
                userInput = scanner.nextLine();
                continue;
            }
            if (userInput.length() >= 6 && userInput.substring(0, 6).equalsIgnoreCase("unmark")) {
                Mark mark = new Mark(false, userInput);
                int taskIndex = mark.getTaskIndex() - 1;
                tasks[taskIndex].markAsUndone();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[taskIndex].toString());
                System.out.println("____________________________________________________________");
                userInput = scanner.nextLine();
                continue;
            }
            //if asked to list out the tasks
            if (userInput.length() >= 4 && userInput.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    int index = i + 1;
                    System.out.println(index + "." + tasks[i].toString());
                }
                System.out.println("____________________________________________________________");
                userInput = scanner.nextLine();
                continue;
            }
            //if asked to delete
            if (userInput.length() >= 6 && userInput.substring(0, 6).equalsIgnoreCase("delete")) {
                System.out.println("____________________________________________________________");
                System.out.println("Noted. I've removed this task:");
                System.out.println(tasks[Integer.parseInt(userInput.substring(7)) - 1].toString());
                for (int i = Integer.parseInt(userInput.substring(7)); i < taskCount; i++) {
                    tasks[i - 1] = tasks[i];
                }
                tasks[taskCount - 1] = null;
                taskCount--;
                System.out.println("Now you have " + taskCount + " tasks in the list");
                System.out.println("____________________________________________________________");
                userInput = scanner.nextLine();
                continue;
            }
            //Add normal task
            //Add Todo
            if (userInput.length() >= 4 && userInput.substring(0, 4).equalsIgnoreCase("todo")) {
                Task currTask;
                // check for empty description
                if (userInput.length() == 4) {
                    currTask = new Todo("");
                } else {
                    currTask = new Todo(userInput.substring(5));
                }
                try {
                    EmptyDescriptionValidator.validateEmptyDescription(currTask.getDescription(), "todo");
                    tasks[taskCount] = currTask;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(currTask.toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list");
                    System.out.println("____________________________________________________________\n");
                    userInput = scanner.nextLine();
                } catch (EmptyDescriptionException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(e.getMessage());
                    System.out.println("____________________________________________________________\n");
                    userInput = scanner.nextLine();
                }
                //Add Dateline
            } else if (userInput.length() >= 8 && userInput.substring(0, 8).equalsIgnoreCase("deadline")) {
                Task currTask;
                //check for empty description
                if (userInput.length() == 8) {
                    currTask = new Event("", "", "");
                } else if (userInput.length() > 8 && userInput.substring(9).trim().isEmpty()) {
                    currTask = new Event("", "", "");
                } else {
                    int byPosition = userInput.lastIndexOf("/by");
                    currTask = new Deadline(userInput.substring(9, byPosition - 1), userInput.substring(byPosition + 4));
                }
                try {
                    EmptyDescriptionValidator.validateEmptyDescription(currTask.getDescription(), "deadline");
                    tasks[taskCount] = currTask;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(currTask.toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list");
                    System.out.println("____________________________________________________________\n");
                    userInput = scanner.nextLine();
                } catch (EmptyDescriptionException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(e.getMessage());
                    System.out.println("____________________________________________________________\n");
                    userInput = scanner.nextLine();
                }
                //Add Event
            } else if (userInput.length() >= 5 && userInput.substring(0, 5).equalsIgnoreCase("event")) {
                Task currTask;
                //check for empty description
                if (userInput.length() == 5) {
                    currTask = new Event("", "", "");
                } else if (userInput.length() > 5 && userInput.substring(6).trim().isEmpty()) {
                    currTask = new Event("", "", "");
                } else {
                    int fromPosition = userInput.lastIndexOf("/from");
                    int toPosition = userInput.lastIndexOf("/to");
                    currTask = new Event(userInput.substring(6, fromPosition - 1), userInput.substring(fromPosition + 6, toPosition - 1), userInput.substring(toPosition + 4));
                }
                try {
                    EmptyDescriptionValidator.validateEmptyDescription(currTask.getDescription(), "event");
                    tasks[taskCount] = currTask;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println("Got it. I've added this task:");
                    System.out.println(currTask.toString());
                    System.out.println("Now you have " + taskCount + " tasks in the list");
                    System.out.println("____________________________________________________________\n");
                    userInput = scanner.nextLine();
                } catch (EmptyDescriptionException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(e.getMessage());
                    System.out.println("____________________________________________________________\n");
                    userInput = scanner.nextLine();
                }
            } else {
                //if input is not a task
                throw new NotTaskException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            } catch (NotTaskException e) {
                // Handle invalid command
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________\n");
                userInput = scanner.nextLine();
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
