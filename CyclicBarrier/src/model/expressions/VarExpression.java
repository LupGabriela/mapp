package model.expressions;

import exceptions.TypeCheckException;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.Value;

public class VarExpression implements Expression{

    String id;

    public VarExpression(String id) {
        this.id = id;
    }

    @Override
    public synchronized Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        synchronized (symbolTable) {
            return symbolTable.lookUp(id);
        }
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!typeEnvironment.containsKey(id))
            throw new TypeCheckException("the variable isn't defined\n");
        return typeEnvironment.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
