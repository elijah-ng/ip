package aladdin;

import java.util.Scanner;

/**
 * Represents a User Interface to interact with user.
 */
public class Ui {

    /** Line Separator used by Aladdin chatbot */
    private static final String LINE_SEP = "_".repeat(60);

    /** Scanner to read from standard input */
    private static final Scanner scannerInput = new Scanner(System.in);;

    /**
     * Prints line divider.
     */
    public static void printLineDivider() {
        System.out.println(LINE_SEP);
    }

    public static void printWelcome(String name) {
        // Print greeting message
        System.out.println(LINE_SEP);
        System.out.println("Hello! I am " + name + "!");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEP);
    }

    public static void printExit() {
        // Print exit message
        System.out.println(LINE_SEP);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE_SEP);
    }

    public static void printException(AladdinException aladdinException) {
        System.out.println(LINE_SEP);
        System.out.println("AladdinException: " + aladdinException.getMessage());
        System.out.println(LINE_SEP);
    }

    /**
     * Prints a message.
     *
     * @param msg Message to print.
     */
    public static void printMsg(String msg) {
        System.out.println(LINE_SEP);
        System.out.println(msg);
        System.out.println(LINE_SEP);
    }

    /**
     * Prints a message, then string representation of an object.
     *
     * @param msg Message to print.
     * @param obj Object to print its string representation.
     */
    public static void printMsgWithObject(String msg, Object obj) {
        System.out.println(LINE_SEP);
        System.out.println(msg);
        System.out.println(obj);
        System.out.println(LINE_SEP);
    }

    /**
     * Prints a message, string representation of an object, then another message.
     *
     * @param msg1 First message to print.
     * @param obj Object to print its string representation.
     * @param msg2 Second message to print.
     */
    public static void printMsgWithObject(String msg1, Object obj, String msg2) {
        System.out.println(LINE_SEP);
        System.out.println(msg1);
        System.out.println(obj);
        System.out.println(msg2);
        System.out.println(LINE_SEP);
    }

    public static String getUserInput() {
        // Return if there is no user input
        // Required for automated text UI test
        if (!scannerInput.hasNextLine()) {
            return null;
        }

        // Return user input
        return scannerInput.nextLine();
    }

}