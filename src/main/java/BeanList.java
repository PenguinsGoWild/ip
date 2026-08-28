
import java.util.ArrayList;

public class BeanList {
    private ArrayList<Task> ls;

    BeanList () {
        this.ls = new ArrayList<Task>();

    }

    public String getTaskTag(int i) {
        return this.ls.get(i-1).getTag();
    }

    public String getTaskName(int i) {
        return this.ls.get(i-1).get()[1];
    }

    public int getSize() {
        return this.ls.size();
    }

    /**
     * Adds a new Deadline task at the end of the list.
     * 
     * @param task Task name.
     * @param date By date.
     */
    public void addDeadline(String task, String date) {
        this.ls.add(new Deadline(task, date));
        printAddTask("[D][ ] " + task + " (by: " + date + ")");

    }

    /**
     * Adds a new Event task at the end of the list.
     * 
     * @param task Task name.
     * @param from From date.
     * @param to To date.
     */
    public void addEvent(String task, String from, String to) {
        this.ls.add(new Event(task, from, to));
        printAddTask("[E][ ] " + task + " (from: " + from + " to: " + to + ")");

    }

    /**
     * Adds a new Todo task at the end of the list.
     * 
     * @param task Task name.
     */
    public void addTodo(String task) {
        this.ls.add(new Todo(task));
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
        this.ls.add(new Deadline(task, date));

    }

    /**
     * Adds a new Event task at the end of the list silently.
     * For memory usage.
     * 
     * @param task Task name.
     * @param from From date.
     * @param to To date.
     */
    public void addEventSilent(String task, String from, String to) {
        this.ls.add(new Event(task, from, to));

    }

    /**
     * Adds a new Todo task at the end of the list silently.
     * For memory usage.
     * 
     * @param task Task name.
     */
    public void addTodoSilent(String task) {
        this.ls.add(new Todo(task));

    }

    /**
     * Marks the task in the list as complete at index i.
     * 
     * @param i Index of task.
     */
    public void markTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;

        Task task = this.ls.get(i-1);
        task.markDone();
        this.ls.set(i-1, task);

    }

    /**
     * Marks the task in the list as incomplete at index i.
     * 
     * @param i Index of task.
     */
    public void unmarkTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;

        Task task = this.ls.get(i-1);
        task.unmarkDone();
        this.ls.set(i-1, task);

    }


    /**
     * Deletes task from the list at index i.
     * 
     * @param i Index of task.
     */
    public void deleteTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;
        Bean.printString("Alrighty! I've removed the following task:\n\n"
            + ls.get(i-1).toString() + "\n\n" +
            "Now you have " + this.ls.size() + " tasks in the list."
        );
        this.ls.remove(i-1);
    }

    /**
     * Formats tasks into suitable format for writing to memory.
     * 
     * @param i Index i..
     * @return formatted string.
     */
    public String formatTask(int i) {
        Task task = this.ls.get(i);
        String[] args = task.get();
        switch (task.getTag()) {
            case "[T]":
                return "0" + "|" + args[0] + "|" + args[1];
            case "[D]":
                return "1" + "|" + args[0] + "|" + args[1] + "|" + args[2];
            case "[E]":
                return "2" + "|" + args[0] + "|" + args[1] + "|" + args[2] + "|" + args[3];

        }
        return "";

    }

    /** 
     * Displays all the tasks currently in the list.
     * 
    **/
    public void displayTasks() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.ls.size(); i++) {
            Task task = this.ls.get(i);
            if (i != 0)
                sb.append("\n");
            sb.append((i+1) + ". " + task.toString());
        }

        Bean.printString("Here are the tasks in your list:\n\n" + sb.toString());

    }

    /**
     * Prints string s to screen in a formatted way.
     * 
     * @param s Message to be printed
     */
    private void printAddTask(String s) {
        Bean.printString("Alrighty! I've added the following task:\n\n"
            + s + "\n\n" +
            "Now you have " + this.ls.size() + " tasks in the list."
        );

    }
    
}
