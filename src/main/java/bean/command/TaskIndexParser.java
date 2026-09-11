package bean.command;

import bean.exception.BeanListOutOfBoundsException;
import bean.exception.InvalidSyntaxException;

/** Parses and validates task indices supplied to task-manipulation commands. */
final class TaskIndexParser {

    private TaskIndexParser() {
    }

    /** Returns the one-based task index, or {@code -1} when no index is supplied. */
    static int parse(String input, int taskCount) {
        String[] words = input.split(" ");
        if (words.length == 1) {
            return -1;
        }

        if (words[1].isBlank()) {
            throw new InvalidSyntaxException(
                "Oops, you've keyed in an additional blank space index!", input);
        }
        int index = Integer.parseInt(words[1]);
        if (index <= 0 || index > taskCount) {
            throw new BeanListOutOfBoundsException(
                    "Oops, you've keyed in an invalid Task index! (" + index + " of "
                            + taskCount + ")", index);
        }
        return index;
    }
}
