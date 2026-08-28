package bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bean.command.CommandParser;
import bean.exception.BeanListOutOfBoundsException;
import bean.exception.InvalidSyntaxException;
import bean.exception.UnknownCommandException;
import bean.storage.MemoryHandler;
import bean.task.BeanList;
import bean.ui.BeanInteraction;

/** Starts the Bean command-line task manager. */
public class Bean {
    private static final BeanList TASKS = new BeanList();
    private static final MemoryHandler MEMORY_HANDLER = new MemoryHandler("./memory.txt");

    private static boolean isTerminated = false;

    /** Starts the application and accepts commands until the user exits. */
    public static void main(String[] args) {
        MEMORY_HANDLER.readMemory(TASKS);
        BeanInteraction.intro();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!isTerminated) {
            try {
                System.out.print(">>> ");
                String input = reader.readLine();
                System.out.println();
                CommandParser.getCommand(input, TASKS);
            } catch (IOException e) {
                System.err.println("An error occurred while reading input: " + e.getMessage());
            } catch (UnknownCommandException e) {
                BeanInteraction.printString(e.getMessage());
            } catch (BeanListOutOfBoundsException e) {
                BeanInteraction.printString(e.getMessage());
            } catch (InvalidSyntaxException e) {
                BeanInteraction.printString(e.getMessage());
            }
        }
        MEMORY_HANDLER.writeMemory(TASKS);
    }

    /** Requests normal application termination after the current command completes. */
    public static void terminate() {
        isTerminated = true;
    }
}
