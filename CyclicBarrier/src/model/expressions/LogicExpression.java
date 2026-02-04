package model.expressions;

import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import model.types.BooleanType;
import model.types.IntType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.BooleanValue;
import model.values.Value;

public class LogicExpression implements Expression {

    private Expression left;
    private Expression right;
    private String operator;// and - &&, or - ||

    public LogicExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        Value value1 = left.evaluate(symbolTable, heap);
        if(!value1.getType().equals(new BooleanType()))
            throw new InvalidArguments("first operator is not of type boolean");
        boolean bool1 = ((BooleanValue) value1).getValue();
        Value value2 = right.evaluate(symbolTable, heap);
        if(!value2.getType().equals(new BooleanType()))
            throw new InvalidArguments("second operator is not of type boolean");
        boolean bool2 = ((BooleanValue) value2).getValue();
        if(operator.equals("and")){
            return new BooleanValue(bool1 && bool2);
        } else if (operator.equals("or")){
            return new BooleanValue(bool1 || bool2);
        }
        return null;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!left.typecheck(typeEnvironment).equals(new BooleanType()))
            throw new TypeCheckException("the expresion must be of BooleanType\n");
        if(!right.typecheck(typeEnvironment).equals(new BooleanType()))
            throw new TypeCheckException("the expresion must be of BooleanType\n");
        return new BooleanType();
    }

    @Override
    public String toString() {
        String printFormat = String.valueOf(left);
        if(operator.equals("and"))
            printFormat += " && ";
        else if(operator.equals("or"))
            printFormat += " || ";
        printFormat += String.valueOf(right);
        return printFormat;
    }
}
