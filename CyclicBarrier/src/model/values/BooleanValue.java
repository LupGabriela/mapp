package model.values;

import model.types.BooleanType;
import model.types.Type;

import java.util.Objects;

public class BooleanValue implements Value {

    private boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }

    @Override
    public Value deepCopy() {
        return new BooleanValue(this.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        BooleanValue that = (BooleanValue) other;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
