package aladdin;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

// Solution below inspired by AB2. https://github.com/se-edu/addressbook-level2
/**
 * Represents a Parser to make sense of user commands for Aladdin.
 */
public class Parser {

    /**
     * Enumeration for Commands
     */
    private enum Command { LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE }

    /**
     *
     * @param userInput
     * @return A string array containing substrings of formatted user command.
     * @throws AladdinException If invalid Command formatting.
     * @throws IllegalArgumentException If Command is Invalid.
     */
    public static String[] parseUserCommand(String userInput) throws AladdinException {
        String[] userInputArray = userInput.split(" ", 2);

        try {
            // throws IllegalArgumentException if command does not match (or empty command)
            Command mainCommand = Command.valueOf(userInputArray[0].toUpperCase());

            // Only list command can have 1 substring. All other commands have at least 2 substrings
            if ((userInputArray.length != 2) && (mainCommand != Command.LIST)) {
                throw new AladdinException("Invalid command. Please enter full command.");
            }

            String[] formattedUserCommand = null;

            switch (mainCommand) {
            case LIST:
                formattedUserCommand = Parser.formatList();
                break;

            case MARK:
                formattedUserCommand = Parser.formatMark(userInputArray[1]);
                break;

            case UNMARK:
                formattedUserCommand = Parser.formatUnmark(userInputArray[1]);
                break;

            case DELETE:
                formattedUserCommand = Parser.formatDelete(userInputArray[1]);
                break;

            case TODO:
                formattedUserCommand = Parser.formatTodo(userInputArray[1]);
                break;

            case DEADLINE:
                formattedUserCommand = Parser.formatDeadline(userInputArray[1]);
                break;

            case EVENT:
                formattedUserCommand = Parser.formatEvent(userInputArray[1]);
                break;
            }
            return formattedUserCommand;

        } catch (IllegalArgumentException e) {
            throw new AladdinException("Invalid command.");
        }
    }

    private static String[] formatList() {
        String[] formattedListCommand = new String[1];
        formattedListCommand[0] = "LIST";
        return formattedListCommand;
    }

    private static String[] formatMark(String commandDescription) throws AladdinException {
        String[] formattedMarkCommand = new String[2];
        formattedMarkCommand[0] = "MARK";

        try {
            int taskNumber = Integer.parseInt(commandDescription);
            formattedMarkCommand[1] = Integer.toString(taskNumber);

        } catch (NumberFormatException e) {
            throw new AladdinException("Task Number must be integer: " + e.getMessage());
        }

        return formattedMarkCommand;
    }

    private static String[] formatUnmark(String commandDescription) throws AladdinException {
        String[] formattedUnmarkCommand = new String[2];
        formattedUnmarkCommand[0] = "UNMARK";

        try {
            int taskNumber = Integer.parseInt(commandDescription);
            formattedUnmarkCommand[1] = Integer.toString(taskNumber);

        } catch (NumberFormatException e) {
            throw new AladdinException("Task Number must be integer: " + e.getMessage());
        }

        return formattedUnmarkCommand;
    }

    private static String[] formatDelete(String commandDescription) throws AladdinException {
        String[] formattedDeleteCommand = new String[2];
        formattedDeleteCommand[0] = "DELETE";

        try {
            int taskNumber = Integer.parseInt(commandDescription);
            formattedDeleteCommand[1] = Integer.toString(taskNumber);

        } catch (NumberFormatException e) {
            throw new AladdinException("Task Number must be integer: " + e.getMessage());
        }

        return formattedDeleteCommand;
    }

    private static String[] formatTodo(String commandDescription) throws AladdinException {
        String[] formattedTodoCommand = new String[2];
        formattedTodoCommand[0] = "TODO";

        // Check if description is empty, null, or whitespaces only
        if (commandDescription.isBlank()) {
            throw new AladdinException("Invalid Todo Description. Cannot be empty/blank.");

        } else if (commandDescription.contains("|")) {
            throw new AladdinException("Invalid Todo Description. Cannot Contain '|'.");

        } else {
            formattedTodoCommand[1] = commandDescription;
        }

        return formattedTodoCommand;
    }

    private static String[] formatDeadline(String commandDescription) throws AladdinException {
        String[] formattedDeadlineCommand = new String[3];
        formattedDeadlineCommand[0] = "DEADLINE";

        // Split into max 2 substrings
        String[] commandDescriptionArray = commandDescription.split(" /by ", 2);

        if (commandDescriptionArray.length != 2) {
            throw new AladdinException("Invalid deadline format. "
                    + "Please specify {description} /by {date/time}.");
        }

        // Check if description is empty, null, or whitespaces only
        if (commandDescriptionArray[0].isBlank()) {
            throw new AladdinException("Invalid Deadline Description. Cannot be empty/blank.");

        } else if (commandDescriptionArray[0].contains("|")) {
            throw new AladdinException("Invalid Deadline Description. Cannot Contain '|'.");
        }
        formattedDeadlineCommand[1] = commandDescriptionArray[0];

        try {
            LocalDateTime byDate = LocalDateTime.parse(commandDescriptionArray[1], Aladdin.DATE_TIME_STORE);
            formattedDeadlineCommand[2] = byDate.format(Aladdin.DATE_TIME_STORE);

        } catch (DateTimeParseException e) {
            throw new AladdinException("Invalid Deadline 'by' Date. "
                    + "Please enter in d-M-yyyy HHmm format." + System.lineSeparator()
                    + e.getMessage());
        }

        return formattedDeadlineCommand;
    }

    private static String[] formatEvent(String commandDescription) throws AladdinException {
        String[] formattedEventCommand = new String[4];
        formattedEventCommand[0] = "EVENT";

        String eventFormatError = "Invalid event format. "
                + "Please specify {description} /from {date/time} /to {date/time}.";

        // Split string by "/from"
        String[] commandDescriptionArray1 = commandDescription.split(" /from ", 2);
        if (commandDescriptionArray1.length != 2) {
            throw new AladdinException(eventFormatError);
        }

        // Split string by "/to"
        String[] commandDescriptionArray2 = commandDescriptionArray1[1].split(" /to ", 2);
        if (commandDescriptionArray2.length != 2) {
            throw new AladdinException(eventFormatError);
        }

        // Check if description is empty, null, or whitespaces only
        if (commandDescriptionArray1[0].isBlank()) {
            throw new AladdinException("Invalid Event Description. Cannot be empty/blank.");

        } else if (commandDescriptionArray1[0].contains("|")) {
            throw new AladdinException("Invalid Event Description. Cannot Contain '|'.");
        }
        formattedEventCommand[1] = commandDescriptionArray1[0];

        try {
            LocalDateTime fromDate = LocalDateTime.parse(commandDescriptionArray2[0], Aladdin.DATE_TIME_STORE);
            LocalDateTime toDate = LocalDateTime.parse(commandDescriptionArray2[1], Aladdin.DATE_TIME_STORE);

            // If fromDate is not before toDate (fromDate equal or after toDate)
            if (!fromDate.isBefore(toDate)) {
                throw new AladdinException("Event 'from' must be before 'to' Date/Time.");
            }

            formattedEventCommand[2] = fromDate.format(Aladdin.DATE_TIME_STORE);
            formattedEventCommand[3] = toDate.format(Aladdin.DATE_TIME_STORE);

        } catch (DateTimeParseException e) {
            throw new AladdinException("Invalid Event 'from' and/or 'to' Date. "
                    + "Please enter in d-M-yyyy HHmm format.\n"
                    + e.getMessage());
        }

        return formattedEventCommand;
    }

}
