package bean.command;

import bean.exception.InvalidSyntaxException;
import bean.task.BeanList;
import bean.task.Priority;

/** Executes commands that add deadline tasks. */
public class DeadlineCommand {
    private static final String USAGE = "event \"TASK\" /by \"DATE\"";

    /** Adds a deadline task based on the supplied command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        int[] indexes = {-1, -1, -1};
        int index = 0;

        for (int i = 1; i < words.length; i++) {
            indexes[index] = i;
            if (words[i].equals("/by")) {
                index = 1;
            }
            if (words[i].equals("/priority")) {
                index = 2;
            }
        }

        if (indexes[0] == -1 || indexes[1] == -1) {
            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for deadline!\n\n"
                    + "Usage: deadline \"TASK\" /by \"DATE\"", input);
        }

        if (indexes[2] == -1 && words[indexes[1]].equals("/priority")) {
            throw new InvalidSyntaxException(
                    "Uh Oh! Invalid Syntax for adding deadling with priority!\n\n"
                    + "Usage: deadline \"TASK\" /by \"DATE\" "
                    + "/priority \"{HIGH | MEDIUM | LOW}\"", input);
        }

        StringBuilder taskName = CommandText.joinWords(words, 1, indexes[0]);
        StringBuilder date = CommandText.joinWords(words, indexes[0] + 1,
                indexes[2] > indexes[1] ? indexes[1] : indexes[1] + 1);

        CommandValidator.checkEmpty(taskName, input, "deadline",
                "Deadline must have a name!\n\n", USAGE);
        CommandValidator.checkEmpty(date, input, "deadline",
                "Deadline must have a by date!\n\nTry the format yyyy-mm-dd!\n\n",
                USAGE);

        if (indexes[2] != -1) {
            Priority priority = Priority.fromString(words[indexes[2]]);
            return taskList.addDeadline(taskName.toString().trim(),
                    date.toString().trim(), priority);
        }

        return taskList.addDeadline(taskName.toString().trim(), date.toString().trim());
    }
}
