package model.statements;

import exceptions.InvalidArguments;
import javafx.util.Pair;
import model.ProgramState;
import model.types.IntType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.IntValue;
import model.values.Value;

import java.util.List;

public class AwaitStatement implements IStatement{

    private String var;

    public AwaitStatement(String var) {
        this.var = var;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {
        synchronized (state.getBarrierTable()) {
            var symbolTable = state.getSymbolTable();
            var barrierTable = state.getBarrierTable();
            var executionStack = state.getExecutionStack();

            if (!symbolTable.containsKey(var))
                throw new InvalidArguments("var was not declared");

            if (!symbolTable.lookUp(var).getType().equals(new IntType()))
                throw new InvalidArguments("var must be of type IntType");

            Integer foundIndex = ((IntValue) symbolTable.lookUp(var)).getValue();

            if (!barrierTable.containsKey(foundIndex))
                throw new InvalidArguments("the barrier is not declared");

            Integer max_len = barrierTable.lookUp(foundIndex).getKey();
            var list1 = barrierTable.lookUp(foundIndex).getValue();
            Integer length = list1.size();
            if (max_len > length) {
                if (!list1.contains(state.getProgramId())) {
                    list1.add(state.getProgramId());
                }
                executionStack.push(this);
            }
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!typeEnvironment.containsKey(var))
            throw new InvalidArguments("the var is not declared");

        if (!typeEnvironment.lookUp(var).equals(new IntType()))
            throw new InvalidArguments("the type of var must be IntType");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "await(" + var + ")";
    }
}
