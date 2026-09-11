package bean.task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BeanListTest {

    @Test
    public void addDeadline_validDate_taskAddedSuccessfully() {
        BeanList beanList = new BeanList();

        // Action: Add a deadline with the correct yyyy-mm-dd format
        beanList.addDeadline("Finish CS2103T iP", "2026-08-28");

        // Assertion: The list size should increase to 1
        assertEquals(1, beanList.getSize());
    }

    @Test
    public void addDeadline_invalidDate_catchesExceptionAndIgnoresTask() {
        BeanList beanList = new BeanList();

        // Action: Add a deadline with an incorrect date format
        beanList.addDeadline("Finish CS2103T iP", "28-08-2026");

        // Assertion: The exception should be caught, and the list should remain empty
        assertEquals(0, beanList.getSize());
    }

    @Test
    public void markTask_invalidIndices_doesNotThrowException() {
        BeanList beanList = new BeanList();
        beanList.addTodoSilent("Read textbook", Priority.LOW); // List size is now 1

        // Action & Assertion: Pass boundary-breaking indices and verify the program
        // silently returns instead of crashing with an IndexOutOfBoundsException
        assertDoesNotThrow(() -> beanList.markTask(0)); // Zero index
        assertDoesNotThrow(() -> beanList.markTask(-1)); // Negative index
        assertDoesNotThrow(() -> beanList.markTask(5)); // Out of bounds index
    }

    @Test
    public void taskOperations_todoIsMarkedFoundAndDeleted() {
        BeanList beanList = new BeanList();
        beanList.addTodoSilent("Read textbook", Priority.HIGH);

        assertEquals("0|0|Read textbook", beanList.formatTask(0));
        assertEquals("Here are the tasks in your list:\n\n1. [T][ ] Read textbook",
                beanList.findTasks("textbook"));
        assertEquals("Good Job! I'll mark the task as done!\n\n [T][X] Read textbook",
                beanList.markTask(1));
        assertEquals("Alrighty! I've removed the following task:\n\nRead textbook"
                + "\n\nNow you have 0 tasks in the list.", beanList.deleteTask(1));
        assertEquals(0, beanList.getSize());
    }

    @Test
    public void addEvent_validDates_taskIsStoredWithDates() {
        BeanList beanList = new BeanList();

        beanList.addEvent("Project meeting", "2026-09-01", "2026-09-02");

        assertEquals(1, beanList.getSize());
        assertEquals("[E]", beanList.getTaskTag(1));
        assertEquals("Project meeting", beanList.getTaskName(1));
        assertEquals("2|0|Project meeting|2026-09-01|2026-09-02", beanList.formatTask(0));
    }
}
