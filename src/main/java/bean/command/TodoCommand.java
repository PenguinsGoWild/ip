package bean.command;

import bean.task.BeanList;
import bean.exception.InvalidSyntaxException;

public class TodoCommand {

    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        StringBuilder taskName = new StringBuilder();
        for (int i = 1; i < words.length; i++) {
            taskName.append(words[i]).append(" ");
        }
        if (taskName.isEmpty()) {
            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for todo!\n\n"
                + "Usage: todo \"TASK\"", input);
        }
        return taskList.addTodo(taskName.toString().trim());
    }
    
}
