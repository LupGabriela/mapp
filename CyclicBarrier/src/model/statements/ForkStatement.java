package model.statements;

import model.ProgramState;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.MyDictionary;
import model.utilities.ADTs.MyStack;
import model.values.Value;

import java.util.Map;

public class ForkStatement implements IStatement {

    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Value> copySymbolTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry : state.getSymbolTable().getContent().entrySet())
            copySymbolTable.put(entry.getKey(), entry.getValue().deepCopy());
        return new ProgramState(new MyStack<>(), copySymbolTable, state.getOutput(),
                state.getFileTable(), statement, state.getHeap(), state.getBarrierTable());
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        statement.typecheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
