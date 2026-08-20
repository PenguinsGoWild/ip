
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
        return this.ls.get(i-1).get();
    }

    public int getSize() {
        return this.ls.size();
    }

    public void addDeadline(String task, String date) {
        this.ls.add(new Deadline(task, date));
        printAddTask("[D][ ] " + task + " (by: " + date + ")");

    }
    public void addEvent(String task, String from, String to) {
        this.ls.add(new Event(task, from, to));
        printAddTask("[E][ ] " + task + " (from: " + from + " to: " + to + ")");

    }

    public void addTodo(String task) {
        this.ls.add(new Todo(task));
        Bean.printString("added: " + task);

    }

    public void markTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;

        Task task = this.ls.get(i-1);
        task.markDone();
        this.ls.set(i-1, task);

    }

    public void unmarkTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;

        Task task = this.ls.get(i-1);
        task.unmarkDone();
        this.ls.set(i-1, task);

    }

    public void displayTasks() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.ls.size(); i++) {
            Task task = this.ls.get(i);
            if (i != 0)
                sb.append("\n");
            sb.append((i+1) + ". " + task.getTag() + "["+ (task.isDone() ? "X" : " ") + "] " + task.get());
        }

        Bean.printString("Here are the tasks in your list:\n\n" + sb.toString());

    }

    private void printAddTask(String s) {
        Bean.printString("Alrighty! I've added the following task:\n\n"
            + s + "\n\n" +
            "Now you have " + this.ls.size() + " tasks in the list."
        );

    }
    
}
