package model.statements;

import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import model.ProgramState;
import model.expressions.Expression;
import model.types.BooleanType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import model.utilities.ADTs.IStack;
import model.values.BooleanValue;
import model.values.Value;

public class WhileStatement implements  IStatement {

    private Expression condition;
    private IStatement statement;

    public WhileStatement(Expression condition, IStatement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IStack<IStatement> executionStack = state.getExecutionStack();
        IHeap<Value> heap = state.getHeap();
        Value value = condition.evaluate(symbolTable, heap);
        if (!value.getType().equals(new BooleanType()))
            throw new InvalidArguments("Invalid type for while condition");
        if (((BooleanValue) value).getValue()) {
            executionStack.push(this);
            executionStack.push(statement);
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!condition.typecheck(typeEnvironment).equals(new BooleanType()))
            throw new TypeCheckException("the condition must be a BooleanType\n");
        statement.typecheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "while(" + condition +
                ") {" + statement + "}";
    }
}
