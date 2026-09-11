package bean.task;

public enum Priority {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int level;

    Priority(int level) {
        this.level = level;

    }

    public int getLevel() {
        return level;
    }

    public static Priority fromString(String value) {
        return switch (value.toLowerCase()) {
            case "high" -> Priority.HIGH;
            case "medium" -> Priority.MEDIUM;
            case "low" -> Priority.LOW;
            default -> throw new IllegalArgumentException(
                    "Unknown priority: " + value
            );
        };



    }
    
}
