package bean.command;

import bean.exception.InvalidSyntaxException;
import bean.task.BeanList;

/** Executes commands that add deadline tasks. */
public class DeadlineCommand {

    /** Adds a deadline task based on the supplied command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        StringBuilder taskName = new StringBuilder();
        StringBuilder date = new StringBuilder();
        int[] indexes = {-1, -1};
        int index = 0;

        for (int i = 1; i < words.length; i++) {
            indexes[index] = i;
            if (words[i].equals("/by")) {
                index = 1;
            }
        }

        if (indexes[0] == -1 || indexes[1] == -1) {
            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for deadline!\n\n"
                    + "Usage: deadline \"TASK\" /by \"DATE\"", input);
        }

        for (int i = 1; i < indexes[0]; i++) {
            taskName.append(words[i]).append(" ");
        }

        for (int i = indexes[0] + 1; i < indexes[1]; i++) {
            date.append(words[i]).append(" ");
        }

        checkEmpty(taskName, input, StringType.NAME);
        checkEmpty(date, input, StringType.BY);

        return taskList.addDeadline(taskName.toString().trim(), date.toString().trim());
    }

    /**
     * Checks if the string is empty and throws an InvalidSyntaxException if it is.
     *
     * @param string String that is being checked.
     * @param input Input of command.
     * @param type Type of syntax to check for.
     */
    private static void checkEmpty(StringBuilder string, String input, StringType type) {
        if (string.isEmpty()) {
            String output = "";
            switch (type) {
                case NAME -> output = "Deadline must have a name!\n\n";
                case BY -> output = "Deadline must have a to date!\n\n";
                default -> output = "";

            }

            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for deadline!\n"
                    + output
                    + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
        }

    }
}
