package bean.command;

import bean.task.BeanList;

public class ListCommand {

    public static String execute (BeanList taskList) {
        return taskList.displayTasks();

    }
    
}
