package bean.exception;

public class InvalidMemoryDataException extends RuntimeException {

    private final String invalidMemory;

    public InvalidMemoryDataException(String message, String invalidMemory) {
        super(message);
        this.invalidMemory = invalidMemory;
    }

    public InvalidMemoryDataException(String invalidMemory) {
        super("Warning: Invalid Memory Data!"
                + "Data should only have integers, \'|\' and Strings! No spaces are allowed!");
        this.invalidMemory = invalidMemory;
    }

    public String getInvalidIndex() {
        return invalidMemory;
    }
}
