package model.expressions;

import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.Value;

public class ValueExpression implements Expression {

    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        return value;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) {
        return value.getType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
