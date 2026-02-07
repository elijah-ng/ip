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
    private enum Command { LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, BYE, FREE }

    /**
     * Returns the formatted user command as an array of substrings.
     *
     * @param userInput The user's input to parse.
     * @return A string array containing substrings of formatted user command.
     * @throws AladdinException If user command is invalid.
     */
    public static String[] parseUserCommand(String userInput) throws AladdinException {
        assert userInput != null : "userInput should not be null";

        String[] userInputArray = userInput.split(" ", 2);

        try {
            // throws IllegalArgumentException if command does not match (or empty command)
            Command mainCommand = Command.valueOf(userInputArray[0].toUpperCase());

            // Only list command can have 1 substring. All other commands have at least 2 substrings
            boolean isByeOrList = (mainCommand == Command.BYE) || (mainCommand == Command.LIST);
            if ((userInputArray.length != 2) && (!isByeOrList)) {
                throw new AladdinException("Invalid command. Please enter full command.");
            }

            String[] formattedUserCommand = null;

            switch (mainCommand) {
            case BYE:
                formattedUserCommand = Parser.formatBye();
                break;
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
            case FIND:
                formattedUserCommand = Parser.formatFind(userInputArray[1]);
                break;
            case FREE:
                formattedUserCommand = Parser.formatFindFreeTimes(userInputArray[1]);
                break;
            default:
                // Do nothing. Should never reach default case
                break;
            }
            return formattedUserCommand;

        } catch (IllegalArgumentException e) {
            throw new AladdinException("Invalid command.");
        }
    }

    private static String[] formatBye() {
        String[] formattedByeCommand = new String[1];
        formattedByeCommand[0] = "BYE";
        return formattedByeCommand;
    }

    private static String[] formatList() {
        String[] formattedListCommand = new String[1];
        formattedListCommand[0] = "LIST";
        return formattedListCommand;
    }

    private static String[] formatMark(String commandDescription) throws AladdinException {
        assert commandDescription != null : "commandDescription should not be null";

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
        assert commandDescription != null : "commandDescription should not be null";

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
        assert commandDescription != null : "commandDescription should not be null";

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
        assert commandDescription != null : "commandDescription should not be null";

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
        assert commandDescription != null : "commandDescription should not be null";

        String[] formattedDeadlineCommand = new String[3];
        formattedDeadlineCommand[0] = "DEADLINE";

        // Split into max 2 substrings
        String[] descriptionAndBy = commandDescription.split(" /by ", 2);

        if (descriptionAndBy.length != 2) {
            throw new AladdinException("Invalid deadline format. "
                    + "Please specify {description} /by {date/time}.");
        }

        // Check if description is empty, null, or whitespaces only
        if (descriptionAndBy[0].isBlank()) {
            throw new AladdinException("Invalid Deadline Description. Cannot be empty/blank.");

        } else if (descriptionAndBy[0].contains("|")) {
            throw new AladdinException("Invalid Deadline Description. Cannot Contain '|'.");
        }
        formattedDeadlineCommand[1] = descriptionAndBy[0];

        try {
            LocalDateTime byDate = LocalDateTime.parse(descriptionAndBy[1], Aladdin.DATE_TIME_STORE);
            formattedDeadlineCommand[2] = byDate.format(Aladdin.DATE_TIME_STORE);

        } catch (DateTimeParseException e) {
            throw new AladdinException("Invalid Deadline 'by' Date. "
                    + "Please enter in d-M-yyyy HHmm format." + System.lineSeparator()
                    + e.getMessage());
        }

        return formattedDeadlineCommand;
    }

    private static String[] formatEvent(String commandDescription) throws AladdinException {
        assert commandDescription != null : "commandDescription should not be null";

        String[] formattedEventCommand = new String[4];
        formattedEventCommand[0] = "EVENT";

        String eventFormatError = "Invalid event format. "
                + "Please specify {description} /from {date/time} /to {date/time}.";

        String[] descriptionAndDates = commandDescription.split(" /from ", 2);
        if (descriptionAndDates.length != 2) {
            throw new AladdinException(eventFormatError);
        }

        String[] fromAndTo = descriptionAndDates[1].split(" /to ", 2);
        if (fromAndTo.length != 2) {
            throw new AladdinException(eventFormatError);
        }

        // Check if description is empty, null, or whitespaces only
        if (descriptionAndDates[0].isBlank()) {
            throw new AladdinException("Invalid Event Description. Cannot be empty/blank.");

        } else if (descriptionAndDates[0].contains("|")) {
            throw new AladdinException("Invalid Event Description. Cannot Contain '|'.");
        }
        formattedEventCommand[1] = descriptionAndDates[0];

        try {
            LocalDateTime fromDate = LocalDateTime.parse(fromAndTo[0], Aladdin.DATE_TIME_STORE);
            LocalDateTime toDate = LocalDateTime.parse(fromAndTo[1], Aladdin.DATE_TIME_STORE);

            // If fromDate is not before toDate (fromDate equal or after toDate)
            if (!fromDate.isBefore(toDate)) {
                throw new AladdinException("Event 'from' must be before 'to' Date/Time.");
            }

            formattedEventCommand[2] = fromDate.format(Aladdin.DATE_TIME_STORE);
            formattedEventCommand[3] = toDate.format(Aladdin.DATE_TIME_STORE);

        } catch (DateTimeParseException e) {
            throw new AladdinException("Invalid Event 'from' and/or 'to' Date. "
                    + "Please enter in d-M-yyyy HHmm format." + System.lineSeparator()
                    + e.getMessage());
        }

        return formattedEventCommand;
    }

    private static String[] formatFind(String commandDescription) {
        assert commandDescription != null : "commandDescription should not be null";

        String[] formattedFindCommand = new String[2];
        formattedFindCommand[0] = "FIND";
        formattedFindCommand[1] = commandDescription;

        return formattedFindCommand;
    }

    private static String[] formatFindFreeTimes(String commandDescription) throws AladdinException {
        assert commandDescription != null : "commandDescription should not be null";

        String[] formattedFreeCommand = new String[3];
        formattedFreeCommand[0] = "FREE";

        String freeFormatError = "Invalid find free times format. "
                + "Please specify /from {date/time} /to {date/time}.";

        String[] emptyStringAndDates = commandDescription.split("/from ", 2);
        // Throw exception if "/from" not present or there are characters/whitespaces are before it
        if (emptyStringAndDates.length != 2 || !emptyStringAndDates[0].isEmpty()) {
            throw new AladdinException(freeFormatError);
        }

        String[] fromAndTo = emptyStringAndDates[1].split(" /to ", 2);
        if (fromAndTo.length != 2) {
            throw new AladdinException(freeFormatError);
        }

        try {
            LocalDateTime fromDate = LocalDateTime.parse(fromAndTo[0], Aladdin.DATE_TIME_STORE);
            LocalDateTime toDate = LocalDateTime.parse(fromAndTo[1], Aladdin.DATE_TIME_STORE);

            // If fromDate is not before toDate (fromDate equal or after toDate)
            if (!fromDate.isBefore(toDate)) {
                throw new AladdinException("Free 'from' must be before 'to' Date/Time.");
            }

            formattedFreeCommand[1] = fromDate.format(Aladdin.DATE_TIME_STORE);
            formattedFreeCommand[2] = toDate.format(Aladdin.DATE_TIME_STORE);

        } catch (DateTimeParseException e) {
            throw new AladdinException("Invalid free 'from' and/or 'to' Date. "
                    + "Please enter in d-M-yyyy HHmm format." + System.lineSeparator()
                    + e.getMessage());
        }

        return formattedFreeCommand;
    }
}
