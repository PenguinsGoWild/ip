package bean.command;

import bean.exception.InvalidSyntaxException;
import bean.task.BeanList;

/** Executes commands that add to-do tasks. */
public class TodoCommand {

    /** Adds a to-do task based on the supplied command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        StringBuilder taskName = CommandText.joinWords(words, 1, words.length);
        if (taskName.isEmpty()) {
            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for todo!\n\n"
                + "Usage: todo \"TASK\"", input);
        }
        return taskList.addTodo(taskName.toString().trim());
    }
}
