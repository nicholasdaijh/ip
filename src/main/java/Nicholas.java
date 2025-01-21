import java.util.Scanner;

public class Nicholas {
    public static void main(String[] args) {
        String[] tasks = new String[100];
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
            if (userInput.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < taskCount; i++) {
                    int index = i + 1;
                    System.out.println(index + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
                userInput = scanner.nextLine();
                continue;
            }
            System.out.println("____________________________________________________________");
            System.out.println("added: " + userInput);
            System.out.println("____________________________________________________________\n");
            tasks[taskCount] = userInput;
            taskCount++;
            userInput = scanner.nextLine();
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
