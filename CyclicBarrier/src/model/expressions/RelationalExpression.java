package model.expressions;

import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import model.types.BooleanType;
import model.types.IntType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.values.BooleanValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression{

    private final Expression left;
    private final Expression right;
    private final String operator;

    public RelationalExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> symbolTable, IHeap<Value> heap) {
        Value valueLeft = left.evaluate(symbolTable, heap);
        if(!(valueLeft.getType().equals(new IntType())))
            throw new InvalidArguments("Invalid left argument");
        Value valueRight = right.evaluate(symbolTable, heap);
        if(!(valueRight.getType().equals(new IntType())))
            throw new InvalidArguments("Invalid right argument");
        int numberLeft = ((IntValue) valueLeft).getValue();
        int numberRight = ((IntValue) valueRight).getValue();
        return switch (operator) {
            case "<" -> new BooleanValue(numberLeft < numberRight);
            case "<=" -> new BooleanValue(numberLeft <= numberRight);
            case "==" -> new BooleanValue(numberLeft == numberRight);
            case "!=" -> new BooleanValue(numberLeft != numberRight);
            case ">" -> new BooleanValue(numberLeft > numberRight);
            case ">=" -> new BooleanValue(numberLeft >= numberRight);
            default -> throw new InvalidArguments("invalid operator");
        };
    }

    @Override
    public Type typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!left.typecheck(typeEnvironment).equals(new IntType()))
            throw new TypeCheckException("the expresion must be of IntType\n");
        if(!right.typecheck(typeEnvironment).equals(new IntType()))
            throw new TypeCheckException("the expresion must be of IntType\n");
        return new BooleanType();
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }
}
