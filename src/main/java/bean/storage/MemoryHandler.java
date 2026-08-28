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

/** Reads and writes the task list using the application's plain-text memory file. */
public class MemoryHandler {
    private static final String TASK_TODO = "0";
    private static final String TASK_DEADLINE = "1";
    private static final String TASK_EVENT = "2";

    private static final String TASK_MARKED = "1";

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
                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split("\\|");

                try {
                    if (values.length < 3) {
                        throw new InvalidMemoryDataException(line);
                    }
                    if (values[2].isBlank()) {
                        throw new InvalidMemoryDataException(
                                "Warning: Invalid Data! Name cannot be blank!", line);
                    }

                    switch (values[0]) {
                        case TASK_TODO:
                            taskList.addTodoSilent(values[2]);
                            break;
                        case TASK_DEADLINE:
                            if (values.length < 4) {
                                throw new InvalidMemoryDataException(line);
                            }
                            taskList.addDeadlineSilent(values[2], values[3]);
                            break;
                        case TASK_EVENT:
                            if (values.length < 5) {
                                throw new InvalidMemoryDataException(line);
                            }
                            taskList.addEventSilent(values[2], values[3], values[4]);
                            break;
                        default:
                            return;
                    }
                    if (values[1].equals(TASK_MARKED)) {
                        taskList.markTask(taskList.getSize());
                    }
                } catch (InvalidMemoryDataException e) {
                    System.out.println(e.getMessage());
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
