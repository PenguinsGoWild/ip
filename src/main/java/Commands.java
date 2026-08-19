public enum Commands {
    EXIT ("exit"),
    NONE;

    private final String[] names;

    Commands(String... names) {
        this.names = names;

    }

    public static Commands match(String input) {
        for (Commands cmd : Commands.values()) {
            for (String name : cmd.names)  {
                if (name.equalsIgnoreCase(input)) {
                    return cmd;
                }
            }
        }
        return NONE;
    }

    
}
