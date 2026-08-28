package bean.storage;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.time.format.DateTimeParseException;

import bean.task.BeanList;
import bean.exception.InvalidMemoryDataException;

public class MemoryHandler {
    private String path;
    private BufferedReader br;
    private PrintWriter pw;
    public static final String TASK_TODO = "0";
    public static final String TASK_DEADLINE = "1";
    public static final String TASK_EVENT = "2";

    public static final String TASK_UNMARKED = "0";
    public static final String TASK_MARKED = "1";

    public MemoryHandler(String path) {
        this.path = path;


    }

    public void readMemory(BeanList bl) {
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] vars = line.split("\\|");

                try {
                    if (vars.length < 3) {
                        throw new InvalidMemoryDataException(line);
                    }
                    if (vars[2].isBlank()) {
                        throw new InvalidMemoryDataException("Warning: Invalid Data! "
                            + "Name cannot be blank!", line);
                    }

                    switch (vars[0]) {
                        case TASK_TODO:
                            bl.addTodoSilent(vars[2]);
                            break;

                        case TASK_DEADLINE:
                            if (vars.length < 4) {
                                throw new InvalidMemoryDataException(line);
                            }
                            bl.addDeadlineSilent(vars[2], vars[3]);
                            break;

                        case TASK_EVENT:
                            if (vars.length < 5) {
                                throw new InvalidMemoryDataException(line);
                            }

                            bl.addEventSilent(vars[2], vars[3], vars[4]);
                            break;

                        default:
                            return;

                    }
                    if (vars[1].equals(TASK_MARKED)) {
                        bl.markTask(bl.getSize());
                    }


                } catch (InvalidMemoryDataException e) {
                    System.out.println(e.getMessage());
                }

            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("No memory found! Creating new memory file!");
            try {
                File file = new File(path);
                if (file.createNewFile()) {
                    System.out.println("File successfully created: " + file.getName());
                }
                
            } catch (IOException e2) {
                System.out.println("An error occurred when trying to create memory file!");
                e2.printStackTrace();
            } 
        } catch (IOException e) {
            System.out.println(e.getMessage());

        } catch (DateTimeParseException e) {
            System.out.println("Warning: Data format in memory is incorrect! Skipping line!");

        }


    }

    public void writeMemory(BeanList bl) {
        try {
            pw = new PrintWriter(new FileWriter(path));
            int size = bl.getSize();
            for (int i = 0; i < size; i++) {
                pw.println(bl.formatTask(i));

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

        pw.close();

    }


    
}
