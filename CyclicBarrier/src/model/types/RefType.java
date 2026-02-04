package model.types;

import model.values.RefValue;
import model.values.Value;

import java.util.Objects;

public class RefType implements Type {

    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner(){ return this.inner; }

    @Override
    public Value defaultValue() {
         return new RefValue(0, inner);
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof RefType)
            return inner.equals(((RefType) other).getInner());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }
}
