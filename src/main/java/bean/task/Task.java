package bean.task;

/** Represents the state and common behaviour of a task. */
class Task {
    protected String task;
    protected String tag = "";
    protected boolean isDone;

    Task(String task) {
        this.task = task;
        this.isDone = false;
    }

    public String getTag() {
        return this.tag;
    }

    /** Marks the task as complete. */
    public void markDone() {
        this.isDone = true;
    }

    /** Marks the task as incomplete. */
    public void unmarkDone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return this.isDone;
    }

    /** Returns the task fields in a format suitable for storage. */
    public String[] get() {
        return new String[] { isDone ? "1" : "0", task };
    }
}
