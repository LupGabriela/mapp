package model.statements;

import model.ProgramState;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IStack;
import model.utilities.ADTs.MyStack;

public class CompoundStatement implements IStatement {

    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IStack<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        return second.typecheck(first.typecheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return  first + " ; " + second;
    }
}
