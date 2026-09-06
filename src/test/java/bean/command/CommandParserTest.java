package bean.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bean.exception.UnknownCommandException;
import bean.task.BeanList;

public class CommandParserTest {

    @Test
    public void getCommand_todoCommand_addsTask() {
        BeanList taskList = new BeanList();

        String response = CommandParser.getCommand("todo read textbook", taskList);

        assertEquals(1, taskList.getSize());
        assertEquals("[T]", taskList.getTaskTag(1));
        assertEquals("read textbook", taskList.getTaskName(1));
        assertEquals("Alrighty! I've added the following task:\n\n[T][ ] read textbook"
                + "\n\nNow you have 1 tasks in the list.", response);
    }

    @Test
    public void getCommand_unknownCommand_throwsExceptionWithInput() {
        BeanList taskList = new BeanList();

        UnknownCommandException exception = assertThrows(UnknownCommandException.class, () ->
                CommandParser.getCommand("unknown command", taskList));

        assertEquals("unknown command", exception.getInvalidCommand());
    }

    @Test
    public void match_commandAndAlias_returnsMatchingCommand() {
        assertEquals(Commands.TODO, Commands.match("todo"));
        assertEquals(Commands.TODO, Commands.match("TD"));
        assertEquals(Commands.DEADLINE, Commands.match("dln"));
        assertEquals(Commands.NONE, Commands.match("unsupported"));
    }
}
