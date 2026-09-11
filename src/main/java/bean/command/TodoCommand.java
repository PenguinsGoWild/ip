package bean.command;

import bean.exception.InvalidSyntaxException;
import bean.task.BeanList;
import bean.task.Priority;

/** Executes commands that add to-do tasks. */
public class TodoCommand {

    /** Adds a to-do task based on the supplied command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        Priority priority;

        int[] indexes = {-1, -1};

        for (int i = 1; i < words.length; i++) {
            if (words[i].equals("/priority")) {
                indexes[1] = i + 1;
                break;
            }
            indexes[0] = i;
        }

        StringBuilder taskName = CommandText.joinWords(words, 1, indexes[0] + 1);

        CommandValidator.checkEmpty(taskName, input, "todo", 
            "Todo must have a name!\n\n", "Usage: todo \"NAME\"");

        if (indexes[1] != -1) {
            if (words.length - indexes[1] - 1 >= 0) { 
                priority = Priority.fromString(words[indexes[1]]);
                return taskList.addTodo(taskName.toString().trim(), priority);
            }
            throw new InvalidSyntaxException(
                "Uh Oh! Invalid Syntax for adding todo with priority!\n\n"
                + "Usage: todo \"TASK\" /priority \"{HIGH, MEDIUM, LOW}\"" , input);
        }

        return taskList.addTodo(taskName.toString().trim());

    }
}
