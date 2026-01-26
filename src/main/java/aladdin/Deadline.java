package aladdin;

import java.time.LocalDateTime;

/**
 * Deadline Class
 * Tasks that need to be done before a specific date/time.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Constructor for Deadline.
     *
     * @param description description of deadline task.
     * @param by date/time for deadline.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of a serialised Deadline task for storage.
     *
     * @return A string representing the serialised Deadline task.
     */
    @Override
    public String serialise() {
        return "D|" + (this.isDone ? "1" : "0") + "|"
                + this.description + "|" + this.by.format(Aladdin.DATE_TIME_STORE);
    }

    /**
     * Returns a string representation of a Deadline task.
     *
     * @return A string representing the Deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: "
                + this.by.format(Aladdin.DATE_TIME_DISPLAY) + ")";
    }
}
