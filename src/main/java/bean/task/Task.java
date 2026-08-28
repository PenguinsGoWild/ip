package bean.task;
class Task {
    String task;
    String tag = "";
    boolean isDone;
    Task(String task) {
        this.task = task;
        isDone = false;

    }
    public String getTag() {
        return this.tag;
    }

    /**
     * Marks task as complete.
     * 
     */
    public void markDone() {
        this.isDone = true;

    }
    
    /**
     * Marks task as incomplete.
     * 
     */
    public void unmarkDone() {
        this.isDone = false;

    }

    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns task as string.
     * 
     */
    public String[] get() {
        return new String[] {isDone ? "1" : "0", this.task};
    }

}