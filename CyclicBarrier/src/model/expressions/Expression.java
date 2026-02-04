package model.expressions;

import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.Value;

public interface Expression {
    Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap);
    Type typecheck(IDictionary<String, Type> typeEnvironment);
}
