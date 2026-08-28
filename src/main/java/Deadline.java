import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDate date = LocalDate.parse("1900-01-01");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
    Deadline(String task, LocalDate date) {
        super(task);
        this.tag = "[D]";
        this.date = date;

    }

    public String getDate() {
        return this.date.format(formatter).toString();
    }

    public String[] get() {
        return new String[] {isDone ? "1" : "0", task, this.date.toString()};

    }

    @Override
    public String toString() {
        return this.tag + (this.isDone ? "[X] " : "[ ] ") + task + " (by: " + date.format(formatter).toString() + ")";
    }

}