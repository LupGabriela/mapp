package model.values;

import model.types.IntType;
import model.types.Type;

import java.util.Objects;

public class IntValue implements Value {

    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value) ;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        IntValue intValue = (IntValue) other;
        return value == intValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
