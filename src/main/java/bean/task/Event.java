package bean.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Represents a task that occurs between two dates. */
public class Event extends Task {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate from;
    private final LocalDate to;

    Event(String task, LocalDate from, LocalDate to) {
        super(task);
        this.tag = "[E]";
        this.from = from;
        this.to = to;
    }

    public String[] get() {
        return new String[] { isDone ? "1" : "0", task, from.toString(),
                to.toString() };

    }

    @Override
    public String toString() {
        return tag + (isDone ? "[X] " : "[ ] ") + task + " (from: "
                + from.format(DATE_FORMATTER) + " to: " + to.format(DATE_FORMATTER) + ")";
    }

}
