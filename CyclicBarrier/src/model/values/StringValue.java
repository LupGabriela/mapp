package model.values;

import model.types.StringType;
import model.types.Type;

import java.util.Objects;

public class StringValue implements Value {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getValue(){
        return value;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString(){
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        StringValue that = (StringValue) other;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
