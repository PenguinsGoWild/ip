public class Deadline extends Task {
    String date = "";
    Deadline(String task, String date) {
        super(task);
        this.tag = "[D]";
        this.date = date;

    }

    public String getDate() {
        return this.date;
    }

    public String get() {
        return task + " (by: " + date + ")";

    }

    @Override
    public String toString() {
        return this.tag + (this.done ? "[X] " : "[ ] ") + task + " (by: " + date + ")";
    }

}