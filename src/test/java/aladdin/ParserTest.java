package aladdin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    public void parseUserCommand_invalid_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("random");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid command.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_validList_Success() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("list");
            assertEquals("LIST", formattedCommand[0]);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseUserCommand_invalidMark_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("mark sometask");
            fail();

        } catch (AladdinException e) {
            assertEquals("Task Number must be integer: For input string: \"sometask\"", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidUnmark_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("unmark sometask2");
            fail();

        } catch (AladdinException e) {
            assertEquals("Task Number must be integer: For input string: \"sometask2\"", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDelete_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("delete sometask3");
            fail();

        } catch (AladdinException e) {
            assertEquals("Task Number must be integer: For input string: \"sometask3\"", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_validTodo_Success() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("todo eat");
            assertEquals("TODO", formattedCommand[0]);
            assertEquals("eat", formattedCommand[1]);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseUserCommand_invalidTodo_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("todo");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid command. Please enter full command.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidTodoDesc_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("todo ");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Todo Description. Cannot be empty/blank.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidTodoDesc2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("todo  ");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Todo Description. Cannot be empty/blank.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidTodoDesc3_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("todo hel|o");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Todo Description. Cannot Contain '|'.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_validDeadline_Success() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("deadline eat /by 1-1-2026 1400");
            assertEquals("DEADLINE", formattedCommand[0]);
            assertEquals("eat", formattedCommand[1]);
            assertEquals("1-1-2026 1400", formattedCommand[2]);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseUserCommand_invalidDeadline_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("deadline");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid command. Please enter full command.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDesc_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("deadline  /by 1-1-2026 1400");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Deadline Description. Cannot be empty/blank.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDesc2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Deadline hel|o /by 1-1-2026 1400");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Deadline Description. Cannot Contain '|'.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDesc3_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Deadline  ");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid deadline format. Please specify {description} /by {date/time}.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDesc4_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Deadline /by 1-1-2026 1400");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid deadline format. Please specify {description} /by {date/time}.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDesc5_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Deadline a deadline task");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid deadline format. Please specify {description} /by {date/time}.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDate_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Deadline a deadline task /by tmr");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Deadline 'by' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator() + "Text 'tmr' could not be parsed at index 0", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDate2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Deadline a deadline task /by 1-12026 1400");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Deadline 'by' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator()
                    + "Text '1-12026 1400' could not be parsed at index 7",
                    e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidDeadlineDate3_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Deadline a deadline task /by 1-1-2026 1460");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Deadline 'by' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator()
                    + "Text '1-1-2026 1460' could not be parsed: "
                    + "Invalid value for MinuteOfHour (valid values 0 - 59): 60",
                    e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_validEvent_Success() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("event carnival /from 1-1-2026 1400 /to 1-1-2026 1600");
            assertEquals("EVENT", formattedCommand[0]);
            assertEquals("carnival", formattedCommand[1]);
            assertEquals("1-1-2026 1400", formattedCommand[2]);
            assertEquals("1-1-2026 1600", formattedCommand[3]);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseUserCommand_invalidEvent_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("event");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid command. Please enter full command.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventDesc_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("event  /from 1-1-2026 1400 /to 1-1-2026 1600");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event Description. Cannot be empty/blank.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventDesc2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("event hel|o /from 1-1-2026 1400 /to 1-1-2026 1600");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event Description. Cannot Contain '|'.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventDesc3_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Event  ");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid event format. "
                    + "Please specify {description} /from {date/time} /to {date/time}.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventDesc4_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Event /from 1-1-2026 1400");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid event format. "
                    + "Please specify {description} /from {date/time} /to {date/time}.", e.getMessage());        }
    }

    @Test
    public void parseUserCommand_invalidEventDesc5_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Event an event");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid event format. "
                    + "Please specify {description} /from {date/time} /to {date/time}.", e.getMessage());        }
    }

    @Test
    public void parseUserCommand_invalidEventDateRange_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-1-2026 1400 /to 1-1-2026 1400");
            fail();

        } catch (AladdinException e) {
            assertEquals("Event 'from' must be before 'to' Date/Time.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventDateRange2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-1-2026 1400 /to 1-1-2026 1200");
            fail();

        } catch (AladdinException e) {
            assertEquals("Event 'from' must be before 'to' Date/Time.", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventFromDate_exceptionThrown() {
        try {
            String[] formattedCommand = Parser.parseUserCommand("Event an event /from tmr /to tmr");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event 'from' and/or 'to' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator() + "Text 'tmr' could not be parsed at index 0", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventFromDate2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-1-2026 1460 /to 1-1-2026 1600");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event 'from' and/or 'to' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator()
                    + "Text '1-1-2026 1460' could not be parsed: "
                    + "Invalid value for MinuteOfHour (valid values 0 - 59): 60",
                    e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventFromDate3_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-12026 1400 /to 1-1-2026 1600");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event 'from' and/or 'to' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator()
                    + "Text '1-12026 1400' could not be parsed at index 7",
                    e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventToDate_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-1-2026 1400 /to tmr");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event 'from' and/or 'to' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator() + "Text 'tmr' could not be parsed at index 0", e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventToDate2_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-1-2026 1400 /to 1-1-2026 1660");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event 'from' and/or 'to' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator()
                    + "Text '1-1-2026 1660' could not be parsed: "
                    + "Invalid value for MinuteOfHour (valid values 0 - 59): 60",
                    e.getMessage());
        }
    }

    @Test
    public void parseUserCommand_invalidEventToDate3_exceptionThrown() {
        try {
            String[] formattedCommand = Parser
                    .parseUserCommand("Event an event /from 1-1-2026 1400 /to 1-12026 1600");
            fail();

        } catch (AladdinException e) {
            assertEquals("Invalid Event 'from' and/or 'to' Date. Please enter in d-M-yyyy HHmm format."
                    + System.lineSeparator()
                    + "Text '1-12026 1600' could not be parsed at index 7",
                    e.getMessage());
        }
    }

}
