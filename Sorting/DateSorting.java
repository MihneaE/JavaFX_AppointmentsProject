package Sorting;

import Model.Appointment;
import Model.ListImplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** SORT BY START DATE AND BY AND DATE **/

public class DateSorting  {

    static class DateStart implements Comparator<Appointment> {
        @Override
        public int compare(Appointment a1, Appointment a2) {
            return a1.getStart().compareTo(a2.getStart());
        }
    }

    static class DateEnd implements Comparator<Appointment> {
        @Override
        public int compare(Appointment a1, Appointment a2) {
            return a1.getEnd().compareTo(a2.getEnd());
        }
    }

    public List<Appointment> sortByStartDate(ListImplementation<Appointment> list) {
        List<Appointment> sortedList = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i)
            sortedList.add(list.get(i));

        for (int i = 0; i < sortedList.size() - 1; ++i)
        {
            for (int j = i + 1; j < sortedList.size(); ++j)
            {
                DateStart comparator = new DateStart();

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

    public List<Appointment> sortByEndDate(ListImplementation<Appointment> list) {
        List<Appointment> sortedList = new ArrayList<>();

        for (int i = 0; i < list.size(); ++i)
            sortedList.add(list.get(i));

        for (int i = 0; i < sortedList.size() - 1; ++i)
        {
            for (int j = i + 1; j < sortedList.size(); ++j)
            {
                DateEnd comparator = new DateEnd();

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
