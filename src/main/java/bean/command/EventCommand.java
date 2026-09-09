package bean.command;

import bean.exception.InvalidSyntaxException;
import bean.task.BeanList;

/** Executes commands that add event tasks. */
public class EventCommand {
    private static final String USAGE = "event \"TASK\" /from \"DATE\" /to \"DATE\"";

    /** Adds an event task based on the supplied command input. */
    public static String execute(String input, BeanList taskList) {
        String[] words = input.split(" ");
        int[] indexes = {-1, -1, -1};
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

        StringBuilder taskName = CommandText.joinWords(words, 1, indexes[0]);
        StringBuilder from = CommandText.joinWords(words, indexes[0] + 1, indexes[1]);
        StringBuilder to = CommandText.joinWords(words, indexes[1] + 1, indexes[2] + 1);

        CommandValidator.checkEmpty(taskName, input, "event",
                "Event must have a name!\n\n", USAGE);
        CommandValidator.checkEmpty(from, input, "event",
                "Event must have a from date!\n\nTry the format yyyy-mm-dd!\n\n",
                USAGE);
        CommandValidator.checkEmpty(to, input, "event",
                "Event must have a to date!\n\nTry the format yyyy-mm-dd!\n\n",
                USAGE);

        return taskList.addEvent(taskName.toString().trim(), from.toString().trim(),
                to.toString().trim());
    }
}
