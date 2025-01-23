public class EmptyDescriptionValidator {
    // Method to validate the string
    public static void validateEmptyDescription(String taskDescription, String task) throws EmptyDescriptionException {
        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            throw new EmptyDescriptionException(task);
        }
    }
}