package model.statements;

import model.ProgramState;
import model.types.Type;
import model.utilities.ADTs.IDictionary;

public interface IStatement extends Cloneable {
    ProgramState execute(ProgramState state);
    IDictionary<String, Type> typecheck(IDictionary<String, Type> typeEnvironment);
}
