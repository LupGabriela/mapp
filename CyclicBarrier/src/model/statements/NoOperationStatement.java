package model.statements;

import model.ProgramState;
import model.types.Type;
import model.utilities.ADTs.IDictionary;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "NOP";
    }
}
