package bean.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Stores and manages the tasks currently known to the application. */
public class BeanList {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final List<Task> tasks;
    private int size = 0;

    /** Creates an empty task list. */
    public BeanList() {
        this.tasks = new ArrayList<>();
    }

    public String getTaskTag(int i) {
        assert i >= 1 && i <= tasks.size() : "Task index must be one-based and valid";
        return tasks.get(i - 1).getTag();
    }

    public String getTaskName(int i) {
        assert i >= 1 && i <= tasks.size() : "Task index must be one-based and valid";
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
            return formatAddTask("[D][ ] " + task + " (by: " + parsedDate.format(DATE_FORMATTER) + ")");
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
            return formatAddTask("[D][ ] " + task + " (by: " + parsedDate.format(DATE_FORMATTER) + ")");
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
            return formatAddTask("[E][ ] " + task + " (from: " + parsedFrom.format(DATE_FORMATTER)
                    + " to: " + parsedTo.format(DATE_FORMATTER) + ")");
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
            return formatAddTask("[E][ ] " + task + " (from: " + parsedFrom.format(DATE_FORMATTER)
                    + " to: " + parsedTo.format(DATE_FORMATTER) + ")");
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
        return formatAddTask("[T][ ] " + task);
    }

    /**
     * Adds a new to-do task sorted by priority to the list.
     */
    public String addTodo(String task, Priority priority) {
        tasks.add(new Todo(task, priority, size + 1));
        size += 1;
        return formatAddTask("[T][ ] " + task);
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

        assert index >= 1 && index <= tasks.size() : "Task index must be one-based and valid";
        tasks.get(index - 1).markDone();

        return "Good Job! I'll mark the task as done!\n\n"
            + " " + getTaskTag(index) + "[X] " + getTaskName(index);
    }

    /**
     * Marks the task at the given one-based index as incomplete.
     */
    public String unmarkTask(int index) {

        assert index >= 1 && index <= tasks.size() : "Task index must be one-based and valid";
        tasks.get(index - 1).unmarkDone();
        return "Awww, Okay! I'll unmark it!\n\n"
                + " " + getTaskTag(index) + "[ ] " + getTaskName(index);
    }

    /**
     * Deletes the task at the given one-based index.
     */
    public String deleteTask(int index) {

        assert index >= 1 && index <= tasks.size() : "Task index must be one-based and valid";
        String removedTask = tasks.remove(index - 1).get()[1];

        size -= 1;
        return "Alrighty! I've removed the following task:\n\n"
                + removedTask + "\n\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns the task at the zero-based index in a format suitable for storage.
     */
    public String formatTask(int index) {
        assert index >= 0 && index < tasks.size() : "Storage index must be zero-based and valid";
        Task task = tasks.get(index);
        String[] taskData = task.get();
        String tag = task.getTag();
        assert tag.equals("[T]") || tag.equals("[D]") || tag.equals("[E]")
                : "Every task must have a recognized storage tag";
        return switch (tag) {
            case "[T]" -> {
                assert taskData.length == 2 : "A to-do task must have two storage fields";
                yield "0|" + taskData[0] + "|" + taskData[2] + "|" + taskData[1] + "|";
            }
            case "[D]" -> {
                assert taskData.length == 3 : "A deadline task must have three storage fields";
                yield "1|" + taskData[0] + "|" + taskData[3] + "|" + taskData[1] + "|" + taskData[2];
            }
            case "[E]" -> {
                assert taskData.length == 4 : "An event task must have four storage fields";
                yield "2|" + taskData[0] + "|" + taskData[4] + "|" + taskData[1] + "|" + taskData[2]
                        + "|" + taskData[3];
            }
            default -> "";
        };
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

        List<Task> matchingTasks = tasks.stream()
                .filter(task -> pattern.matcher(task.get()[1]).find())
                .toList();

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
