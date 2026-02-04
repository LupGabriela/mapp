package model.values;

import model.types.RefType;
import model.types.Type;

public class RefValue implements Value {

    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress(){ return address; }

    public Type getLocationType() { return locationType; }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(this.address,this.locationType.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + address +
                ", " + locationType + ")";
    }
}
