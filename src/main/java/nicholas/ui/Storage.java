package nicholas.ui;

import java.io.*;
import java.util.*;

import nicholas.tasks.Task;

public class Storage {
    private String filePath;
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    // Load tasks from a file
    public List<Task> loadTasks() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            Parser taskParser = new Parser();
            String line = scanner.nextLine();
            tasks.add(taskParser.parseTask(line));
        }
        scanner.close();
        return tasks;
    }

    // Save tasks to a file
    public void saveTasks(List<Task> tasks) throws FileNotFoundException {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            for (Task task : tasks) {
                fw.write(task.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Empties a file
    public static void emptyFile(String filePath) throws IOException {
        File file = new File(filePath);

        // Create a FileWriter with the file in overwrite mode (it will truncate the file to zero length)
        try (FileWriter writer = new FileWriter(file)) {
            // Writing an empty string to the file will effectively empty it
            writer.write("");
        } catch (IOException e) {
            // Rethrow the exception to be handled by the caller
            throw new IOException("Error occurred while emptying the file: " + filePath, e);
        }
    }
}
