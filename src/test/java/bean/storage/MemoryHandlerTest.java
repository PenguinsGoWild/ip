package bean.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import bean.task.BeanList;

public class MemoryHandlerTest {

    @TempDir
    Path temporaryDirectory;

    @Test
    public void readMemory_invalidDate_skipsLineAndLoadsFollowingTasks() throws IOException {
        Path memoryFile = temporaryDirectory.resolve("memory.txt");
        Files.writeString(memoryFile, "1|0|0|Invalid date|not-a-date\n"
                + "0|0|2|Valid task|\n");
        BeanList taskList = new BeanList();

        new MemoryHandler(memoryFile.toString()).readMemory(taskList);

        assertEquals(1, taskList.getSize());
        assertEquals("Valid task", taskList.getTaskName(1));
    }

    @Test
    public void readMemory_unknownType_skipsLineAndLoadsFollowingTasks() throws IOException {
        Path memoryFile = temporaryDirectory.resolve("memory.txt");
        Files.writeString(memoryFile, "9|0|0|Unknown task|\n"
                + "0|0|0|Valid task|\n");
        BeanList taskList = new BeanList();

        new MemoryHandler(memoryFile.toString()).readMemory(taskList);

        assertEquals(1, taskList.getSize());
        assertEquals("Valid task", taskList.getTaskName(1));
    }
}
