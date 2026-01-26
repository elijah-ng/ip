package aladdin;

/**
 * Represents checked exceptions that occurs when running Aladdin.
 */
public class AladdinException extends Exception {
    /**
     * Creates an AladdinException instance.
     *
     * @param message Exception message.
     */
    public AladdinException(String message) {
        super(message);
    }
}