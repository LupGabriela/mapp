package model.expressions;

import exceptions.DivisionByZero;
import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import model.types.IntType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.IntValue;
import model.values.Value;

public class ArithmeticExpression implements Expression{
    private Expression left;
    private Expression right;
    int operator;//1 - plus, 2 - minus, 3 - star, 4 - divide

    public ArithmeticExpression(Expression left, Expression right, int operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        Value value1, value2;
        value1 = left.evaluate(symbolTable, heap);
        if(!value1.getType().equals(new IntType()))
            throw new InvalidArguments("first operator is not an integer");
        int number1 = ((IntValue) value1).getValue();
        value2 = right.evaluate(symbolTable, heap);
        if(!value2.getType().equals(new IntType()))
            throw new InvalidArguments("second operator is not an integer");
        int number2 = ((IntValue) value2).getValue();
        if(operator == 1)
            return new IntValue(number1 + number2);
        else if(operator == 2)
            return new IntValue(number1 - number2);
        else if(operator == 3)
            return new IntValue(number1 * number2);
        else if(operator == 4) {
            if(number2 == 0)
                throw new DivisionByZero("division by zero");
            return new IntValue(number1 / number2);
        }
        return null;
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!left.typecheck(typeEnvironment).equals(new IntType()))
            throw new TypeCheckException("the expresion must be of IntType\n");
        if(!right.typecheck(typeEnvironment).equals(new IntType()))
            throw new TypeCheckException("the expresion must be of IntType\n");
        return new IntType();
    }

    @Override
    public String toString() {
        String printFormat = String.valueOf(left);
        if(operator == 1)
            printFormat += " + ";
        else if(operator == 2)
            printFormat += " - ";
        else if(operator == 3)
            printFormat += " * ";
        else if(operator == 4)
            printFormat += " / ";
        printFormat += String.valueOf(right);
        return printFormat;
    }
}
