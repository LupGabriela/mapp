package model.utilities.ADTs;

import model.values.StringValue;
import java.io.BufferedReader;
import java.util.Map;

public interface IFileTable {
    BufferedReader lookUp(StringValue key);
    boolean containsKey(StringValue key);
    void put(StringValue key, BufferedReader value);
    void remove(StringValue key);
    Map<StringValue, BufferedReader> getContent();
}
