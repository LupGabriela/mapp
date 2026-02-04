package model.utilities.DTOs;

import javafx.beans.property.SimpleStringProperty;
import model.values.Value;

public class SymbolTableEntry {

    private SimpleStringProperty name;
    private SimpleStringProperty value;
    private String originalName;
    private Value originalValue;

    public SymbolTableEntry(String name, Value value) {
        this.name = new SimpleStringProperty(String.valueOf(name));
        this.value = new SimpleStringProperty(value.toString());
        this.originalName = name;
        this.originalValue = value.deepCopy();
    }

    public String getName() {
        return originalName;
    }

    public Value getValue() {
        return originalValue;
    }
}
