package nicholas.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import nicholas.tasks.Deadline;
import nicholas.tasks.Event;
import nicholas.tasks.Task;
import nicholas.tasks.Todo;

/**
 * Handles parsing of user input into commands and tasks.
 */
public class Parser {

    /**
     * Parses user input into two parts: command and task description with date.
     *
     * @param userInput The full input string from the user.
     * @return A string array containing the command and task description with date.
     */
    public static String[] parseCommand(String userInput) {
        return userInput.trim().split(" ", 2);
    }

    /**
     * Parses a string representation of a task and converts it into a Task object.
     *
     * @param input The string representation of the task.
     * @return A Task object (Todo, Deadline, or Event) parsed from the input.
     * @throws IllegalArgumentException If the input format is invalid.
     */
    public Task parseTask(String input) {
        assert input != null && !input.isEmpty() : "Input string should not be null or empty";
        String[] parts = input.split("\\]\\[", 2);
        assert parts.length == 2 : "Input should be in a valid task format";
        String taskType = parts[0];
        String descriptionAndDate = parts[1].substring(3).trim();

        switch (taskType) {
        case "[T":
            return convertToTodoObject(input, descriptionAndDate);
        case "[D":
            return convertToDeadlineObject(input, descriptionAndDate);
        case "[E":
            return convertToEventObject(input, descriptionAndDate);
        default:
            throw new IllegalArgumentException("Invalid task type: " + taskType);
        }
    }

    /**
     * Converts a parsed string into a {@code Todo} task.
     *
     * @param input The original task string.
     * @param descriptionAndDate The description of the task.
     * @return A Todo object.
     */
    public Task convertToTodoObject(String input, String descriptionAndDate) {
        Task todoTask = new Todo(descriptionAndDate);
        if (input.charAt(4) == 'X') {
            todoTask.markAsDone();
        }
        return todoTask;
    }

    /**
     * Converts a parsed string into a {@code Deadline} task.
     *
     * @param input The original task string.
     * @param descriptionAndDate The description of the task with its deadline.
     * @return A Deadline object.
     * @throws IllegalArgumentException If the deadline format is invalid.
     */
    public Task convertToDeadlineObject(String input, String descriptionAndDate) {
        int deadlineBy = descriptionAndDate.indexOf("by");
        assert deadlineBy != -1 : "Deadline task must contain 'by' keyword";
        if (deadlineBy == -1) {
            throw new IllegalArgumentException("Invalid deadline format");
        }
        String deadlineDate = descriptionAndDate.substring(deadlineBy + 4, descriptionAndDate.length() - 1);
        Task deadlineTask = parseDeadline(descriptionAndDate.substring(0, deadlineBy - 2)
                + " /by " + reverseParseDate(deadlineDate));
        if (input.charAt(4) == 'X') {
            deadlineTask.markAsDone();
        }
        return deadlineTask;
    }

    /**
     * Converts a parsed string into an {@code Event} task.
     *
     * @param input The original task string.
     * @param descriptionAndDate The description of the task with start and end times.
     * @return An Event object.
     * @throws IllegalArgumentException If the event format is invalid.
     */
    public Task convertToEventObject(String input, String descriptionAndDate) {
        int eventStartIndexBegin = descriptionAndDate.indexOf("from:") + 6;
        int eventStartIndexEnd = descriptionAndDate.indexOf("to:") - 1;
        int eventEndIndexBegin = descriptionAndDate.indexOf("to:") + 4;
        int eventEndIndexEnd = descriptionAndDate.length() - 1;
        if (eventStartIndexBegin == -1 || eventStartIndexEnd == -1 || eventEndIndexBegin == -1) {
            throw new IllegalArgumentException("Invalid event format");
        }
        String eventStart = descriptionAndDate.substring(eventStartIndexBegin, eventStartIndexEnd).trim();
        String eventEnd = descriptionAndDate.substring(eventEndIndexBegin, eventEndIndexEnd).trim();
        Task eventTask = parseEvent(descriptionAndDate.substring(0, eventStartIndexBegin - 8)
                + " /from " + reverseParseDate(eventStart) + " /to " + reverseParseDate(eventEnd));
        if (input.charAt(4) == 'X') {
            eventTask.markAsDone();
        }
        return eventTask;
    }

    /**
     * Parses a date string into a formatted date representation.
     *
     * @param by The date string in "yyyy-MM-dd HHmm" format.
     * @return The formatted date string in "MMM dd yyyy HHmm" format.
     * @throws IllegalArgumentException If the date format is invalid.
     */
    public String parseDate(String by) {
        assert by != null && !by.isEmpty() : "Date string should not be null or empty";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(by, inputFormatter);
            return dateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected: yyyy-MM-dd HHmm", e);
        }
    }

    /**
     * Converts a formatted date string back to the original format.
     *
     * @param by The date string in "MMM dd yyyy HHmm" format.
     * @return The date string in "yyyy-MM-dd HHmm" format.
     * @throws IllegalArgumentException If the date format is invalid.
     */
    public String reverseParseDate(String by) {
        assert by != null && !by.isEmpty() : "Date string should not be null or empty";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(by, inputFormatter);
            return dateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected: MMM dd yyyy HHmm", e);
        }
    }

    /**
     * Parses a deadline task description with date and creates a Deadline task.
     *
     * @param input The input string containing the deadline task description and due date.
     * @return A Deadline task object.
     */
    public static Task parseDeadline(String input) {
        assert input != null && !input.isEmpty() : "Deadline input should not be null or empty";
        String[] parts = input.split("/by", 2);
        assert parts.length == 2 : "Deadline format should contain '/by'";
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    /**
     * Parses an event task description with dates and creates an Event task.
     *
     * @param input The input string containing the event description, start time, and end time.
     * @return An Event task object.
     */
    public static Task parseEvent(String input) {
        assert input != null && !input.isEmpty() : "Event input should not be null or empty";
        String[] parts = input.split("/from", 2);
        assert parts.length == 2 : "Event format should contain '/from'";
        String description = parts[0].trim();
        parts = parts[1].split("/to", 2);
        assert parts.length == 2 : "Event format should contain '/to'";
        String from = parts[0].trim();
        String to = parts[1].trim();
        return new Event(description, from, to);
    }
}
