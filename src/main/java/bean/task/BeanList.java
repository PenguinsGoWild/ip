package bean.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Stores and manages the tasks currently known to the application. */
public class BeanList {
    private final List<Task> tasks;
    private int size = 0;

    /** Creates an empty task list. */
    public BeanList() {
        this.tasks = new ArrayList<>();
    }

    public String getTaskTag(int i) {
        validateTaskIndex(i);
        return tasks.get(i - 1).getTag();
    }

    public String getTaskName(int i) {
        validateTaskIndex(i);
        return tasks.get(i - 1).get()[1];
    }

    public int getSize() {
        return tasks.size();
    }

    /**
     * Adds a deadline task at the end of the list.
     */
    public String addDeadline(String task, String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            tasks.add(new Deadline(task, parsedDate, size + 1));
            size += 1;
            return formatAddTask(tasks.get(size - 1).toString());
        } catch (DateTimeParseException e) {
            return "Woah! You tried keying in a wrong date format!\n"
                    + "Try the format yyyy-mm-dd!";
        }
    }

    /**
     * Adds a deadline task sorted by priority to the list.
     */
    public String addDeadline(String task, String date, Priority priority) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            tasks.add(new Deadline(task, parsedDate, priority, size + 1));
            size += 1;
            return formatAddTask(tasks.get(size - 1).toString());
        } catch (DateTimeParseException e) {
            return "Woah! You tried keying in a wrong date format!\n"
                    + "Try the format yyyy-mm-dd!";
        }
    }

    /**
     * Adds a new event task at the end of the list.
     */
    public String addEvent(String task, String from, String to) {
        try {
            LocalDate parsedFrom = LocalDate.parse(from);
            LocalDate parsedTo = LocalDate.parse(to);
            tasks.add(new Event(task, parsedFrom, parsedTo, size + 1));
            size += 1;
            return formatAddTask(tasks.get(size - 1).toString());
        } catch (DateTimeParseException e) {
            return "Woah! You tried keying in a wrong date format!\n"
                    + "Try the format yyyy-mm-dd!";
        }
    }

    /**
     * Adds a new event task sorted by priority to the list.
     */
    public String addEvent(String task, String from, String to, Priority priority) {
        try {
            LocalDate parsedFrom = LocalDate.parse(from);
            LocalDate parsedTo = LocalDate.parse(to);
            tasks.add(new Event(task, parsedFrom, parsedTo, priority, size + 1));
            size += 1;
            return formatAddTask(tasks.get(size - 1).toString());
        } catch (DateTimeParseException e) {
            return "Woah! You tried keying in a wrong date format!\n"
                    + "Try the format yyyy-mm-dd!";
        }
    }

    /**
     * Adds a new to-do task at the end of the list.
     */
    public String addTodo(String task) {
        tasks.add(new Todo(task, size + 1));
        size += 1;
        return formatAddTask(tasks.get(size - 1).toString());
    }

    /**
     * Adds a new to-do task sorted by priority to the list.
     */
    public String addTodo(String task, Priority priority) {
        tasks.add(new Todo(task, priority, size + 1));
        size += 1;
        return formatAddTask(tasks.get(size - 1).toString());
    }

    /**
     * Adds a deadline task silently when loading saved data.
     */
    public void addDeadlineSilent(String task, String date, Priority priority) {
        LocalDate parsedDate = LocalDate.parse(date);
        tasks.add(new Deadline(task, parsedDate, priority, size + 1));
        size += 1;
    }

    /**
     * Adds an event task sorted by priority
     * silently when loading saved data.
     */
    public void addEventSilent(String task, String from, String to, Priority priority) {
        LocalDate parsedFrom = LocalDate.parse(from);
        LocalDate parsedTo = LocalDate.parse(to);
        tasks.add(new Event(task, parsedFrom, parsedTo, priority, size + 1));
        size += 1;
    }

    /**
     * Adds a to-do task silently when loading saved data.
     */
    public void addTodoSilent(String task, Priority priority) {
        tasks.add(new Todo(task, priority, size + 1));
        size += 1;
    }

    /**
     * Marks the task at the given one-based index as complete.
     */
    public String markTask(int index) {
        if (index <= 0 || index > tasks.size()) {
            return "";
        }

        tasks.get(index - 1).markDone();

        return "Good Job! I'll mark the task as done!\n\n"
            + " " + getTaskTag(index) + "[X] " + getTaskName(index);
    }

    /**
     * Marks the task at the given one-based index as incomplete.
     */
    public String unmarkTask(int index) {

        validateTaskIndex(index);
        tasks.get(index - 1).unmarkDone();
        return "Awww, Okay! I'll unmark it!\n\n"
                + " " + getTaskTag(index) + "[ ] " + getTaskName(index);
    }

    /**
     * Deletes the task at the given one-based index.
     */
    public String deleteTask(int index) {

        validateTaskIndex(index);
        Task removedTask = tasks.remove(index - 1);

        size -= 1;
        return "Alrighty! I've removed the following task:\n\n"
                + removedTask + "\n\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns the task at the zero-based index in a format suitable for storage.
     */
    public String formatTask(int index) {
        validateStorageIndex(index);
        Task task = tasks.get(index);
        String[] taskData = task.get();
        String tag = task.getTag();
        return switch (tag) {
            case "[T]" -> {
                validateTaskDataLength(taskData, 3, "A to-do task must have three storage fields");
                yield "0|" + taskData[0] + "|" + taskData[2] + "|" + taskData[1] + "|";
            }
            case "[D]" -> {
                validateTaskDataLength(taskData, 4, "A deadline task must have four storage fields");
                yield "1|" + taskData[0] + "|" + taskData[3] + "|" + taskData[1] + "|" + taskData[2];
            }
            case "[E]" -> {
                validateTaskDataLength(taskData, 5, "An event task must have five storage fields");
                yield "2|" + taskData[0] + "|" + taskData[4] + "|" + taskData[1] + "|" + taskData[2]
                        + "|" + taskData[3];
            }
            default -> throw new IllegalStateException("Every task must have a recognized storage tag");
        };
    }

    private void validateTaskIndex(int index) {
        if (index < 1 || index > tasks.size()) {
            throw new IllegalArgumentException("Task index must be one-based and valid: " + index);
        }
    }

    private void validateStorageIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IllegalArgumentException("Storage index must be zero-based and valid: " + index);
        }
    }

    private void validateTaskDataLength(String[] taskData, int expectedLength, String message) {
        if (taskData.length != expectedLength) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Displays all tasks currently in the list.
     */
    public String displayTasks() {
        return "Here are the tasks in your list:\n\n"
            + formatTaskDisplay(tasks);

    }

    /** Finds and displays tasks whose descriptions match the given regular expression. */
    public String findTasks(String searchExpression) {
        Pattern pattern = Pattern.compile(searchExpression);

        List<Task> matchingTasks = new ArrayList<>(tasks.stream()
                .filter(task -> pattern.matcher(task.get()[1]).find())
                .toList());

        return "Here are the tasks in your list:\n\n" + formatTaskDisplay(matchingTasks);
    }

    /**
     * Returns a formatted confirmation message with the taskDescription.
     */
    private String formatAddTask(String taskDescription) {
        return "Alrighty! I've added the following task:\n\n"
                + taskDescription + "\n\nNow you have " + size + " tasks in the list.";
    }

    private void updateTaskCachedPositions(List<Task> taskList) {
        for (int i = 0; i < size; i++) {
            Task task = taskList.get(i);
            if (task.getPosition() != i) {
                task.setPosition(i);
            }
        }

    }

    private void sortTaskPriorities(List<Task> taskList) {
        updateTaskCachedPositions(taskList);
        taskList.sort(Comparator.comparingInt((Task task) -> -task.getPriority().getLevel())
            .thenComparing(Comparator.comparingInt((Task task) -> task.getPosition())));

    }

    private String formatTaskDisplay(List<Task> taskList) {
        sortTaskPriorities(taskList);
        return IntStream.range(0, taskList.size())
            .mapToObj(index -> index + 1 + ". " + taskList.get(index))
            .collect(Collectors.joining("\n"));
    }
}
