package Model;

/**  HERE IS DEFINED AN INTERFACE, WHICH CONTAINS THE METHODS OF THE LIST **/

public interface ListInterface<E> {
    void add(E element);
    void addAtIndex(int index, E element);
    E get(int index);
    E removeByIndex(int index);
    void remove(E element);
    void set(int index, E element);
    boolean contains(E element);
    void clear();
    int size();
    boolean isEmpty();
}
