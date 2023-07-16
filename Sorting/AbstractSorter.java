package Sorting;

import Model.ListImplementation;

import java.util.Comparator;
import java.util.List;

/** HERE IS AN ABSTRACT SORTER **/

public abstract class AbstractSorter<E>  {

    public abstract List<E> sort(ListImplementation<E> list);
}
