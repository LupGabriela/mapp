package exceptions;

public class ConcurrentException extends RuntimeException{
    public ConcurrentException(String message) {
        super(message);
    }
}
