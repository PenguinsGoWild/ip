package bean.command;

import bean.exception.InvalidSyntaxException;
import bean.task.BeanList;

/** Executes commands that find tasks by name. */
public class FindCommand {

    /** Finds tasks whose names match the supplied command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        StringBuilder taskName = new StringBuilder();
        for (int i = 1; i < words.length; i++) {
            taskName.append(words[i]).append(" ");
        }

        if (taskName.isEmpty()) {
            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for find!\n\n"
                    + "Usage: find \"KEYWORD\"", input);
        }
        return taskList.findTasks(taskName.toString().trim());
    }
}
