/**
 * AladdinException class
 * Represent checked exceptions specific to Aladdin.
 */
public class AladdinException extends Exception {
    /**
     * Constructor for AladdinException.
     *
     * @param message exception message.
     */
    public AladdinException(String message) {
        super(message);
    }
}