package model.utilities.ADTs;

import exceptions.VariableNotDefined;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<V> implements IHeap<V>{

    private Map<Integer, V> entities;
    private AtomicInteger freeAddress;

    public MyHeap() {
        this.entities = new ConcurrentHashMap<>();
        this.freeAddress = new AtomicInteger(0);
    }


    @Override
    public V lookUp(Integer key) {
        V value = entities.get(key);
        if( value == null )
            throw new VariableNotDefined("Inexistent key: " + key);
        return value;
    }

    @Override
    public boolean containsKey(Integer key) {
        return entities.containsKey(key);
    }

    @Override
    public int put(V value) {
        int allocatedLocation = freeAddress.incrementAndGet();
        entities.put(allocatedLocation, value);
        return allocatedLocation;
    }

    @Override
    public void remove(Integer key) {
        entities.remove(key);
    }

    @Override
    public void replace(Integer key, V newValue) {
        entities.replace(key, newValue);
    }

    @Override
    public void setContent(Map<Integer, V> values) {
        this.entities = values;
    }

    @Override
    public Map<Integer, V> getContent() {
        return entities;
    }

    @Override
    public String toString() {
        return entities.toString();
    }
}
