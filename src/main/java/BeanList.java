
import java.util.ArrayList;

public class BeanList {
    private ArrayList<Task> ls;
    BeanList () {
        this.ls = new ArrayList<Task>();

    }

    public String getTaskName(int i) {
        return this.ls.get(i-1).get();
    }
    public int getSize() {
        return this.ls.size();
    }

    public void addTask(String task) {
        this.ls.add(new Task(task));
        Bean.printString("added: " + task);

    }

    public void markTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;

        Task task = this.ls.get(i-1);
        task.markDone();
        this.ls.set(i-1, task);

    }

    public void unmarkTask(int i) {
        if (i <= 0) return;
        if (i-1>= this.ls.size()) return;

        Task task = this.ls.get(i-1);
        task.unmarkDone();
        this.ls.set(i-1, task);

    }

    public void displayTasks() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.ls.size(); i++) {
            Task task = this.ls.get(i);
            if (i != 0)
                sb.append("\n");
            sb.append((i+1) + ". " + "["+ (task.isDone() ? "X" : " ") + "] " + task.get());
        }

        Bean.printString(sb.toString());

    }
    
}
