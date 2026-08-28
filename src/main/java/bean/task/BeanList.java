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

    private final ArrayList<Task> tasks;

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
            BeanInteraction.printString("Woah! You tried keying in a wrong date format!\n" +
                    "Try the format yyyy-mm-dd!");
        }

    }

    /**
     * Adds a new Event task at the end of the list.
     * 
     * @param task Task name.
     * @param from From date.
     * @param to   To date.
     */
    public void addEvent(String task, String from, String to) {
        try {
            LocalDate parsedFrom = LocalDate.parse(from);
            LocalDate parsedTo = LocalDate.parse(to);
            tasks.add(new Event(task, parsedFrom, parsedTo));
            printAddTask("[E][ ] " + task + " (from: " + parsedFrom.format(DATE_FORMATTER)
                    + " to: " + parsedTo.format(DATE_FORMATTER) + ")");
        } catch (DateTimeParseException e) {
            BeanInteraction.printString("Woah! You tried keying in a wrong date format!\n" +
                    "Try the format yyyy-mm-dd!");
        }

    }

    /**
     * Adds a new Todo task at the end of the list.
     * 
     * @param task Task name.
     */
    public void addTodo(String task) {
        tasks.add(new Todo(task));
        printAddTask("[T][ ] " + task);

    }

    /**
     * Adds a new Deadline task at the end of the list silently.
     * For memory usage.
     * 
     * @param task Task name.
     * @param date By date.
     */
    public void addDeadlineSilent(String task, String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        tasks.add(new Deadline(task, parsedDate));

    }

    /**
     * Adds a new Event task at the end of the list silently.
     * For memory usage.
     * 
     * @param task Task name.
     * @param from From date.
     * @param to   To date.
     */
    public void addEventSilent(String task, String from, String to) {
        LocalDate parsedFrom = LocalDate.parse(from);
        LocalDate parsedTo = LocalDate.parse(to);
        tasks.add(new Event(task, parsedFrom, parsedTo));

    }

    /**
     * Adds a new Todo task at the end of the list silently.
     * For memory usage.
     * 
     * @param task Task name.
     */
    public void addTodoSilent(String task) {
        tasks.add(new Todo(task));

    }

    /**
     * Marks the task in the list as complete at index i.
     * 
     * @param i Index of task.
     */
    public void markTask(int i) {
        if (i <= 0) {
            return;
        }
        if (i - 1 >= tasks.size()) {
            return;
        }

        Task task = tasks.get(i - 1);
        task.markDone();
    }

    /**
     * Marks the task in the list as incomplete at index i.
     * 
     * @param i Index of task.
     */
    public void unmarkTask(int i) {
        if (i <= 0) {
            return;
        }
        if (i - 1 >= tasks.size()) {
            return;
        }

        Task task = tasks.get(i - 1);
        task.unmarkDone();
    }

    /**
     * Deletes task from the list at index i.
     * 
     * @param i Index of task.
     */
    public void deleteTask(int i) {
        if (i <= 0) {
            return;
        }
        if (i - 1 >= tasks.size()) {
            return;
        }
        BeanInteraction.printString("Alrighty! I've removed the following task:\n\n"
                + tasks.get(i - 1) + "\n\nNow you have " + tasks.size() + " tasks in the list.");
        tasks.remove(i - 1);
    }

    /**
     * Formats tasks into suitable format for writing to memory.
     * 
     * @param i Index i..
     * @return formatted string.
     */
    public String formatTask(int i) {
        Task task = tasks.get(i);
        String[] args = task.get();
        return switch (task.getTag()) {
        case "[T]" -> "0|" + args[0] + "|" + args[1];
        case "[D]" -> "1|" + args[0] + "|" + args[1] + "|" + args[2];
        case "[E]" -> "2|" + args[0] + "|" + args[1] + "|" + args[2] + "|" + args[3];
        default -> "";
        };

    }

    /**
     * Displays all the tasks currently in the list.
     * 
     **/
    public void displayTasks() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (i != 0) {
                sb.append("\n");
            }
            sb.append(i + 1).append(". ").append(task);
        }

        BeanInteraction.printString("Here are the tasks in your list:\n\n" + sb.toString());

    }

    /** 
     * Finds and displays the tasks currently in the list that match the string.
     * 
    **/
    public void findTasks (String regex) {
        Pattern pattern = Pattern.compile(regex);

        StringBuilder sb = new StringBuilder();
        List<Task> newList = ls.stream().filter(x -> pattern.matcher(x.get()[1]).find())
            .toList();

        for (int i = 0; i < newList.size(); i++) {
            Task task = newList.get(i);
            if (i != 0) 
                sb.append("\n");
            sb.append((i+1) + ". " + task.toString());
        }

        BeanInteraction.printString("Here are the tasks in your list:\n\n" + sb.toString());

    }

    /**
     * Prints string s to screen in a formatted way.
     * 
     * @param s Message to be printed
     */
    private void printAddTask(String s) {
        BeanInteraction.printString("Alrighty! I've added the following task:\n\n"
                + s + "\n\nNow you have " + tasks.size() + " tasks in the list.");

    }

}
