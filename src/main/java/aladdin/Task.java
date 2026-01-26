package aladdin;

/**
 * Represents a Task.
 */
public abstract class Task {
    /** Description of Task */
    protected String description;
    /** Status of Task competion */
    protected boolean isDone;

    /**
     * Creates a Task instance.
     *
     * @param description Description of task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Setter for isDone.
     *
     * @param isDone If Task is done, true. Otherwise, false.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns status icon of task, depending on isDone.
     *
     * @return "X" if task is done. Otherwise, empty string if not done.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns a string representation of a serialised Task for storage.
     *
     * @return A string representing the serialised Task.
     */
    public abstract String serialise();

    /**
     * Returns a string representation of a Task.
     *
     * @return A string representing the Task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
