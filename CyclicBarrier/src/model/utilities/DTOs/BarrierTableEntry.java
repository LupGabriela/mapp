package model.utilities.DTOs;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.values.Value;

import java.util.ArrayList;
import java.util.List;

public class BarrierTableEntry {

    private SimpleStringProperty index;
    private SimpleStringProperty value;
    private SimpleStringProperty list;
    private Integer originalIndex;
    private Integer originalValue;
    private String originalList;

    public BarrierTableEntry(Integer originalIndex, Integer originalValue, String originalList) {
        this.originalIndex = originalIndex;
        this.originalValue = originalValue;
        this.originalList = originalList;
        this.index = new SimpleStringProperty(originalIndex.toString());
        this.value = new SimpleStringProperty(originalValue.toString());
        this.list = new SimpleStringProperty(originalList.toString());
    }

    public Integer getIndex() {
        return originalIndex;
    }

    public Integer getValue() {
        return originalValue;
    }

    public String getList() {
        return originalList;
    }
}
