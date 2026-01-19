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
     * @param taskString Task to be added to taskList.
     * @return true if the task is successfully added. Otherwise, false.
     * @throws AladdinException if task format is invalid.
     */
    private boolean addTask(String taskString) throws AladdinException {
        // If task list is not full
        if (this.taskCount < 100) {
            // Split into max 2 substrings
            String[] addTaskCommand = taskString.split(" ", 2);

            // If there is no task description, addTaskCommand has length == 1.
            // or if the description is blank (empty or contain only whitespaces)
            if ((addTaskCommand.length != 2) || (addTaskCommand[1].isBlank())) {
                throw new AladdinException("Invalid task! The description of a task cannot be empty/blank.");
            }

            if (addTaskCommand[0].equals("todo")) {
                // Add todo task to taskList
                this.taskList[this.taskCount] = new Todo(addTaskCommand[1]);

            } else if (addTaskCommand[0].equals("deadline")) {
                String[] deadlineString = addTaskCommand[1].split(" /by ", 2);
                if (deadlineString.length != 2) {
                    throw new AladdinException("Invalid deadline format. " +
                            "Please specify {description} /by {date/time}.");
                }
                // Add deadline task to taskList
                this.taskList[this.taskCount] = new Deadline(deadlineString[0], deadlineString[1]);

            } else if (addTaskCommand[0].equals("event")) {
                String eventFormatError = "Invalid event format. " +
                        "Please specify {description} /from {date/time} /to {date/time}.";

                String[] eventString1 = addTaskCommand[1].split(" /from ", 2);
                if (eventString1.length != 2) {
                    throw new AladdinException(eventFormatError);
                }

                String[] eventString2 = eventString1[1].split(" /to ", 2);
                if (eventString2.length != 2) {
                    throw new AladdinException(eventFormatError);
                }

                // Add Event task to taskList
                this.taskList[this.taskCount] = new Event(eventString1[0], eventString2[0], eventString2[1]);

            }

            // Print the new task added
            System.out.println(LINE_SEP);
            System.out.println("Got it. Task has been Added:\n" + this.taskList[this.taskCount]);
            this.taskCount++; // increment taskCount
            System.out.println("Now you have " + this.taskCount + " task(s) in the list.");
            System.out.println(LINE_SEP);
            // Task successfully added
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
        System.out.println(LINE_SEP); // Beginning line separator

        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.taskCount)) {
            // Mark task as done
            if (userCommand.equals("mark")) {
                this.taskList[taskNumber - 1].setDone(true);
                System.out.println("Great Job! I have marked the task as done:\n"
                        + this.taskList[taskNumber - 1]);

            } else if (userCommand.equals("unmark")) {
                // Unmark task as not done
                this.taskList[taskNumber - 1].setDone(false);
                System.out.println("Ok, I have marked the task as not done yet:\n"
                        + this.taskList[taskNumber - 1]);
            }

        } else {
            System.out.println("Task " + taskNumber + " does not exist");
        }

        System.out.println(LINE_SEP); // Ending line separator
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

        // Return if there is no user input.
        if (!sc.hasNextLine()) {
            return;
        }

        // Get user input until user types command "bye"
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            try {
                String[] userCommand = userInput.split(" ", 2);

                // Print taskList
                if (userCommand[0].equals("list")) {
                    chatbot.printTaskList();

                } else if (userCommand[0].equals("mark")
                        || userCommand[0].equals("unmark")) {
                    // TODO: catch not integer exception
                    int taskNumber = Integer.parseInt(userCommand[1]);
                    // Call method to change task status
                    chatbot.changeTaskStatus(userCommand[0], taskNumber);

                } else if (userCommand[0].equals("todo")
                        || userCommand[0].equals("deadline")
                        || userCommand[0].equals("event")) {
                    // Add task to taskList
                    chatbot.addTask(userInput);

                } else {
                    throw new AladdinException("Invalid command. Please try again.");
                }

            } catch (AladdinException e) {
                System.out.println(LINE_SEP);
                System.out.println("AladdinException: " + e.getMessage());
                System.out.println(LINE_SEP);

            } catch (NumberFormatException e) {
                System.out.println(LINE_SEP);
                System.out.println("NumberFormatException: Please enter a valid number. " + e.getMessage());
                System.out.println(LINE_SEP);

            } finally {
                // Return if there is no user input.
                if (!sc.hasNextLine()) {
                    return;
                }
                // Get next user input
                userInput = sc.nextLine();
            }
        }

        // Print exit message
        System.out.println(LINE_SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE_SEP);

        // Close scanner
        sc.close();
    }

}