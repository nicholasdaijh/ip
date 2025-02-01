package nicholas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nicholas.tasks.TaskList;
import nicholas.tasks.Todo;

public class TaskListTest {

    @Test
    void testAddTask() {
        // Add a ToDo task
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read books"));
        assertEquals(new Todo("read books").toString(), taskList.getTasks().get(0).toString());
    }

    @Test
    void testDeleteTask() {
        TaskList taskList = new TaskList();
        taskList.addTask(new Todo("read books"));
        taskList.addTask(new Todo("borrow books"));
        assertEquals(new Todo("read books").toString(), taskList.getTasks().get(0).toString());
        taskList.deleteTask(0);
        assertNotEquals(new Todo("read books").toString(), taskList.getTasks().get(0).toString());
    }
}
