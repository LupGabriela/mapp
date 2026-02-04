package model.types;

import model.values.BooleanValue;
import model.values.Value;

public class BooleanType implements Type {
    public BooleanType() { }

    @Override
    public boolean equals(Object object){
        return object instanceof BooleanType;
    }

    @Override
    public String toString(){
        return "boolean";
    }

    @Override
    public Value defaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BooleanType();
    }
}
