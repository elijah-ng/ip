import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Aladdin Chatbot Class
 *
 * @author Elijah Ng Ding Jie
 */
public class Aladdin {
    /** 24-Hour DateTime Format for Tasks Stored by Aladdin */
    public static final DateTimeFormatter DATE_TIME_STORE =
            DateTimeFormatter.ofPattern("d-M-yyyy HHmm");

    /** 12-Hour DateTime Format for Display by Aladdin */
    public static final DateTimeFormatter DATE_TIME_DISPLAY =
            DateTimeFormatter.ofPattern("d MMM yyyy h:mm a");


    /** Line Separator used by Aladdin chatbot */
    private static final String LINE_SEP = "_".repeat(60);
    /** File Path to Store Tasks */
    private static final String TASK_FILE_PATH = "data/aladdin.txt";

    /** Name of chatbot */
    private String name;
    /** Task List of chatbot */
    private TaskList taskList;

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
        this.taskList = new TaskList();
    }

    private void loadTasksFromFile() {
        System.out.println(LINE_SEP);

        File f = new File(TASK_FILE_PATH);
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
                this.taskList.addTask(newTask);
            }

        } catch (FileNotFoundException e) {
            // If file does not exist, no task to load
            System.out.println("Note: There was no saved tasks file found from a previous session.");
            System.out.println("You may safely ignore this if this is your first time using Aladdin.");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("There was an error loading tasks from the file.");
            System.out.println("Please check data formatting in " + TASK_FILE_PATH);

        } catch (DateTimeParseException e) {
            System.out.println("DateTimeParseException: " + e.getMessage());
        }

        System.out.println(LINE_SEP);
    }

    // Helper Method to Parse and Deserialize Tasks
    private static Task parseTask(String nextLineString) {
        String[] nextLineStringArray = nextLineString.split("\\|");

        Task newTask = null;

        if (nextLineStringArray[0].equals("D")) {
            // Create Deadline task
            newTask = new Deadline(nextLineStringArray[2],
                    LocalDateTime.parse(nextLineStringArray[3], DATE_TIME_STORE));

        } else if (nextLineStringArray[0].equals("E")) {
            // Create Event task
            newTask = new Event(nextLineStringArray[2],
                    LocalDateTime.parse(nextLineStringArray[3], DATE_TIME_STORE),
                    LocalDateTime.parse(nextLineStringArray[4], DATE_TIME_STORE));

        } else { // nextLineStringArray[0].equals("T")
            // Create Todo task
            newTask = new Todo(nextLineStringArray[2]);
        }

        // If task is marked done
        if (nextLineStringArray[1].equals("1")) {
            newTask.setDone(true);
        } else {
            newTask.setDone(false);
        }

        return newTask;
    }

    // Saves tasks to file whenever task list changes.
    // If no tasks in taskList, create an empty file.
    private void saveTasksToFile() {
        try {
            // Creates directory if it does not exist
            Files.createDirectories(Paths.get("data"));
            // Creates file if it does not exist, otherwise overwrite (delete/add tasks)
            FileWriter fw = new FileWriter(TASK_FILE_PATH);

            for (int i = 0; i < this.taskList.getSize(); i++) {
                Task currentTask = this.taskList.getTask(i);
                fw.write(currentTask.serialise() + System.lineSeparator());
            }

            // Close FileWrite object to complete writing operation
            fw.close();

        } catch (IOException e) {
            System.out.println("Error creating/opening " + TASK_FILE_PATH
                    + " file to save tasks: " + e.getMessage());
        }
    }

    /**
     * Getter for name
     *
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

        Task newTask = null;

        if (addTaskCommand[0].equalsIgnoreCase("todo")) {
            // Add todo task to taskList
            newTask = new Todo(addTaskCommand[1]);

        } else if (addTaskCommand[0].equalsIgnoreCase("deadline")) {
            String[] deadlineString = addTaskCommand[1].split(" /by ", 2);
            if (deadlineString.length != 2) {
                throw new AladdinException("Invalid deadline format. "
                        + "Please specify {description} /by {date/time}.");
            }
            // Add deadline task to taskList
            newTask = new Deadline(deadlineString[0],
                    LocalDateTime.parse(deadlineString[1], DATE_TIME_STORE));

        } else if (addTaskCommand[0].equalsIgnoreCase("event")) {
            String eventFormatError = "Invalid event format. "
                    + "Please specify {description} /from {date/time} /to {date/time}.";

            // Split string by "/from"
            String[] eventString1 = addTaskCommand[1].split(" /from ", 2);
            if (eventString1.length != 2) {
                throw new AladdinException(eventFormatError);
            }

            // Split string by "/to"
            String[] eventString2 = eventString1[1].split(" /to ", 2);
            if (eventString2.length != 2) {
                throw new AladdinException(eventFormatError);
            }

            // Add Event task to taskList
            newTask = new Event(eventString1[0],
                    LocalDateTime.parse(eventString2[0], DATE_TIME_STORE),
                    LocalDateTime.parse(eventString2[1], DATE_TIME_STORE));
        }

        // Add the new task
        this.taskList.addTask(newTask);
        System.out.println(LINE_SEP);
        System.out.println("Got it. Task has been Added:");
        System.out.println(newTask);
        System.out.println("Now you have " + this.taskList.getSize() + " task(s) in the list.");
        System.out.println(LINE_SEP);
    }

    /**
     * Change Task Status and either Mark or Unmark the specified task as done or not done.
     *
     * @param taskNumber Specified task to mark or unmark.
     * @param isDone Specifies if task is done or not.
     */
    private void markTaskStatus(int taskNumber, boolean isDone) {
        System.out.println(LINE_SEP); // Beginning line separator

        Task modifiedTask = this.taskList.changeTaskStatus(taskNumber, isDone);

        if (modifiedTask != null) {
            if (isDone) {
                System.out.println("Great Job! I have marked the task as done:");
            } else {
                System.out.println("Ok, I have marked the task as not done yet:");
            }
            System.out.println(modifiedTask);

        } else {
            System.out.println("Task " + taskNumber + " does not exist");
        }

        System.out.println(LINE_SEP); // Ending line separator
    }

    /**
     * Delete a Task from list based on the task number.
     *
     * @param taskNumber Specified task to delete (starts from 1).
     */
    private void deleteTask(int taskNumber) {
        System.out.println(LINE_SEP); // Ending line separator
        Task deletedTask = this.taskList.deleteTask(taskNumber);

        if (deletedTask != null) {
            System.out.println("Noted. I have removed this task:");
            System.out.println(deletedTask);
            System.out.println("Now you have " + this.taskList.getSize() + " task(s) in the list.");

        } else {
            System.out.println("Task " + taskNumber + " does not exist");
        }

        System.out.println(LINE_SEP); // Beginning line separator
    }

    /**
     * Prints the chatbot's taskList.
     */
    private void printTaskList() {
        System.out.println(LINE_SEP);
        System.out.println("Here are the tasks in your list:");
        this.taskList.printTasks();
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
                    taskNumber = Integer.parseInt(userInputArray[1]);
                    // Call method to mark task
                    chatbot.markTaskStatus(taskNumber, true);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;

                case UNMARK:
                    taskNumber = Integer.parseInt(userInputArray[1]);
                    // Call method to unmark task
                    chatbot.markTaskStatus(taskNumber, false);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;

                case TODO:
                case DEADLINE:
                case EVENT:
                    // Add task to taskList
                    chatbot.addTask(userInput);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;

                case DELETE:
                    taskNumber = Integer.parseInt(userInputArray[1]);
                    // Call method to delete task
                    chatbot.deleteTask(taskNumber);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
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

            } catch (DateTimeParseException e) {
                System.out.println(LINE_SEP);
                System.out.println("DateTimeParseException: Please enter in d-M-yyyy HHmm format.");
                System.out.println(e.getMessage());
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