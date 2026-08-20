class Task {
    String task;
    boolean done;
    Task(String task) {
        this.task = task;
        done = false;

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