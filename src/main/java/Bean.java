import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Bean {
    static boolean terminateProgram = false;

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
        switch (Commands.match(input)) {
            case Commands.EXIT:
                outro();
                break;
            default:
                printString(input);

        }

    }

    private static void printString(String input) {
                System.out.println("----------------------------------");
                System.out.println();
                System.out.println(input);
                System.out.println();
                System.out.println("----------------------------------");
                System.out.println();


    }


}
