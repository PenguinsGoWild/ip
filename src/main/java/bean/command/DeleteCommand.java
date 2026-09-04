package bean.command;

import bean.exception.BeanListOutOfBoundsException;
import bean.task.BeanList;

/** Executes commands that delete tasks. */
public class DeleteCommand {

    /** Deletes the task at the index supplied in the command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        int index = -1;
        if (words.length == 1) {
            return "";
        }

        index = Integer.parseInt(words[1]);
        if (index <= 0 || index - 1 >= taskList.getSize()) {
            throw new BeanListOutOfBoundsException(
                    "Oops, you've keyed in an invalid Task index! (" + index + " of "
                            + taskList.getSize() + ")",
                                index);
        }

        return taskList.deleteTask(index);
    }
}
