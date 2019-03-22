package constant;

public enum FileConstants {
    FILE_EXTENSION_SEPARATOR("."),
    JIRA_KEY_PREFIX("_"),
    ATTACHMENT_PATH("\\target\\attachments\\") ;

    private final String value;

    FileConstants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}