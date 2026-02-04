package model.statements;

import exceptions.AlreadyExistingVariable;
import exceptions.InvalidArguments;
import exceptions.NotFoundException;
import exceptions.TypeCheckException;
import model.ProgramState;
import model.expressions.Expression;
import model.types.StringType;
import model.types.Type;
import model.utilities.ADTs.IDictionary;
import model.utilities.ADTs.IFileTable;
import model.utilities.ADTs.IHeap;
import model.values.StringValue;
import model.values.Value;

import java.io.*;

public class OpenRFileStatement implements IStatement{

    private final Expression expression;

    public OpenRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        IFileTable fileTable = state.getFileTable();
        IHeap<Value> heap = state.getHeap();
        var value = expression.evaluate(symbolTable, heap);
        if(!(value.getType().equals(new  StringType())))
            throw new InvalidArguments("invalid expression type");
        if (fileTable.containsKey((StringValue) value))
            throw new AlreadyExistingVariable("File was already opened");
        StringValue filename = ((StringValue) value);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename.getValue()));
            fileTable.put(filename, bufferedReader);
        } catch (FileNotFoundException fileNotFoundException) {
            throw new NotFoundException("File not found" + filename.getValue());
        }
        return null;
    }

    @Override
    public IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment) {
        if(!expression.typecheck(typeEnvironment).equals(new StringType()))
            throw new TypeCheckException("The type of the expresion must be StringType\n");
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "open(" + expression + ')';
    }
}
