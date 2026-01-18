import java.util.Scanner;

/**
 * Aladdin Chatbot Class
 *
 * @author Elijah Ng Ding Jie
 */
public class Aladdin {
    /** Line Separator used by Aladdin chatbot */
    private static final String LINE_SEP = "_".repeat(60);
    /** Name of chatbot */
    private String name;
    /** Task List of chatbot */
    private Task[] taskList;
    /** Counts the number of tasks in taskList */
    private int taskCount;

    /**
     * Constructor for Aladdin chatbot.
     *
     * @param name name of the chatbot
     */
    public Aladdin(String name) {
        this.name = name;
        this.taskList = new Task[100];
        this.taskCount = 0;
    }

    /**
     * Getter for name
     * @return name of chatbot
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds the user's task to list.
     *
     * @param taskName Task to be added to taskList.
     * @return true if the task is successfully added. Otherwise, false.
     */
    private boolean addTask(String taskName) {
        // If task list is not full
        if (this.taskCount < 100) {
            // Add task to taskList
            this.taskList[this.taskCount] = new Task(taskName);
            this.taskCount++;

            // Print task added
            System.out.println(LINE_SEP + "\n" + "Added: " + taskName + "\n" + LINE_SEP);
            return true;

        } else {
            System.out.println("Task List is full! Maximum of 100 tasks.");
            return false;
        }
    }

    /**
     * Print the taskList stored in the chatbot.
     */
    private void printTaskList() {
        System.out.println(LINE_SEP);
        for (int i = 0; i < this.taskCount; i++) {
            int taskIndex = i + 1;
            System.out.println(taskIndex + ". " + this.taskList[i]);
        }
        System.out.println(LINE_SEP);
    }

    /**
     * Main method to start Aladdin chatbot greeting.
     *
     * @param args supplied command-line arguments (if any)
     */
    public static void main(String[] args) {
        // Create a scanner to read from standard input
        Scanner sc = new Scanner(System.in);
        // Instantiate Aladdin chatbot
        Aladdin chatbot = new Aladdin("Aladdin");

        // Print greeting message
        System.out.println(LINE_SEP);
        System.out.println("Hello! I am " + chatbot.getName() + "!");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEP);

        // Get user input until user types command "bye"
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            // Print taskList
            if (userInput.equals("list")) {
                chatbot.printTaskList();
            }
            else {
                // Add task to list
                chatbot.addTask(userInput);
            }
            // Get next user input
            userInput = sc.nextLine();
        }

        // Print exit message
        System.out.println(LINE_SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE_SEP);

        // Close scanner
        sc.close();
    }

}