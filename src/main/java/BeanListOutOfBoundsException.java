public class BeanListOutOfBoundsException extends RuntimeException {
    
    private final int invalidIndex;

    public BeanListOutOfBoundsException(String message, int invalidIndex) {
        super(message);
        this.invalidIndex = invalidIndex;
    }

    public BeanListOutOfBoundsException(int invalidIndex) {
        super("Error out of bounds of the array!");
        this.invalidIndex = invalidIndex;
    }

    public int getInvalidIndex() {
        return invalidIndex;
    }
}
