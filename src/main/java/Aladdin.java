import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private ArrayList<Task> taskList;

    /**
     * Enumeration for Commands
     */
    private enum Command { LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE }

    /**
     * Constructor for Aladdin chatbot.
     *
     * @param name name of the chatbot
     */
    public Aladdin(String name) {
        this.name = name;
        this.taskList = new ArrayList<Task>();
    }

    private void loadTasksFromFile() {
        System.out.println(LINE_SEP);

        File f = new File("data/aladdin.txt");
        try {
            Scanner s = new Scanner(f); // FileNotFoundException if directory or file does not exist
            System.out.println("File containing saved tasks found!");
            System.out.println("Loading tasks from: " + f.getAbsolutePath());

            // while file contains non-whitespace character
            while (s.hasNext()) {
                // Store next line
                String nextLineString = s.nextLine();
                Task newTask = parseTask(nextLineString);

                // Add to taskList
                this.taskList.add(newTask);
            }

        } catch (FileNotFoundException e) {
            // If file does not exist, no task to load
            System.out.println("Note: There was no saved tasks file found from a previous session.");
            System.out.println("You may safely ignore this if this is your first time using Aladdin.");
        }
        System.out.println(LINE_SEP);
    }

    // Helper Method to Parse and Deserialize Tasks
    private static Task parseTask(String nextLineString) {
        String[] nextLineStringArray = nextLineString.split("\\|");

        Task newTask = null;

        if (nextLineStringArray[0].equals("D")) {
            // Create Deadline task
            newTask = new Deadline(nextLineStringArray[2], nextLineStringArray[3]);

        } else if (nextLineStringArray[0].equals("E")) {
            // Create Event task
            newTask = new Event(nextLineStringArray[2],
                    nextLineStringArray[3], nextLineStringArray[4]);

        } else { // nextLineStringArray[0].equals("T")
            // Create Todo task
            newTask = new Todo(nextLineStringArray[2]);
        }

        // If task is marked done
        if (nextLineStringArray[1].equals("1")) {
            newTask.setDone(true);
        }

        return newTask;
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
     * @throws AladdinException if task format is invalid.
     */
    private void addTask(String taskString) throws AladdinException {
        // Split into max 2 substrings
        String[] addTaskCommand = taskString.split(" ", 2);

        // If there is no task description, addTaskCommand has length == 1.
        // or if the description is blank (empty or contain only whitespaces)
        if ((addTaskCommand.length != 2) || (addTaskCommand[1].isBlank())) {
            throw new AladdinException("Invalid task! The description of a task cannot be empty/blank.");
        }

        if (addTaskCommand[0].equalsIgnoreCase("todo")) {
            // Add todo task to taskList
            this.taskList.add(new Todo(addTaskCommand[1]));

        } else if (addTaskCommand[0].equalsIgnoreCase("deadline")) {
            String[] deadlineString = addTaskCommand[1].split(" /by ", 2);
            if (deadlineString.length != 2) {
                throw new AladdinException("Invalid deadline format. " +
                        "Please specify {description} /by {date/time}.");
            }
            // Add deadline task to taskList
            this.taskList.add(new Deadline(deadlineString[0], deadlineString[1]));

        } else if (addTaskCommand[0].equalsIgnoreCase("event")) {
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
            this.taskList.add(new Event(eventString1[0], eventString2[0], eventString2[1]));

        }

        // Print the new task added
        System.out.println(LINE_SEP);
        System.out.println("Got it. Task has been Added:\n"
                + this.taskList.get(this.taskList.size() - 1));

        System.out.println("Now you have " + this.taskList.size() + " task(s) in the list.");
        System.out.println(LINE_SEP);
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
        if ((0 < taskNumber) && (taskNumber <= this.taskList.size())) {
            // Mark task as done
            if (userCommand.equalsIgnoreCase("mark")) {
                this.taskList.get(taskNumber - 1).setDone(true);
                System.out.println("Great Job! I have marked the task as done:\n"
                        + this.taskList.get(taskNumber - 1));

            } else if (userCommand.equalsIgnoreCase("unmark")) {
                // Unmark task as not done
                this.taskList.get(taskNumber - 1).setDone(false);
                System.out.println("Ok, I have marked the task as not done yet:\n"
                        + this.taskList.get(taskNumber - 1));
            }

        } else {
            System.out.println("Task " + taskNumber + " does not exist");
        }

        System.out.println(LINE_SEP); // Ending line separator
    }

    /**
     * Delete a Task from list.
     *
     * @param taskNumber Specified task to delete.
     */
    private void deleteTask(int taskNumber) {
        System.out.println(LINE_SEP); // Beginning line separator

        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.taskList.size())) {
            Task deletedTask = this.taskList.remove(taskNumber - 1);
            System.out.println("Noted. I have removed this task:\n" + deletedTask);
            System.out.println("Now you have " + this.taskList.size() + " task(s) in the list.");

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
        for (int i = 0; i < this.taskList.size(); i++) {
            int taskNumber = i + 1;
            System.out.println(taskNumber + ". " + this.taskList.get(i));
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
        chatbot.loadTasksFromFile();

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

        while (!userInput.equalsIgnoreCase("bye")) {
            try {
                String[] userInputArray = userInput.split(" ", 2);
                Command userCommand = Command.valueOf(userInputArray[0].toUpperCase());
                // Initialise taskNumber for later use
                int taskNumber = 0;

                switch (userCommand) {
                case LIST:
                    // Print taskList
                    chatbot.printTaskList();
                    break;

                case MARK:
                case UNMARK:
                    taskNumber = Integer.parseInt(userInputArray[1]);
                    // Call method to change task status
                    chatbot.changeTaskStatus(userInputArray[0], taskNumber);
                    break;

                case TODO:
                case DEADLINE:
                case EVENT:
                    // Add task to taskList
                    chatbot.addTask(userInput);
                    break;

                case DELETE:
                    taskNumber = Integer.parseInt(userInputArray[1]);
                    // Call method to change task status
                    chatbot.deleteTask(taskNumber);
                    break;
                }

            } catch (AladdinException e) {
                System.out.println(LINE_SEP);
                System.out.println("AladdinException: " + e.getMessage());
                System.out.println(LINE_SEP);

            } catch (NumberFormatException e) {
                System.out.println(LINE_SEP);
                System.out.println("NumberFormatException: Please enter a valid number. " + e.getMessage());
                System.out.println(LINE_SEP);

            } catch (IllegalArgumentException e) {
                System.out.println(LINE_SEP);
                System.out.println("Invalid command. Please try again.");
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