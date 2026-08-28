public class Event extends Task {
    String from, to;
    Event(String task, String from, String to) {
        super(task);
        this.tag = "[E]";
        this.from = from;
        this.to = to;
    }

    public String[] get() {
        return new String[] {isDone ? "1" : "0", task, from, to};

    }

    @Override
    public String toString() {
        return this.tag + (this.isDone ? "[X] " : "[ ] ") + task + " (from: " + from + " to: " + to + ")";
    }

}