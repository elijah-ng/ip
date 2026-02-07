package aladdin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    /** ArrayList of Task objects */
    private ArrayList<Task> tasks;

    /**
     * Creates a TaskList instance.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Returns number of tasks in the list.
     *
     * @return Size of ArrayList.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Adds a Task to the list.
     *
     * @param t Task to be added to the list.
     */
    public void addToTaskList(Task t) {
        this.tasks.add(t);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of Task to return.
     * @return The Task at the specified index.
     */
    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskNumber Specified task to delete (starts from 1).
     * @return The Task deleted if valid taskNumber. Otherwise, return null.
     */
    public Task deleteTask(int taskNumber) {
        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.tasks.size())) {
            Task deletedTask = this.tasks.remove(taskNumber - 1);
            return deletedTask;

        } else {
            return null;
        }
    }

    /**
     * Changes Task status.
     * Either mark or unmark the specified task as done or not done.
     *
     * @param taskNumber Specified task to mark or unmark.
     * @return The modified task if valid taskNumber. Otherwise, return null.
     */
    public Task changeTaskStatus(int taskNumber, boolean isDone) {
        // If taskNumber is valid
        if ((0 < taskNumber) && (taskNumber <= this.tasks.size())) {
            if (isDone) {
                // Mark task as done
                this.tasks.get(taskNumber - 1).setDone(true);

            } else {
                // Unmark task as not done
                this.tasks.get(taskNumber - 1).setDone(false);
            }
            // Return modified task
            return tasks.get(taskNumber - 1);

        } else {
            // taskNumber is not valid
            return null;
        }
    }

    /**
     * Searches tasks for the specified keyword.
     *
     * @param keyword Keyword to match with the tasks' descriptions.
     * @return A String representing the tasks with keyword found in the task description.
     */
    public String searchTasks(String keyword) {
        assert keyword != null : "keyword should not be null";

        StringBuilder matchingTaskListString = new StringBuilder();
        String separator = "";

        for (int i = 0; i < this.tasks.size(); i++) {
            Task currentTask = this.tasks.get(i);

            if (currentTask.description.contains(keyword)) {
                // No new line separator for first task
                matchingTaskListString.append(separator);
                separator = System.lineSeparator();

                int taskNumber = i + 1;
                matchingTaskListString.append(taskNumber + ". " + this.tasks.get(i));
            }

        }
        return matchingTaskListString.toString();
    }

    /**
     * Searches free slots that do not clash with on existing Event tasks.
     *
     * @param start Start Date/Time to search for free slots.
     * @param end End Date/Time to search for free slots.
     * @return A string representing the free slots.
     */
    public String findFreeSlots(LocalDateTime start, LocalDateTime end) {
        assert start != null : "start should not be null";
        assert end != null : "end should not be null";

        // Extract Event tasks coinciding between start and end (exclusive) from taskList
        List<Event> currentEvents = this.tasks.stream()
                .filter(Event.class::isInstance)
                .map(Event.class::cast)
                .filter((Event event) -> event.getFrom().isBefore(end) && event.getTo().isAfter(start))
                .collect(Collectors.toList());
        // Sort currentEvents by "from" then "to".
        Collections.sort(currentEvents, Comparator.comparing(Event::getFrom).thenComparing(Event::getTo));

        List<Event> freeSlots = new ArrayList<>();
        LocalDateTime current = start;
        int freeSlotNumber = 1;

        for (Event busyEvent : currentEvents) {
            LocalDateTime busyEventStart = busyEvent.getFrom();
            LocalDateTime busyEventEnd = busyEvent.getTo();

            if (busyEventStart.isAfter(current)) { // Free slot is found
                freeSlots.add(new Event("Slot " + freeSlotNumber, current, busyEventStart));
                freeSlotNumber += 1;
            }
            // Move current to be max(current, busyEventEnd)
            current = current.isAfter(busyEventEnd) ? current : busyEventEnd;
        }

        if (current.isBefore(end)) { // Check for free slot before end
            freeSlots.add(new Event("Slot " + freeSlotNumber, current, end));
        }

        return freeSlotsToString(freeSlots);
    }

    // Helper method to convert freeSlots to String representation.
    private String freeSlotsToString(List<Event> freeSlots) {
        // Create String representation of free slots to return
        StringBuilder freeSlotsString = new StringBuilder();

        for (int i = 0; i < freeSlots.size(); i++) {
            freeSlotsString.append(freeSlots.get(i).toDescriptionFromToString());

            // Add new line if not last item
            if (i < freeSlots.size() - 1) {
                freeSlotsString.append(System.lineSeparator());
            }
        }

        return freeSlotsString.toString();
    }

    /**
     * Returns a string representation of a TaskList.
     *
     * @return A string representing the TaskList.
     */
    @Override
    public String toString() {
        StringBuilder taskListString = new StringBuilder();

        for (int i = 0; i < this.tasks.size(); i++) {
            int taskNumber = i + 1;
            taskListString.append(taskNumber + ". " + this.tasks.get(i));

            // Add new line if not last item
            if (i < this.tasks.size() - 1) {
                taskListString.append(System.lineSeparator());
            }
        }
        return taskListString.toString();
    }

}
