import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Bean {
    private static BeanList bl = new BeanList();
    private static MemoryHandler mh = new MemoryHandler("./src/main/memory.txt");
    private static boolean isTerminated = false;

    public static void main(String[] args) {
        mh.readMemory(bl);
        BeanInteraction.intro();
        String input = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!isTerminated) {
            try {
                System.out.print(">>> ");
                input = reader.readLine();
                System.out.println();
                CommandParser.getCommand(input, bl);
                input = "";

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
        mh.writeMemory(bl);

    }

    public static void terminate() {
        isTerminated = true;
    }





}
