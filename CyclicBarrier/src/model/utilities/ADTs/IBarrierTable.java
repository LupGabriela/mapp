package model.utilities.ADTs;

public interface IBarrierTable<K,V> extends IDictionary<K,V> {
    int getFreeLocation();
}
