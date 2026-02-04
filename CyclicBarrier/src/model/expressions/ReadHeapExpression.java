package model.expressions;

import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import model.types.RefType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.RefValue;
import model.values.Value;

public class ReadHeapExpression implements Expression{

    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public synchronized Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        synchronized (symbolTable){
            synchronized (heap){
                Value value = expression.evaluate(symbolTable, heap);
                if(!(value.getType() instanceof RefType))
                    throw new InvalidArguments("invalid type of variable");
                int address = ((RefValue) value).getAddress();

                if (!heap.containsKey(address))
                    throw new InvalidArguments("The variable is not declared in the heap");
                return heap.lookUp(address);
            }
        }
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) {
        Type type = expression.typecheck(typeEnvironment);
        if(!(type instanceof RefType))
            throw new TypeCheckException("the argument should be a RefType\n");
        return ((RefType) type).getInner();
    }

    @Override
    public String toString() {
        return "read(" + expression + ")";
    }
}
