package Model;

import java.util.Arrays;

/**  HERE IS THE IMPLEMENTATION OF THE INTERFACE **/

public class ListImplementation<E> implements ListInterface<E> {
    private Object[] elements;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 10;

    public ListImplementation()
    {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.elements = new Object[capacity];
    }

    public void ensureCapacity()
    {
        this.capacity = capacity * 2;
        this.elements = Arrays.copyOf(elements, capacity);
    }

    @Override
    public void add(E element) {
        if (size == capacity - 1)
            ensureCapacity();

        elements[size] = element;
        size++;
    }

    @Override
    public void addAtIndex(int index, E element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index is out of bound");

        if (size == capacity - 1)
            ensureCapacity();

        for (int i = size; i > index; --i)
        {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index is out of bound");

        return (E) elements[index];
    }

    @Override
    public E removeByIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index is out of bound");

        Object element = elements[index];

        for (int i = index; i < size - 1; ++i)
            elements[i] = elements[i + 1];

        size--;

        return (E) element;
    }

    @Override
    public void remove(E element) {
        int index = -1;

        for (int i = 0; i < size; ++i)
        {
            if (element == elements[i])
            {
                index = i;
                break;
            }
        }

        if (index != -1)
        {
            for (int i = index; i < size - 1; ++i)
                elements[i] = elements[i + 1];
        }

        size--;
        elements[size] = null;
    }

    @Override
    public void set(int index, E element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index is out of bound");

        elements[index] = element;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; ++i)
            if (element == elements[i])
                return true;
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; ++i)
            elements[i] = null;

        size = 0;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


}
