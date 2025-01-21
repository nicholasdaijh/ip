import java.util.Scanner;

public class Nicholas {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Nicholas!");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________\n");
        //Scanner to receive user's input
        Scanner scanner = new Scanner(System.in);
        //store user's input
        String userInput = scanner.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            System.out.println("____________________________________________________________");
            System.out.println(userInput);
            System.out.println("____________________________________________________________\n");
            userInput = scanner.nextLine();
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
