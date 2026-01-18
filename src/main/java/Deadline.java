/**
 * Deadline Class
 * Tasks that need to be done before a specific date/time.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Constructor for Deadline.
     *
     * @param description description of deadline task.
     * @param by date/time for deadline.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of a Deadline task.
     *
     * @return A string representing the Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + this.by + ")";
    }
}