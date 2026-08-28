package bean.command;

import bean.Bean;
import bean.exception.BeanListOutOfBoundsException;
import bean.exception.InvalidSyntaxException;
import bean.exception.UnknownCommandException;
import bean.task.BeanList;
import bean.ui.BeanInteraction;
/** Parses and executes commands entered by the user. */
public class CommandParser {

    /** Executes the command represented by the given input. */
    public static void getCommand(String input, BeanList taskList) {
        String[] words = input.split(" ");
        StringBuilder taskName = new StringBuilder();
        int index = -1;
        switch (Commands.match(words[0])) {
            case EXIT:
                BeanInteraction.outro();
                Bean.terminate();
                break;
            case LIST:
                taskList.displayTasks();
                break;
            case MARK:
                if (words.length == 1) {
                    break;
                }
                index = Integer.parseInt(words[1]);
                if (index <= 0 || index - 1 >= taskList.getSize()) {
                    throw new BeanListOutOfBoundsException(
                            "Oops, you've keyed in an invalid Task index! (" + index + " of "
                                    + taskList.getSize() + ")",
                            index);
                }
                BeanInteraction.printString("Good Job! I'll mark the task as done!\n\n"
                        + " " + taskList.getTaskTag(index) + "[X] " + taskList.getTaskName(index));

                taskList.markTask(index);
                break;
            case UNMARK:
                if (words.length == 1) {
                    break;
                }
                index = Integer.parseInt(words[1]);
                if (index <= 0 || index - 1 >= taskList.getSize()) {
                    throw new BeanListOutOfBoundsException(
                            "Oops, you've keyed in an invalid Task index! (" + index + " of "
                                    + taskList.getSize() + ")",
                            index);
                }
                BeanInteraction.printString("Awww, Okay! I'll unmark it!\n\n"
                        + " " + taskList.getTaskTag(index) + "[ ] " + taskList.getTaskName(index));
                taskList.unmarkTask(index);
                break;
            case TODO:
                for (int i = 1; i < words.length; i++) {
                    taskName.append(words[i]).append(" ");
                }
                if (taskName.isEmpty()) {
                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for todo!\n\n"
                            + "Usage: todo \"TASK\"", input);
                }
                taskList.addTodo(taskName.toString().trim());
                break;
            case EVENT:
                StringBuilder from = new StringBuilder();
                StringBuilder to = new StringBuilder();
                for (int i = 1; i < words.length; i++) {
                    if (words[i].equals("/from")) {
                        if (i + 1 >= words.length) {
                            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n\n"
                                    + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
                        }
                        for (int j = i + 1; j < words.length; j++) {
                            if (words[j].equals("/to")) {
                                if (j + 1 >= words.length) {
                                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n\n"
                                            + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
                                }
                                for (int k = j + 1; k < words.length; k++) {
                                    to.append(words[k]).append(" ");
                                }
                                break;
                            }
                            from.append(words[j]).append(" ");
                        }
                        break;
                    }
                    taskName.append(words[i]).append(" ");
                }
                if (taskName.isEmpty()) {
                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n"
                            + "Event must have a name!\n\n"
                            + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
                }
                if (from.isEmpty()) {
                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n"
                            + "Event must have a from date!\n\n"
                            + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
                }
                if (to.isEmpty()) {
                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n"
                            + "Event must have a to date!\n\n"
                            + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
                }

                taskList.addEvent(taskName.toString().trim(), from.toString().trim(), to.toString().trim());
                break;
            case DEADLINE:
                StringBuilder date = new StringBuilder();
                for (int i = 1; i < words.length; i++) {
                    if (words[i].equals("/by")) {
                        if (i + 1 >= words.length) {
                            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for deadline!\n\n"
                                    + "Usage: deadline \"TASK\" /by \"DATE\"", input);
                        }
                        for (int j = i + 1; j < words.length; j++) {
                            date.append(words[j]).append(" ");
                        }
                        break;
                    }
                    taskName.append(words[i]).append(" ");
                }
                if (taskName.isEmpty()) {
                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for deadline!\n"
                            + "Deadline must have a name!\n\n"
                            + "Usage: deadline \"TASK\" /by \"DATE\"", input);
                }
                if (date.isEmpty()) {
                    throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for deadline!\n"
                            + "Deadline must have by date!\n\n"
                            + "Usage: deadline \"TASK\" /by \"DATE\"", input);
                }
                taskList.addDeadline(taskName.toString().trim(), date.toString().trim());
                break;
            case DELETE:
                if (words.length == 1) {
                    break;
                }
                index = Integer.parseInt(words[1]);
                if (index <= 0 || index - 1 >= taskList.getSize()) {
                    throw new BeanListOutOfBoundsException(
                            "Oops, you've keyed in an invalid Task index! (" + index + " of "
                                    + taskList.getSize() + ")",
                            index);
                }
                taskList.deleteTask(index);
                break;
            default:
                throw new UnknownCommandException("Sorry, I don't know what you mean. :<", input);

        }
    }
}
