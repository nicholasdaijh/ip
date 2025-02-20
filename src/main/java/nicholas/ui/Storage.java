package nicholas.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nicholas.tasks.Task;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private static final String filePath = "tasks.txt";

    /**
     * Loads tasks from the specified file.
     *
     * @return A list of tasks loaded from the file.
     * @throws FileNotFoundException If the file does not exist.
     */
    public List<Task> loadTasks() throws FileNotFoundException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        assert file.exists() : "File should exist before reading";
        if (!file.exists()) {
            return tasks;
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            Parser taskParser = new Parser();
            String line = scanner.nextLine();
            assert line != null && !line.isEmpty() : "Task line should not be null or empty";
            tasks.add(taskParser.parseTask(line));
        }
        scanner.close();
        return tasks;
    }

    /**
     * Saves tasks to the specified file by appending to it.
     *
     * @param tasks The list of tasks to be saved.
     * @throws FileNotFoundException If the file cannot be found.
     */
    public void saveTasks(List<Task> tasks) throws FileNotFoundException {
        assert tasks != null : "Tasks list should not be null";
        try {
            FileWriter fw = new FileWriter(filePath, true);
            for (Task task : tasks) {
                assert tasks != null : "Tasks list should not be null";
                fw.write(task.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Empties the contents of the specified file.
     *
     * @param filePath The path of the file to be emptied.
     * @throws IOException If an error occurs while emptying the file.
     */
    public static void emptyFile(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(""); // Writing an empty string to truncate the file
        } catch (IOException e) {
            throw new IOException("Error occurred while emptying the file: " + filePath, e);
        }
    }
}
