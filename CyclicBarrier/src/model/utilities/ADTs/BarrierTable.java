package model.utilities.ADTs;

public class BarrierTable<K,V> extends MyDictionary<K,V> implements IBarrierTable<K,V> {

    private int nextFreeLocation;

    public BarrierTable() {
        super();
        this.nextFreeLocation = 1;
    }

    @Override
    public int getFreeLocation() {
        return nextFreeLocation;
    }

    @Override
    public void put(K key, V value) {
        super.put(key, value);
        nextFreeLocation++;
    }
}
