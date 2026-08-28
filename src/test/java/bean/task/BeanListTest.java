package bean.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        beanList.addTodoSilent("Read textbook"); // List size is now 1

        // Action & Assertion: Pass boundary-breaking indices and verify the program
        // silently returns instead of crashing with an IndexOutOfBoundsException
        assertDoesNotThrow(() -> beanList.markTask(0)); // Zero index
        assertDoesNotThrow(() -> beanList.markTask(-1)); // Negative index
        assertDoesNotThrow(() -> beanList.markTask(5)); // Out of bounds index
    }
}
