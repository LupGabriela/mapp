package exceptions;

public class AlreadyExistingVariable extends RuntimeException {
    public AlreadyExistingVariable(String message) {
        super(message);
    }
}
