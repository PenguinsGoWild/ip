public class Event extends Task {
    String from, to;
    Event(String task, String from, String to) {
        super(task);
        this.tag = "[E]";
        this.from = from;
        this.to = to;
    }

    public String get() {
        return task + " (from: " + from + " to: " + to + ")";

    }

    @Override
    public String toString() {
        return this.tag + (this.done ? "[X] " : "[ ] ") + task + " (from: " + from + " to: " + to + ")";
    }

}