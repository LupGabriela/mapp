package model.utilities.ADTs;

import exceptions.VariableNotDefined;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<K, V> implements IDictionary<K, V> {

    private Map<K, V> map;

    public MyDictionary() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public V lookUp(K key) {
        V value = map.get(key);
        if( value == null )
            throw new VariableNotDefined("Inexistent key: " + key);
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public void replace(K key, V newValue) {
        map.replace(key, newValue);
    }

    @Override
    public void setContent(Map<K, V> content) {
        this.map = content;
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public IDictionary<K, V> deepCopy() {
        Map<K,V> copyMap = new ConcurrentHashMap<>(map);
        MyDictionary<K,V> copyMyDictionary = new MyDictionary<>();
        copyMyDictionary.setContent(copyMap);
        return copyMyDictionary;
    }

    @Override
    public String toString() {
        return map.toString() ;
    }
}
