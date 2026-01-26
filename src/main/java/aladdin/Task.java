package aladdin;

/**
 * Task Abstract Class
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor for Task.
     *
     * @param description description of task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Setter for isDone.
     *
     * @param isDone set if task is done
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns status icon of task, depending if it is done.
     *
     * @return X if task is done. Otherwise, empty string if not done.
     */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns a string representation of a serialised Task for storage.
     *
     * @return A string representing the serialised task.
     */
    public abstract String serialise();

    /**
     * Returns a string representation of a Task.
     *
     * @return A string representing the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
