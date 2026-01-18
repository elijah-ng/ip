/**
 * Todo Class
 * tasks without any date/time attached to it
 */
public class Todo extends Task {

    /**
     * Constructor for Todo.
     *
     * @param description description of event.
     */
    public Todo(String description) {
        super(description);
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