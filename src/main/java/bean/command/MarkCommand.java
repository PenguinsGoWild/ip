package bean.command;

import bean.task.BeanList;

/** Executes commands that mark tasks as complete. */
public class MarkCommand {

    /** Marks the task at the index supplied in the command input. */
    public static String execute(String input, BeanList taskList) {
        int index = TaskIndexParser.parse(input, taskList.getSize());
        if (index == -1) {
            return "";
        }

        return taskList.markTask(index);
    }
}
