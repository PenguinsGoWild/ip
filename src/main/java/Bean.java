import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Bean {
    static boolean terminateProgram = false;
    private static BeanList bl = new BeanList();

    public static void main(String[] args) {
        intro();
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!terminateProgram) {
            try {
                System.out.print(">>> ");
                input = reader.readLine();
                System.out.println();
                getCommand(input);
                input = "";

            } catch (IOException e) {
                System.err.println("An error occurred while reading input: " + e.getMessage());

            }
        }

    }

    private static void intro() {
                    String banner =
                        "       ▄▄▄                    \n" +
                        "      ██▀▀█▄                  \n" +
                        "      ██ ▄█▀             ▄    \n" +
                        "      ██▀▀█▄ ▄█▀█▄ ▄▀▀█▄ ████▄\n" +
                        "    ▄ ██  ▄█ ██▄█▀ ▄█▀██ ██ ██\n" +
                        "    ▀██████▀▄▀█▄▄▄▄▀█▄██▄██ ▀█\n";
                           
                           
        System.out.println("----------------------------------");
        System.out.println(banner);
        String intro = "Hello! I'm Bean.\n\nWhat can I do for you today?";
        printString(intro);
        
    }

    private static void outro() {
        String goodbye = "Bye bye! Hope to see you again soon!";
        printString(goodbye);
        terminateProgram = true;

    }

    private static void getCommand(String input) {
        String[] str = input.split(" ");
        int index = -1;
        switch (Commands.match(str[0])) {
            case Commands.EXIT:
                outro();
                break;
            case Commands.LIST:
                bl.displayTasks();
                break;
            case Commands.MARK:
                if (str.length == 1) break;
                index = Integer.parseInt(str[1]);
                if (index <= 0 || index - 1 >= bl.getSize()) {
                    printString("Oops, you've keyed in an invalid Task index!");
                    break;
                }
                printString("Good Job! I'll mark the task as done!\n\n"
                     +
                     " [X] " + bl.getTaskName(index)
                );

                bl.markTask(index);
                break;
            case Commands.UNMARK:
                if (str.length == 1) break;
                index = Integer.parseInt(str[1]);
                if (index <= 0 || index - 1 >= bl.getSize()) {
                    printString("Oops, you've keyed in an invalid Task index!");
                    break;
                }
                printString("Awww, Okay! I'll unmark it!\n\n"
                     +
                     " [ ] " + bl.getTaskName(index)
                );
                bl.unmarkTask(index);
                break;
            default:
                bl.addTask(input);   

        }

    }

    public static void printString(String input) {
                System.out.println("----------------------------------");
                System.out.println();
                System.out.println(input);
                System.out.println();
                System.out.println("----------------------------------");
                System.out.println();


    }


}
