package bean.task;

public class Todo extends Task {

    Todo(String task) {
        super(task);
        this.tag = "[T]";
    }

    @Override
    public String toString() {
        return this.tag + (this.isDone ? "[X] " : "[ ] ") + task;
    }

}
