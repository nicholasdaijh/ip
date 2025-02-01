package nicholas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nicholas.ui.Parser;
import nicholas.tasks.Deadline;
import nicholas.tasks.Event;
import nicholas.tasks.Task;
import nicholas.tasks.Todo;


public class ParserTest {

    @Test
    void testParseCommand() {
        Parser parser = new Parser();
        String[] commandParts = parser.parseCommand("mark 12");
        assertEquals(commandParts[0], "mark");
        assertEquals(commandParts[1], "12");
    }

    @Test
    void testParseTask() {
        Parser parser = new Parser();
        Task eventTask = parser.parseTask("[E][ ] something (from: Oct 10 2002 1600 to: Oct 11 2002 1600)");
        Task todoTask = parser.parseTask("[T][ ] read books");
        Task deadlineTask = parser.parseTask("[D][ ] assignment (by: Oct 12 2002 1500)");
        assertEquals(new Event("something", "2002-10-10 1600", "2002-10-11 1600").toString(), eventTask.toString());
        assertEquals(new Todo("read books").toString(), todoTask.toString());
        assertEquals(new Deadline("assignment", "2002-10-12 1500").toString(), deadlineTask.toString());
    }


}
