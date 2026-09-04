package bean.command;

import bean.task.BeanList;
import bean.exception.BeanListOutOfBoundsException;

public class DeleteCommand {
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
