public class Mark {
    private boolean isMark;
    private int taskIndex;
    public Mark(boolean isMark, String description) {
        this.isMark = isMark;
        // Ensure description is not null
        if (description != null) {
            if (isMark) {
                if (description.length() >= 7) {
                    int numOne = description.charAt(description.length() - 1) - '0'; // Last digit
                    int numTwo = description.length() >= 8 ? description.charAt(description.length() - 2) - '0' : 0; // Second-to-last digit
                    taskIndex = numOne + (numTwo * 10);
                }
            } else {
                if (description.length() >= 9) {
                    int numOne = description.charAt(description.length() - 1) - '0'; // Last digit
                    int numTwo = description.length() >= 10 ? description.charAt(description.length() - 2) - '0' : 0; // Second-to-last digit
                    taskIndex = numOne + (numTwo * 10);
                }
            }
        } else {
            System.out.println("Description cannot be null.");
        }
    }

    public int getTaskIndex() {
        return this.taskIndex;
    }

    public boolean isMark() {
        return this.isMark;
    }
}
