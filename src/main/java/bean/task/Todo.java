package bean.task;

/** Represents a task without a date or time constraint. */
public class Todo extends Task {

    Todo(String task) {
        super(task);
        this.tag = "[T]";
    }

    @Override
    public String toString() {
        return tag + (isDone ? "[X] " : "[ ] ") + task;
    }
}
