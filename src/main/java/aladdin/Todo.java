package aladdin;

/**
 * Represents a Todo task.
 * Tasks that do not have any date/time attached to it.
 */
public class Todo extends Task {

    /**
     * Creates a Todo task instance.
     *
     * @param description Description of Todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of a serialised Todo task for storage.
     *
     * @return A string representing the serialised Todo task.
     */
    @Override
    public String serialise() {
        return "T|" + (this.isDone ? "1" : "0") + "|" + this.description;
    }

    /**
     * Returns a string representation of a Todo.
     *
     * @return A string representing the Todo.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
