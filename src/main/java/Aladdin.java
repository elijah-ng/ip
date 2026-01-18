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
     * Change Task Status and either Mark or Unmark the specified task as done or not done.
     *
     * @param userCommand Specifies if the task is to be mark or unmarked.
     * @param taskNumber Specified task to mark or unmark.
     */
    private void changeTaskStatus(String userCommand, int taskNumber) {
        System.out.println(LINE_SEP);

        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.taskCount)) {
            // Mark task as done
            if (userCommand.equals("mark")) {
                this.taskList[taskNumber - 1].setDone(true);
                System.out.println("Great Job! I have marked the task as done:\n" + this.taskList[taskNumber - 1]);

            } else if (userCommand.equals("unmark")) {
                // Unmark task as not done
                this.taskList[taskNumber - 1].setDone(false);
                System.out.println("Ok, I have marked the task as not done yet:\n" + this.taskList[taskNumber - 1]);
            }

        } else {
            System.out.println("Task " + taskNumber + " does not exist");
        }

        System.out.println(LINE_SEP);
    }

    /**
     * Print the taskList stored in the chatbot.
     */
    private void printTaskList() {
        System.out.println(LINE_SEP);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < this.taskCount; i++) {
            int taskNumber = i + 1;
            System.out.println(taskNumber + ". " + this.taskList[i]);
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
            } else if (userInput.startsWith("mark") || userInput.startsWith("unmark")) {
                String[] userCommand = userInput.split(" ");
                int taskNumber = Integer.parseInt(userCommand[1]);
                chatbot.changeTaskStatus(userCommand[0], taskNumber);
            } else {
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