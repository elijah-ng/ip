/**
 * Task Class
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Task Constructor.
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
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Prints Task.
     *
     * @return A string representing the task
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}