public class Parser {

    public static String[] parseCommand(String userInput) {
        return userInput.trim().split(" ", 2);
    }

    public static Task parseTask(String input) {
        String[] parts = input.split("\\]\\[", 2);
        String taskType = parts[0];
        // Common description extraction (excluding the task type)
        String descriptionAndDate = parts[1].substring(3).trim();
        switch (taskType) {
        case "[T":
            // Handle "todo" task
            return new Todo(descriptionAndDate);
        case "[D":
            // Handle "deadline" task
            // Split on the "by" keyword to extract deadline time
            int deadlineBy = descriptionAndDate.indexOf("by");
            if (deadlineBy == -1) {
                throw new IllegalArgumentException("Invalid deadline format");
            }
            String deadlineDate = descriptionAndDate.substring(deadlineBy + 4, descriptionAndDate.length() - 1);
            return parseDeadline(descriptionAndDate.substring(0, deadlineBy - 2) + " /by " + deadlineDate);
        case "[E":
            // Handle "event" task
            // Find start and end times for the event
            int eventStartIndexBegin = descriptionAndDate.indexOf("from:") + 6;
            int eventStartIndexEnd = descriptionAndDate.indexOf("to:") - 1;
            int eventEndIndexBegin = descriptionAndDate.indexOf("to:") + 4;
            int eventEndIndexEnd = descriptionAndDate.length() - 1;

            // Handle case where no valid "from" or "to" was found
            if (eventStartIndexBegin == -1 || eventStartIndexEnd == -1 || eventEndIndexBegin == -1) {
                throw new IllegalArgumentException("Invalid event format");
            }

            // Extract the times for the event
            String eventStart = descriptionAndDate.substring(eventStartIndexBegin, eventStartIndexEnd).trim();
            String eventEnd = descriptionAndDate.substring(eventEndIndexBegin, eventEndIndexEnd).trim();

            // Return parsed event task
            return parseEvent(descriptionAndDate.substring(0, eventStartIndexBegin - 8) + " /from " + eventStart + " /to " + eventEnd);

        default:
            throw new IllegalArgumentException("Invalid task type: " + taskType);
        }
    }

    public static Task parseDeadline(String input) {
        String[] parts = input.split("/by", 2);
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    public static Task parseEvent(String input) {
        String[] parts = input.split("/from", 2);
        String description = parts[0].trim();
        parts = parts[1].split("/to", 2);
        String from = parts[0].trim();
        String to = parts[1].trim();
        return new Event(description, from, to);
    }
}
