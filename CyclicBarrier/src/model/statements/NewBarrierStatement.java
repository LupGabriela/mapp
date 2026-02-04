package model.statements;

import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import javafx.util.Pair;
import model.ProgramState;
import model.expressions.Expression;
import model.types.IntType;
import model.types.Type;
import model.utilities.ADTs.BarrierTable;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.utilities.ADTs.IStack;
import model.values.IntValue;
import model.values.Value;

import java.util.ArrayList;

public class NewBarrierStatement implements IStatement{

    private Expression expression;
    private String var;

    public NewBarrierStatement( String var, Expression expression) {
        this.expression = expression;
        this.var = var;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {

        synchronized (state.getBarrierTable()) {
            IDictionary<String, Value> symbolTable = state.getSymbolTable();
            IHeap<Value> heap = state.getHeap();
            var barrierTable = state.getBarrierTable();
            Value value = expression.evaluate(symbolTable, heap);
            if (!value.getType().equals(new IntType()))
                throw new InvalidArguments("the expression must evaluate to IntType");

            if (!symbolTable.containsKey(var))
                throw new InvalidArguments("the var is not declared");

            if (!symbolTable.lookUp(var).getType().equals(new IntType()))
                throw new InvalidArguments("the type of var must be IntType ");

            Integer location = barrierTable.getFreeLocation();
            Integer nr = ((IntValue) value).getValue();

            barrierTable.put(location, new Pair<>(nr, new ArrayList<>()));
            symbolTable.replace(var, new IntValue(location));
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!expression.typecheck(typeEnvironment).equals(new IntType()))
            throw new TypeCheckException("the expression must evaluate to IntType");

        if(!typeEnvironment.containsKey(var))
            throw new InvalidArguments("the var is not declared");

        if (!typeEnvironment.lookUp(var).equals(new IntType()))
            throw new InvalidArguments("the type of var must be IntType");

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "barrier(" + expression + ", " + var + ")";
    }
}
