package aladdin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    @Test
    public void load_invalidPath_exceptionThrown() {
        try {
            TaskList taskList = new TaskList();
            Storage testStorage = new Storage("unknown/file.txt");
            testStorage.load(taskList);
            fail();

        } catch (AladdinException e) {
            assertEquals("Note: There was no saved tasks file found from a previous session."
                    + System.lineSeparator()
                    + "You may safely ignore this if this is your first time using Aladdin.",
                    e.getMessage());
        }
    }

    @Test
    public void deserialiseTask_validTodo_Success() {
        try {
            Task testTask = Storage.deserialiseTask("T|1|read book");
            Todo testTodo = (Todo) testTask;
            assertEquals(true, testTodo.isDone);
            assertEquals("read book", testTodo.description);

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deserialiseTask_validDeadline_Success() {
        try {
            Task testTask = Storage.deserialiseTask("D|0|return book|6-6-2026 1800");
            Deadline testDeadline = (Deadline) testTask;
            assertEquals(false, testDeadline.isDone);
            assertEquals("return book", testDeadline.description);
            assertEquals("2026-06-06T18:00", testDeadline.by.toString());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deserialiseTask_validEvent_Success() {
        try {
            Task testTask = Storage.deserialiseTask("E|0|project meeting|6-8-2026 1400|6-8-2026 1600");
            Event testEvent = (Event) testTask;
            assertEquals(false, testEvent.isDone);
            assertEquals("project meeting", testEvent.description);
            assertEquals("2026-08-06T14:00", testEvent.from.toString());
            assertEquals("2026-08-06T16:00", testEvent.to.toString());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deserialiseTask_invalidTodo_exceptionThrown() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {Storage.deserialiseTask("T|0");}
        );
    }

    @Test
    public void deserialiseTask_invalidDeadline_exceptionThrown() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {Storage.deserialiseTask("D|0|deadline task");}
        );
    }

    @Test
    public void deserialiseTask_invalidEvent_exceptionThrown() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> {Storage.deserialiseTask("E|1|event task|6-8-2026 1400");}
        );
    }

}
