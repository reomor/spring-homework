package task14.exception;

public class ObjectAlreadyExists extends RuntimeException {
    public ObjectAlreadyExists(String message) {
        super(message);
    }
}
