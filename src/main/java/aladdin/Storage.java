package aladdin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Represents a file that stores tasks.
 */
public class Storage {

    private String filePath;

    /**
     * Creates a Storage object.
     *
     * @param filePath The path of the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the tasks from storage file into TaskList.
     *
     * @param taskList The lists of tasks to populate.
     */
    public void load(TaskList taskList) throws AladdinException {
        File f = new File(this.filePath);
        try {
            Scanner s = new Scanner(f); // FileNotFoundException if directory or file does not exist
            Ui.printMsg("File containing saved tasks found!" + System.lineSeparator()
                    + "Loading tasks from: " + f.getAbsolutePath());

            // while file contains non-whitespace character
            while (s.hasNext()) {
                // Parse next line
                String nextLineString = s.nextLine();
                Task newTask = Storage.deserialiseTask(nextLineString);

                // Add to taskList
                taskList.addToTaskList(newTask);
            }

        } catch (FileNotFoundException e) {
            // If file does not exist, no task to load
            throw new AladdinException("Note: There was no saved tasks file found from a previous session."
                    + System.lineSeparator() + "You may safely ignore this if this is your first time using Aladdin.");

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AladdinException("There was an error loading tasks from the file. "
                    + "Please check data formatting in " + this.filePath);

        } catch (DateTimeParseException e) {
            throw new AladdinException("Corrupted LocalDateTime format in : " + this.filePath);
        }
    }

    // Helper Method to Deserialize Tasks
    // throws ArrayIndexOutOfBoundsException if storage file corrupted.
    protected static Task deserialiseTask(String nextLineString) throws ArrayIndexOutOfBoundsException {
        String[] nextLineStringArray = nextLineString.split("\\|");

        Task newTask = null;

        if (nextLineStringArray[0].equals("D")) {
            // Create Deadline task
            newTask = new Deadline(nextLineStringArray[2],
                    LocalDateTime.parse(nextLineStringArray[3], Aladdin.DATE_TIME_STORE));

        } else if (nextLineStringArray[0].equals("E")) {
            // Create Event task
            newTask = new Event(nextLineStringArray[2],
                    LocalDateTime.parse(nextLineStringArray[3], Aladdin.DATE_TIME_STORE),
                    LocalDateTime.parse(nextLineStringArray[4], Aladdin.DATE_TIME_STORE));

        } else { // nextLineStringArray[0].equals("T")
            // Create Todo task
            newTask = new Todo(nextLineStringArray[2]);
        }

        // If task is marked done
        if (nextLineStringArray[1].equals("1")) {
            newTask.setDone(true);

        } else { // task is not done
            newTask.setDone(false);
        }

        return newTask;
    }

    /**
     * Saves tasks in TaskList to storage file for persistence.
     *
     * @param taskList The list of tasks to save into a file.
     */
    public void save(TaskList taskList) throws AladdinException {
        try {
            Path parentDirectory = Paths.get(this.filePath).getParent();
            if (parentDirectory != null) {
                // Creates parent directory if it does not exist
                Files.createDirectories(parentDirectory);
            }

            // Creates file if it does not exist, otherwise overwrite (delete/add tasks)
            FileWriter fw = new FileWriter(this.filePath);

            for (int i = 0; i < taskList.getSize(); i++) {
                Task currentTask = taskList.getTask(i);
                fw.write(currentTask.serialise() + System.lineSeparator());
            }

            // Close FileWrite object to complete writing operation
            fw.close();

        } catch (IOException e) {
            throw new AladdinException("Error creating/opening " + this.filePath
                    + " file to save tasks: " + e.getMessage());
        }
    }

}