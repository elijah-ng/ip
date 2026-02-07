package aladdin;

import java.time.LocalDateTime;

/**
 * Represents an Event task.
 * Tasks that start at a specific date/time and ends at a specific date/time.
 */
public class Event extends Task {
    /** Event From Date/Time */
    protected LocalDateTime from;
    /** Event To Date/Time */
    protected LocalDateTime to;

    /**
     * Creates an Event task instance.
     *
     * @param description Description of event.
     * @param from Start date/time of event.
     * @param to End date/time of event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns from DateTime
     *
     * @return Event's from DateTime
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Returns to DateTime
     *
     * @return Event's to DateTime
     */
    public LocalDateTime getTo() {
        return this.to;
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
     * Returns a string with description, from and to LocalDateTime of an Event.
     *
     * @return A compact string representation of the Event.
     */
    public String toDescriptionFromToString() {
        return super.getDescription() + " | "
                + this.from.format(Aladdin.DATE_TIME_DISPLAY) + " | "
                + this.to.format(Aladdin.DATE_TIME_DISPLAY);
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
