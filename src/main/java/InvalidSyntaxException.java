
public class InvalidSyntaxException extends RuntimeException {
    
    private final String invalidSyntax;

    public InvalidSyntaxException(String message, String invalidSyntax) {
        super(message);
        this.invalidSyntax = invalidSyntax;
    }

    public InvalidSyntaxException(String invalidSyntax) {
        super("Command not recognized by the chatbot.");
        this.invalidSyntax = invalidSyntax;
    }

    public String getInvalidCommand() {
        return invalidSyntax;
    }
}