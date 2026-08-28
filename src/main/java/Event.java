import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    LocalDate from, to;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

    Event(String task, LocalDate from, LocalDate to) {
        super(task);
        this.tag = "[E]";
        this.from = from;
        this.to = to;
    }

    public String[] get() {
        return new String[] {isDone ? "1" : "0", task, from.toString(),
            to.toString()};

    }

    @Override
    public String toString() {
        return this.tag + (this.isDone ? "[X] " : "[ ] ") + task + " (from: "
            + from.format(formatter).toString() + " to: " + to.format(formatter).toString() + ")";
    }

}