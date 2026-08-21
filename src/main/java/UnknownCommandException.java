public class UnknownCommandException extends RuntimeException {
    
    private final String invalidCommand;

    public UnknownCommandException(String message, String invalidCommand) {
        super(message);
        this.invalidCommand = invalidCommand;
    }

    public UnknownCommandException(String invalidCommand) {
        super("Command not recognized by the chatbot.");
        this.invalidCommand = invalidCommand;
    }

    public String getInvalidCommand() {
        return invalidCommand;
    }
}