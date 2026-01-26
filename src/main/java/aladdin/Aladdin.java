package aladdin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Aladdin chatbot.
 *
 */
public class Aladdin {
    /** 24-Hour DateTime Format for Tasks Stored by Aladdin */
    public static final DateTimeFormatter DATE_TIME_STORE =
            DateTimeFormatter.ofPattern("d-M-yyyy HHmm");

    /** 12-Hour DateTime Format for Display by Aladdin */
    public static final DateTimeFormatter DATE_TIME_DISPLAY =
            DateTimeFormatter.ofPattern("d MMM yyyy h:mm a");

    /** File Path to Store Tasks */
    private static final String TASK_FILE_PATH = "data/aladdin.txt";

    /** Name of chatbot */
    private String name;
    /** Task List of chatbot */
    private TaskList taskList;
    /** Storage File */
    private Storage storage;

    /**
     * Creates an Aladdin chatbot instance.
     *
     * @param name Name of the chatbot.
     */
    public Aladdin(String name) {
        this.name = name;
        this.taskList = new TaskList();
        this.storage = new Storage(TASK_FILE_PATH);
    }

    private void loadTasksFromFile() {
        try {
            storage.load(this.taskList);

        } catch (AladdinException e) {
            Ui.printException(e);
        }
    }

    /**
     * Saves tasks to file whenever task list changes.
     * If no tasks in taskList, create an empty file.
     */
    private void saveTasksToFile() {
        try {
            storage.save(this.taskList);

        } catch (AladdinException e) {
            Ui.printException(e);
        }
    }

    /**
     * Getter for name.
     *
     * @return Name of chatbot.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds the user's task to list.
     *
     * @param formattedTask Array of substrings of correctly formatted task properties.
     * @throws AladdinException if task type is invalid.
     */
    private void addTask(String[] formattedTask) throws AladdinException {
        String taskType = formattedTask[0];
        Task newTask = null;

        if (taskType.equals("TODO")) {
            // Add todo task to taskList
            newTask = new Todo(formattedTask[1]);

        } else if (taskType.equals("DEADLINE")) {
            // Add deadline task to taskList
            newTask = new Deadline(formattedTask[1],
                    LocalDateTime.parse(formattedTask[2], DATE_TIME_STORE));

        } else if (taskType.equals("EVENT")) {
            // Add Event task to taskList
            newTask = new Event(formattedTask[1],
                    LocalDateTime.parse(formattedTask[2], DATE_TIME_STORE),
                    LocalDateTime.parse(formattedTask[3], DATE_TIME_STORE));

        } else {
            throw new AladdinException("Invalid task type: " + taskType);
        }

        // Add the new task
        this.taskList.addToTaskList(newTask);

        Ui.printMsgWithObject("Got it. Task has been Added:", newTask,
                "Now you have " + this.taskList.getSize() + " task(s) in the list.");
    }

    /**
     * Changes task status.
     * Either mark or unmark the specified task as done or not done.
     *
     * @param taskNumber Specified task to mark or unmark.
     * @param isDone Specifies if task is done or not.
     */
    private void markTaskStatus(int taskNumber, boolean isDone) {
        Task modifiedTask = this.taskList.changeTaskStatus(taskNumber, isDone);

        if (modifiedTask != null) {
            String msg;
            if (isDone) {
                msg = "Great Job! I have marked the task as done:";
            } else {
                msg = "Ok, I have marked the task as not done yet:";
            }
            Ui.printMsgWithObject(msg, modifiedTask);

        } else {
            Ui.printMsg("Task " + taskNumber + " does not exist");
        }
    }

    /**
     * Deletes a task from list based on the task number.
     *
     * @param taskNumber Specified task to delete (starts from 1).
     */
    private void deleteTask(int taskNumber) {
        Task deletedTask = this.taskList.deleteTask(taskNumber);

        if (deletedTask != null) {
            Ui.printMsgWithObject("Noted. I have removed this task:", deletedTask,
                    "Now you have " + this.taskList.getSize() + " task(s) in the list.");

        } else {
            Ui.printMsg("Task " + taskNumber + " does not exist");
        }
    }

    /**
     * Prints the chatbot's taskList.
     */
    private void printTaskList() {
        Ui.printMsgWithObject("Here are the tasks in your list:", taskList);
    }

    /**
     * Main method to initialise and run Aladdin chatbot.
     *
     * @param args Supplied command-line arguments (if any).
     */
    public static void main(String[] args) {

        // Instantiate Aladdin chatbot
        String name = "Aladdin";
        Aladdin chatbot = new Aladdin(name);
        chatbot.loadTasksFromFile();

        // Print welcome message
        Ui.printWelcome(name);

        while (true) {
            // Get user input
            String userInput = Ui.getUserInput();

            if (userInput == null) {
                // Return if there is no user input
                // Required for automated text UI test
                return;

            } else if (userInput.equalsIgnoreCase("bye")) {
                // Break when user enters command "bye"
                break;
            }

            try {
                String[] formattedCommand = Parser.parseUserCommand(userInput);

                switch (formattedCommand[0]) {
                case "LIST":
                    // Print taskList
                    chatbot.printTaskList();
                    break;

                case "MARK":
                    // Call method to mark task
                    chatbot.markTaskStatus(Integer.parseInt(formattedCommand[1]), true);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;

                case "UNMARK":
                    // Call method to unmark task
                    chatbot.markTaskStatus(Integer.parseInt(formattedCommand[1]), false);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;

                case "TODO":
                case "DEADLINE":
                case "EVENT":
                    // Add task to taskList
                    chatbot.addTask(formattedCommand);

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;

                case "DELETE":
                    // Call method to delete task
                    chatbot.deleteTask(Integer.parseInt(formattedCommand[1]));

                    // Save updated taskList to file
                    chatbot.saveTasksToFile();
                    break;
                }

            } catch (AladdinException e) {
                Ui.printException(e);
            }
        }

        // Print Exit message
        Ui.printExit();
    }

}
