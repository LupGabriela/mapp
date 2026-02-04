package model.utilities.ADTs;

import java.util.Map;

public interface IDictionary<K, V> {
    V lookUp(K key);
    boolean containsKey(K key);
    void put(K key, V value);
    void remove(K key);
    void replace(K key, V newValue);
    void setContent(Map<K,V> content);
    Map<K,V> getContent();
    public IDictionary<K,V> deepCopy();
}
