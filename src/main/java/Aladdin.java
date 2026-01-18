import java.util.Scanner;

/**
 * Aladdin Chatbot Class
 *
 * @author Elijah Ng Ding Jie
 */
public class Aladdin {
    /** Line Separator used by Aladdin chatbot */
    private static final String LINE_SEP = "_".repeat(60);

    /**
     * Main method to start Aladdin chatbot greeting.
     *
     * @param args supplied command-line arguments (if any)
     */
    public static void main(String[] args) {
        String chatbotName = "Aladdin";

        // Print greeting message
        System.out.println(LINE_SEP);
        System.out.println("Hello! I am " + chatbotName + "!");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEP);

        // Call echo method
        Aladdin.echo();

        // Print exit message
        System.out.println(LINE_SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE_SEP);
    }

    /**
     * Echos the user's input back to the user.
     * Returns when the user inputs the string "bye".
     */
    private static void echo() {
        // Create a scanner to read from standard input
        Scanner sc = new Scanner(System.in);

        // Get user input and echo until user types command "bye"
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            System.out.println(LINE_SEP);
            System.out.println(userInput);
            System.out.println(LINE_SEP);
            userInput = sc.nextLine();
        }
        // When userInput is bye, close scanner and return
        sc.close();
        return;
    }
}
