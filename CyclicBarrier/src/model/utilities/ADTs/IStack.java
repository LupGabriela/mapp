package model.utilities.ADTs;

import java.util.List;

public interface IStack<E> {
    E pop();
    void push(E element);
    boolean isEmpty();
    List<E> toList();
}
