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
        System.out.println("----------------------------------");
        String intro = "Hello! I'm Bean.";
        System.out.println();
        System.out.println(intro + "\n");
        System.out.println("What can I do for you today?");
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();
        
    }

    private static void outro() {
        String goodbye = "Bye bye! Hope to see you again soon!";
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println(goodbye);
        System.out.println();
        System.out.println("----------------------------------");
        terminateProgram = true;

    }

    private static void getCommand(String input) {
        switch (Commands.match(input)) {
            case Commands.EXIT:
                outro();
                break;
            default:
                System.out.println("----------------------------------");
                System.out.println();
                System.out.println(input);
                System.out.println();
                System.out.println("----------------------------------");
                System.out.println();

        }

    }


}
