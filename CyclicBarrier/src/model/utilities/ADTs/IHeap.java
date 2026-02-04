package model.utilities.ADTs;

import model.values.Value;

import java.util.Map;

public interface IHeap<V> {

    V lookUp(Integer key);
    boolean containsKey(Integer key);
    int put(V value);
    void remove(Integer key);
    void replace(Integer key, V newValue);

    void setContent(Map<Integer, V> values);
    Map<Integer, V> getContent();
}
