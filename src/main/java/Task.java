class Task {
    String task;
    String tag = "";
    boolean done;
    Task(String task) {
        this.task = task;
        done = false;

    }
    public String getTag() {
        return this.tag;
    }

    public void markDone() {
        this.done = true;

    }
    
    public void unmarkDone() {
        this.done = false;

    }

    public boolean isDone() {
        return this.done;
    }

    public String get() {
        return this.task;
    }

}