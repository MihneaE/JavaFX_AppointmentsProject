package Sorting;

import Model.Appointment;
import Model.ListImplementation;
import Model.Person;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** SORT BY NAME AND AGE OF A PERSON **/

public class PersonSorting {

    static class SortByName implements Comparator<Appointment> {

        @Override
        public int compare(Appointment a1, Appointment a2) {
            return a1.getPerson().getName().compareTo(a2.getPerson().getName());
        }
    }

    static class SortByAge implements Comparator<Appointment>
    {
        @Override
        public int compare(Appointment a1, Appointment a2) {
            return a1.getPerson().getAge().compareTo(a2.getPerson().getAge());
        }
    }

    public List<Appointment> sortByName(ListImplementation<Appointment> list) {
        List<Appointment> sortedList = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i)
            sortedList.add(list.get(i));

        for (int i = 0; i < sortedList.size() - 1; ++i)
        {
            for (int j = i + 1; j < sortedList.size(); ++j)
            {
                SortByName comparator = new SortByName();

                if (comparator.compare(sortedList.get(i), sortedList.get(j)) > 0)
                {
                    Appointment aux = sortedList.get(i);
                    sortedList.set(i, sortedList.get(j));
                    sortedList.set(j, aux);
                }
            }
        }

        return sortedList;
    }

    public List<Appointment> sortByAge(ListImplementation<Appointment> list)
    {
        List<Appointment> sortedList = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i)
            sortedList.add(list.get(i));

        for (int i = 0; i < sortedList.size() - 1; ++i)
        {
            for (int j = i + 1; j < sortedList.size(); ++j)
            {
                SortByAge comparator = new SortByAge();

                if (comparator.compare(sortedList.get(i), sortedList.get(j)) > 0)
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
