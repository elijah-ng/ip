import java.time.LocalDateTime;

/**
 * Event Class
 * Tasks that start at a specific date/time and ends at a specific date/time.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructor for Event.
     *
     * @param description description of event.
     * @param from start date/time of event.
     * @param to end date/time of event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of a serialised Event task for storage.
     *
     * @return A string representing the serialised Event task.
     */
    @Override
    public String serialise() {
        return "E|" + (this.isDone ? "1" : "0") + "|" + this.description + "|"
                + this.from.format(Aladdin.DATE_TIME_STORE) + "|" + this.to.format(Aladdin.DATE_TIME_STORE);
    }

    /**
     * Returns a string representation of an Event.
     *
     * @return A string representing the Event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: " + this.from.format(Aladdin.DATE_TIME_DISPLAY)
                + ". To: " + this.to.format(Aladdin.DATE_TIME_DISPLAY) + ")";
    }
}