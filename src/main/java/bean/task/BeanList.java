package bean.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import bean.ui.BeanInteraction;

/** Stores and manages the tasks currently known to the application. */
public class BeanList {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final List<Task> tasks;

    /** Creates an empty task list. */
    public BeanList() {
        this.tasks = new ArrayList<>();
    }

    public String getTaskTag(int i) {
        return tasks.get(i - 1).getTag();
    }

    public String getTaskName(int i) {
        return tasks.get(i - 1).get()[1];
    }

    public int getSize() {
        return tasks.size();
    }

    /**
     * Adds a deadline task at the end of the list.
     */
    public void addDeadline(String task, String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            tasks.add(new Deadline(task, parsedDate));
            printAddTask("[D][ ] " + task + " (by: " + parsedDate.format(DATE_FORMATTER) + ")");
        } catch (DateTimeParseException e) {
            BeanInteraction.printString("Woah! You tried keying in a wrong date format!\n"
                    + "Try the format yyyy-mm-dd!");
        }
    }

    /**
     * Adds a new event task at the end of the list.
     */
    public void addEvent(String task, String from, String to) {
        try {
            LocalDate parsedFrom = LocalDate.parse(from);
            LocalDate parsedTo = LocalDate.parse(to);
            tasks.add(new Event(task, parsedFrom, parsedTo));
            printAddTask("[E][ ] " + task + " (from: " + parsedFrom.format(DATE_FORMATTER)
                    + " to: " + parsedTo.format(DATE_FORMATTER) + ")");
        } catch (DateTimeParseException e) {
            BeanInteraction.printString("Woah! You tried keying in a wrong date format!\n"
                    + "Try the format yyyy-mm-dd!");
        }
    }

    /**
     * Adds a new to-do task at the end of the list.
     */
    public void addTodo(String task) {
        tasks.add(new Todo(task));
        printAddTask("[T][ ] " + task);
    }

    /**
     * Adds a deadline task silently when loading saved data.
     */
    public void addDeadlineSilent(String task, String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        tasks.add(new Deadline(task, parsedDate));
    }

    /**
     * Adds an event task silently when loading saved data.
     */
    public void addEventSilent(String task, String from, String to) {
        LocalDate parsedFrom = LocalDate.parse(from);
        LocalDate parsedTo = LocalDate.parse(to);
        tasks.add(new Event(task, parsedFrom, parsedTo));
    }

    /**
     * Adds a to-do task silently when loading saved data.
     */
    public void addTodoSilent(String task) {
        tasks.add(new Todo(task));
    }

    /**
     * Marks the task at the given one-based index as complete.
     */
    public void markTask(int index) {
        if (!isValidIndex(index)) {
            return;
        }

        tasks.get(index - 1).markDone();
    }

    /**
     * Marks the task at the given one-based index as incomplete.
     */
    public void unmarkTask(int index) {
        if (!isValidIndex(index)) {
            return;
        }

        tasks.get(index - 1).unmarkDone();
    }

    /**
     * Deletes the task at the given one-based index.
     */
    public void deleteTask(int index) {
        if (!isValidIndex(index)) {
            return;
        }
        BeanInteraction.printString("Alrighty! I've removed the following task:\n\n"
                + tasks.get(index - 1) + "\n\nNow you have " + tasks.size() + " tasks in the list.");
        tasks.remove(index - 1);
    }

    /**
     * Returns the task at the zero-based index in a format suitable for storage.
     */
    public String formatTask(int index) {
        Task task = tasks.get(index);
        String[] taskData = task.get();
        return switch (task.getTag()) {
            case "[T]" -> "0|" + taskData[0] + "|" + taskData[1];
            case "[D]" -> "1|" + taskData[0] + "|" + taskData[1] + "|" + taskData[2];
            case "[E]" -> "2|" + taskData[0] + "|" + taskData[1] + "|" + taskData[2]
                    + "|" + taskData[3];
            default -> "";
        };
    }

    /**
     * Displays all tasks currently in the list.
     */
    public void displayTasks() {
        StringBuilder taskDisplay = new StringBuilder();

        for (int index = 0; index < tasks.size(); index++) {
            if (index != 0) {
                taskDisplay.append("\n");
            }
            taskDisplay.append(index + 1).append(". ").append(tasks.get(index));
        }

        BeanInteraction.printString("Here are the tasks in your list:\n\n" + taskDisplay);
    }

    /** Finds and displays tasks whose descriptions match the given regular expression. */
    public void findTasks(String searchExpression) {
        Pattern pattern = Pattern.compile(searchExpression);

        StringBuilder taskDisplay = new StringBuilder();
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> pattern.matcher(task.get()[1]).find())
                .toList();

        for (int index = 0; index < matchingTasks.size(); index++) {
            if (index != 0) {
                taskDisplay.append("\n");
            }
            taskDisplay.append(index + 1).append(". ").append(matchingTasks.get(index));
        }

        BeanInteraction.printString("Here are the tasks in your list:\n\n" + taskDisplay);
    }

    private boolean isValidIndex(int index) {
        return index > 0 && index <= tasks.size();
    }

    /**
     * Prints the formatted confirmation after adding a task.
     */
    private void printAddTask(String taskDescription) {
        BeanInteraction.printString("Alrighty! I've added the following task:\n\n"
                + taskDescription + "\n\nNow you have " + tasks.size() + " tasks in the list.");
    }
}
