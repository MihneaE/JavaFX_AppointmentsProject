package Sorting;

import Model.Appointment;
import Model.ListImplementation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** SORT BY SPORT **/

public class SportTypeSorting extends AbstractSorter<Appointment> implements Comparator<Appointment> {

    @Override
    public int compare(Appointment a1, Appointment a2) {
        return a1.getSport_type().compareTo(a2.getSport_type());
    }

    @Override
    public List<Appointment> sort(ListImplementation<Appointment> list) {
        List<Appointment> sortedList = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i)
            sortedList.add(list.get(i));

        for (int i = 0; i < sortedList.size() - 1; ++i)
        {
            for (int j = i + 1; j < sortedList.size(); ++j)
            {
                if (compare(sortedList.get(i), sortedList.get(j)) > 0)
                {
                    Appointment aux = sortedList.get(i);
                    sortedList.set(i, sortedList.get(j));
                    sortedList.set(j, aux);
                }
            }
        }

        return sortedList;
    }
}
