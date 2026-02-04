package model.statements;

import exceptions.InvalidArguments;
import exceptions.TypeCheckException;
import model.ProgramState;
import model.expressions.Expression;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IHeap;
import exceptions.VariableNotDefined;
import model.values.Value;

public class AssignmentStatement implements IStatement {
    String id;
    Expression expression;

    public AssignmentStatement(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        if (!symbolTable.containsKey(id))
            throw new VariableNotDefined("Variable not declared");
        Value value = expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(symbolTable.lookUp(id).getType()))
            throw new InvalidArguments("declared type of variable " + id +
                    " and type of the assigned expression do not match");
        symbolTable.replace(id, value);
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!typeEnvironment.containsKey(id))
            throw new TypeCheckException("The variable isn't declared.\n");
        if(!typeEnvironment.lookUp(id).equals(expression.typecheck(typeEnvironment)))
            throw new TypeCheckException("Assignment: right hand side and left hand side have different types.");
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return  id + "=" + expression;
    }
}
