package bean.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;

import bean.exception.InvalidMemoryDataException;
import bean.task.BeanList;
import bean.task.Priority;

/** Reads and writes the task list using the application's plain-text memory file. */
public class MemoryHandler {
    private static final String TASK_TODO = "0";
    private static final String TASK_DEADLINE = "1";
    private static final String TASK_EVENT = "2";

    private static final String TASK_MARKED = "1";

    private static final String PRIORITY_HIGH = "2";
    private static final String PRIORITY_MEDIUM = "1";
    private static final String PRIORITY_LOW = "0";

    private final String path;

    /** Creates a handler for the file at the given path. */
    public MemoryHandler(String path) {
        this.path = path;
    }

    /** Adds valid tasks from the memory file to the given task list. */
    public void readMemory(BeanList taskList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boolean shouldContinue = processMemoryLine(line, taskList);
                if (!shouldContinue) {
                    return;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No memory found! Creating new memory file!");
            try {
                File file = new File(path);
                if (file.createNewFile()) {
                    System.out.println("File successfully created: " + file.getName());
                }
            } catch (IOException fileCreationException) {
                System.out.println("An error occurred when trying to create memory file!");
                fileCreationException.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Warning: Data format in memory is incorrect! Skipping line!");
        }
    }

    /** Processes one saved-data line and returns whether reading should continue. */
    private boolean processMemoryLine(String line, BeanList taskList) {
        if (line.isBlank()) {
            return true;
        }

        String[] values = line.split("\\|");

        try {
            validateMemoryData(values, line);
            int sizeBeforeAdding = taskList.getSize();
            if (!addTaskFromMemory(values, taskList, line)) {
                return false;
            }
            assert taskList.getSize() == sizeBeforeAdding + 1
                    : "A valid memory record must add exactly one task";
            if (values[1].equals(TASK_MARKED)) {
                assert taskList.getSize() > 0 : "A marked record must have a task to mark";
                taskList.markTask(taskList.getSize());
            }
        } catch (InvalidMemoryDataException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /** Validates the fields shared by all saved task records. */
    private void validateMemoryData(String[] values, String line) {
        if (values.length < 3) {
            throw new InvalidMemoryDataException(line);
        }
        if (values[2].isBlank()) {
            throw new InvalidMemoryDataException(
                    "Warning: Invalid Data! Name cannot be blank!", line);
        }
    }

    private Priority matchPriority(String priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                return Priority.HIGH;
            case PRIORITY_MEDIUM:
                return Priority.MEDIUM;
            case PRIORITY_LOW:
                return Priority.LOW;
            default:
                throw new InvalidMemoryDataException("Warning: Invalid Data Priority!");

        }

    }

    /** Adds a task represented by a saved record and returns whether reading should continue. */
    private boolean addTaskFromMemory(String[] values, BeanList taskList, String line) {
        switch (values[0]) {
            case TASK_TODO:
                assert values.length >= 3 : "A to-do record needs three fields";
                taskList.addTodoSilent(values[3], matchPriority(values[2]));
                break;
            case TASK_DEADLINE:
                if (values.length < 4) {
                    throw new InvalidMemoryDataException(line);
                }
                assert values.length >= 4 : "A deadline record needs four fields";
                taskList.addDeadlineSilent(values[3], values[4], matchPriority(values[2]));
                break;
            case TASK_EVENT:
                if (values.length < 5) {
                    throw new InvalidMemoryDataException(line);
                }
                assert values.length >= 5 : "An event record needs five fields";
                taskList.addEventSilent(values[3], values[4], values[5], matchPriority(values[2]));
                break;
            default:
                return false;
        }
        return true;
    }

    /** Writes all tasks in the given task list to the memory file. */
    public void writeMemory(BeanList taskList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            int size = taskList.getSize();
            for (int i = 0; i < size; i++) {
                writer.println(taskList.formatTask(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
