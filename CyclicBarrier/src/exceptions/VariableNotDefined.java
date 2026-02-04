package exceptions;

public class VariableNotDefined extends RuntimeException {
    public VariableNotDefined(String message) {
        super(message);
    }
}
