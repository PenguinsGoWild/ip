
import java.util.ArrayList;

public class BeanList {
    private ArrayList<String> ls;
    BeanList () {
        this.ls = new ArrayList<String>();

    }

    public void addTask(String task) {
        this.ls.add(task);
        Bean.printString("added: " + task);

    }

    public void displayTasks() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.ls.size(); i++) {
            if (i != 0)
                sb.append("\n");
            sb.append((i+1) + ". " + this.ls.get(i));
        }

        Bean.printString(sb.toString());

    }
    
}
