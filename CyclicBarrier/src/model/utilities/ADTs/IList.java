package model.utilities.ADTs;

import java.util.Iterator;

public interface IList<E> {
    void add(E element);
    Iterator<E> getAll();
    E pop();
    int size();
}
