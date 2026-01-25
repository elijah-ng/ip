import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /*
    public TaskList(Storage initStorage) {
        this.tasks = new ArrayList<Task>();
    }
    */

    public int getSize() {
        return this.tasks.size();
    }

    public void addToTaskList(Task t) {
        this.tasks.add(t);
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Deletes a task from list.
     *
     * @param taskNumber Specified task to delete (starts from 1).
     * @return The task deleted if valid taskNumber. Otherwise, return null.
     */
    public Task deleteTask(int taskNumber) {
        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.tasks.size())) {
            Task deletedTask = this.tasks.remove(taskNumber - 1);
            return deletedTask;

        } else {
            return null;
        }
    }

    /**
     * Change Task Status and either Mark or Unmark the specified task as done or not done.
     *
     * @param taskNumber Specified task to mark or unmark.
     * @return The modified task if valid taskNumber. Otherwise, return null.
     */
    public Task changeTaskStatus(int taskNumber, boolean isDone) {
        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.tasks.size())) {
            if (isDone) {
                // Mark task as done
                this.tasks.get(taskNumber - 1).setDone(true);

            } else {
                // Unmark task as not done
                this.tasks.get(taskNumber - 1).setDone(false);
            }
            // Return modified task
            return tasks.get(taskNumber - 1);

        } else {
            // taskNumber is not valid
            return null;
        }
    }

    /**
     * Returns a string representation of taskList.
     */
    @Override
    public String toString() {
        StringBuilder taskListString = new StringBuilder();

        for (int i = 0; i < this.tasks.size(); i++) {
            int taskNumber = i + 1;
            taskListString.append(taskNumber + ". " + this.tasks.get(i));

            // Add new line if not last item
            if (i < this.tasks.size() - 1) {
                taskListString.append(System.lineSeparator());
            }
        }
        return taskListString.toString();
    }

}