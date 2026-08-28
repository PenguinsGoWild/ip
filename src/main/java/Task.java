class Task {
    String task;
    String tag = "";
    boolean isDone;
    Task(String task) {
        this.task = task;
        done = false;

    }
    public String getTag() {
        return this.tag;
    }

    public void markDone() {
        this.isDone = true;

    }
    
    public void unmarkDone() {
        this.isDone = false;

    }

    public boolean isDone() {
        return this.isDone;
    }

    public String get() {
        return this.task;
    }

}