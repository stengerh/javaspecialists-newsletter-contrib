package eu.javaspecialists.newsletter.issue258.contrib;

public class NondeterministicExecutionException extends RuntimeException {
    public NondeterministicExecutionException() {
    }

    public NondeterministicExecutionException(String message) {
        super(message);
    }

    public NondeterministicExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NondeterministicExecutionException(Throwable cause) {
        super(cause);
    }
}
