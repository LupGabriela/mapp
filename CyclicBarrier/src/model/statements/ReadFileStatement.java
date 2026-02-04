package model.statements;

import exceptions.InvalidArguments;
import exceptions.NotFoundException;
import exceptions.ReadingException;
import exceptions.TypeCheckException;
import model.ProgramState;
import model.expressions.Expression;
import model.types.IntType;
import model.types.StringType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IFileTable;
import model.utilities.ADTs.IHeap;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {

    private final Expression expression;
    private final String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IFileTable fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();
        if(!symbolTable.containsKey(variableName))
            throw new NotFoundException("The variable does not exist");
        if(!(symbolTable.lookUp(variableName).getType().equals(new IntType())))
            throw new InvalidArguments("Invalid variable type");
        Value value = expression.evaluate(symbolTable, heap);
        if(!(value.getType().equals(new StringType())))
            throw new InvalidArguments("The expresion is not of valid string type.");
        StringValue filename = ((StringValue) value);
        if (!fileTable.containsKey(filename))
            throw new NotFoundException("The file that you want to read from is not opened");
        BufferedReader reader = fileTable.lookUp(filename);
        String line;
        try {
            line = reader.readLine();
        } catch (IOException ioException) {
            throw new ReadingException("An error occured while trying to read from the file");
        }
        if (line == null)
            symbolTable.replace(variableName, new IntValue(0));
        else
            symbolTable.replace(variableName, new IntValue((Integer.parseInt(line))));
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!typeEnvironment.containsKey(variableName))
            throw new TypeCheckException("variable is not defined.\n");
        if(!typeEnvironment.lookUp(variableName).equals(new IntType()))
            throw new TypeCheckException("the variable must be IntType.\n");
        if(!expression.typecheck(typeEnvironment).equals(new StringType()))
            throw new TypeCheckException("the expresion must be StringType.\n");
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return variableName + "=read(" + expression + ")";
    }
}
