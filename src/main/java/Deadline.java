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

    public String[] get() {
        return new String[] {task, date};

    }

    @Override
    public String toString() {
        return this.tag + (this.isDone ? "[X] " : "[ ] ") + task + " (by: " + date + ")";
    }

}