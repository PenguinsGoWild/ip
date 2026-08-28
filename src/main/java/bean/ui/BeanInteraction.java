package bean.ui;

/** Provides formatted console output for the application. */
public class BeanInteraction {

    /**
     * Prints a nicely formatted string.
     * 
     * @param input Input to be formatted and printed to screen.
     */
    public static void printString(String input) {
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println(input);
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println();
    }

    /** Displays the application's greeting and banner. */
    public static void intro() {
        String banner = "       ▄▄▄                    \n"
                + "      ██▀▀█▄                  \n"
                + "      ██ ▄█▀             ▄    \n"
                + "      ██▀▀█▄ ▄█▀█▄ ▄▀▀█▄ ████▄\n"
                + "    ▄ ██  ▄█ ██▄█▀ ▄█▀██ ██ ██\n"
                + "    ▀██████▀▄▀█▄▄▄▄▀█▄██▄██ ▀█\n";

        System.out.println("----------------------------------");
        System.out.println(banner);
        String intro = "Hello! I'm Bean.\n\nWhat can I do for you today?";
        printString(intro);
    }

    /** Displays the application's farewell message. */
    public static void outro() {
        String goodbye = "Bye bye! Hope to see you again soon!";
        printString(goodbye);
    }
}
