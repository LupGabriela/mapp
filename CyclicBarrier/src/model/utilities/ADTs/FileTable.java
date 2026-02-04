package model.utilities.ADTs;

import exceptions.VariableNotDefined;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileTable implements  IFileTable{

    private final Map<StringValue, BufferedReader> files;

    public FileTable() {
        this.files = new ConcurrentHashMap<>();
    }

    public FileTable(Map<StringValue, BufferedReader> files) {
        this.files = files;
    }

    @Override
    public BufferedReader lookUp(StringValue key) {
        BufferedReader value = files.get(key);
        if( value == null )
            throw new VariableNotDefined("Inexistent key: " + key);
        return value;
    }

    @Override
    public boolean containsKey(StringValue key) {
        return files.containsKey(key);
    }

    @Override
    public void put(StringValue key, BufferedReader value) {
        files.put(key, value);
    }

    @Override
    public void remove(StringValue key) {
        files.remove(key);
    }

    @Override
    public Map<StringValue, BufferedReader> getContent() {
        return files;
    }

    @Override
    public String toString() {
        return files.toString();
    }
}
