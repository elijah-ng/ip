/**
 * Event Class
 * Tasks that start at a specific date/time and ends at a specific date/time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructor for Event.
     *
     * @param description description of event.
     * @param from start date/time of event.
     * @param to end date/time of event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of an Event.
     *
     * @return A string representing the Event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (From: " + this.from + ". To: " + this.to + ")";
    }
}