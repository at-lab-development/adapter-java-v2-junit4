package constant;

public enum JIRATestResult {
    FAILED("Failed"),
    PASSED("Passed"),
    BLOCKED("Blocked"),
    UNTESTED("Untested"),
    OUT_OF_SCOPE("Out of Scope");

    private final String text;

    JIRATestResult(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}