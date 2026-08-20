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
        StringBuilder sb = new StringBuilder();
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
                     " "+ bl.getTaskTag(index) + "[X] " + bl.getTaskName(index)
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
                     " " + bl.getTaskTag(index) + "[ ] " + bl.getTaskName(index)
                );
                bl.unmarkTask(index);
                break;
            case Commands.TODO:
                for (int i = 1; i < str.length; i++) {
                    sb.append(str[i] + " ");
                }
                if (sb.isEmpty()) {
                    printString("Uh Oh! Invalid Syntax for todo!\n\n" +
                            "Usage: todo \"TASK\"");
                            return;

                }
                bl.addTodo(sb.toString().trim());
                break;
            case Commands.EVENT:
                StringBuilder from = new StringBuilder();
                StringBuilder to = new StringBuilder();
                for (int i = 1; i < str.length; i++) {
                    if (str[i].equals("/from")) {
                        if (i+1 >= str.length) {
                            printString("Uh Oh! Invalid Syntax for event!\n\n" +
                                    "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"");
                            return;
                        }
                        for (int j = i+1; j < str.length; j++) {
                            if (str[j].equals("/to")) {
                                if (j+1 >= str.length) {
                                    printString("Uh Oh! Invalid Syntax for event!\n\n" +
                                            "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"");
                                    return;
                                }
                                for (int k = j+1; k <str.length; k++)  {
                                    to.append(str[k] + " ");
                                }
                                break;

                            }
                            from.append(str[j] + " ");

                        }
                        break;

                    }
                    sb.append(str[i] + " ");
                }
                if (from.isEmpty() || to.isEmpty()) {
                    printString("Uh Oh! Invalid Syntax for event!\n\n" +
                    "Usage: event \"TASK\" /from \"DATE\" /to \"DATE\"");
                    return;

                }


                bl.addEvent(sb.toString().trim(), from.toString().trim(), to.toString().trim());
                break;
            case Commands.DEADLINE:
                StringBuilder date = new StringBuilder();
                for (int i = 1; i < str.length; i++) {
                    if (str[i].equals("/by")) {
                        if (i+1 >= str.length) {
                            printString("Uh Oh! Invalid Syntax for deadline!");
                            return;
                        }
                        for (int j = i+1; j < str.length; j++) {
                            date.append(str[j] + " ");

                        }
                        break;

                    }
                    sb.append(str[i] + " ");
                }
                if (date.isEmpty()) {
                    printString("Uh Oh! Invalid Syntax for deadline!\n\n" +
                    "Usage: deadline \"TASK\" /by \"DATE\"");
                    return;

                }
                bl.addDeadline(sb.toString().trim(), date.toString().trim());
                break;
            default:
                printString("Sorry, I don't know what you mean. :<");

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
