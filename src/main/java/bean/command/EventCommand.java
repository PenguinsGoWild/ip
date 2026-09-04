package bean.command;

import bean.task.BeanList;
import bean.exception.InvalidSyntaxException;

public class EventCommand {

    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        StringBuilder taskName = new StringBuilder();
        StringBuilder from = new StringBuilder();
        StringBuilder to = new StringBuilder();
        int[] indexes = {-1,-1,-1};
        int index = 0;

        for (int i = 1; i < words.length; i++) {
            indexes[index] = i;
            if (words[i].equals("/from")) {
                index = 1;
            }
            if (words[i].equals("/to")) {
                index = 2;
            }

        }
        if (indexes[0] == -1 || indexes[1] == -1 || indexes[2] == -1) {
            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n\n"
                + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
        }

        for (int i = 1; i < indexes[0]; i++) {
            taskName.append(words[i]).append(" ");

        }

        for (int i = indexes[0] + 1; i < indexes[1]; i++) {
            to.append(words[i]).append(" ");

        }

        for (int i = indexes[1] + 1; i < indexes[2]; i++) {
        from.append(words[2]).append(" ");

        }

        checkEmpty(taskName, input, StringType.NAME);
        checkEmpty(from, input, StringType.FROM);
        checkEmpty(to, input, StringType.TO);

        return taskList.addEvent(taskName.toString().trim(), from.toString().trim(),
                to.toString().trim());
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
                case NAME -> output = "Event must have a name!\n\n";
                case FROM -> output = "Event must have a from date!\n\n";
                case TO -> output = "Event must have a to date!\n\n";
                default -> output = "";

            }

            throw new InvalidSyntaxException("Uh Oh! Invalid Syntax for event!\n"
                    + output
                        + "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"", input);
        }

    }
    
}
