package Service;

import Model.Appointment;
import Model.Bank;
import Model.ListImplementation;
import RandomFile.RandomFilePersons;
import RandomFile.RandomStartAppointments;
import Validator.Validator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;

/** IN MEMORY SERVICE IMPLEMENTATION **/

public class AppointmentsService {

        private ListImplementation<Appointment> appointments;
        private FileService fileService;
        private Validator validator;

        public AppointmentsService(ListImplementation<Appointment> appointments, FileService fileService, Validator validator)
        {
            this.appointments = appointments;
            this.fileService = fileService;
            this.validator = validator;
        }

        public void addAppointment(Appointment appointment) throws IOException {
            if (appointment == null)
                throw new IllegalArgumentException("Appointment can not be null!");

            if ((appointment.getPerson() == null) || (appointment.getStart() == null ) || (appointment.getEnd() == null) || appointment.getDuration() == null
                    || (appointment.getSport_type() == null) || (appointment.getPrice() == null))
                throw new IllegalArgumentException(("Fields can not be null"));

            validator.validateAdd(appointment);
            this.appointments.add(appointment);

            fileService.addAppointmentToFile(this.appointments);
            fileService.addPersonToFile(appointment.getPerson());
        }

        public void removeAppointment(Appointment appointment) throws IOException {
            if (appointment == null)
                throw new IllegalArgumentException("Appointment cannot be null!");

            this.validator.validateRemove(appointment);
            this.appointments.remove(appointment);

            this.fileService.removeAppointmentFromFile(appointment);
        }

        public void updateAppointmentByIndex(int index, Appointment appointment) throws IOException {
            if (appointment == null)
                throw new IllegalArgumentException("Appointment cannot be null!");

            this.validator.validateUpdateAppointment(appointment);
            this.appointments.set(index, appointment);

            this.fileService.updateAppointmentToFile(this.appointments, index, appointment);
        }

        public List<Appointment> filterAppointmentsByPersonNameAndAge(String name, Integer age)
        {
            validator.validateFileterByNameAndAge(name, age);

            List<Appointment> fileterdAppointments = new ArrayList<>();

            for (int i = 0; i < appointments.size(); ++i)
                if (appointments.get(i).getPerson().getName().equals(name) && appointments.get(i).getPerson().getAge().equals(age))
                    fileterdAppointments.add(appointments.get(i));

            return fileterdAppointments;
        }

        public List<Appointment> filterAppointmentsByPersonAge(Integer age)
        {
            if (age == null)
                throw new IllegalArgumentException("Age cannot be null!");

            this.validator.validateFilterByAge(age);

            List<Appointment> fileterdAppointments = new ArrayList<>();

            for (int i = 0; i < appointments.size(); ++i)
                if (appointments.get(i).getPerson().getAge().equals(age))
                    fileterdAppointments.add(appointments.get(i));

            return fileterdAppointments;
        }

        public List<Appointment> filterAppointmentsByPersonSubscription(String subscrription)
        {
            if (subscrription == null)
                throw new IllegalArgumentException("Subscription cannot be null!");

            this.validator.validateFilterBySubscription(subscrription);

            List<Appointment> fileterdAppointments = new ArrayList<>();

            for (int i = 0; i < appointments.size(); ++i)
                if (appointments.get(i).getPerson().getSubscription().equals(subscrription))
                    fileterdAppointments.add(appointments.get(i));

            return fileterdAppointments;
        }

    public List<Appointment> filterAppointmentsByDuration(Long duration)
    {
        if (duration == null)
            throw new IllegalArgumentException("Duration cannot be null!");

        this.validator.validateFilterByDuration(duration);

        List<Appointment> fileterdAppointments = new ArrayList<>();

        for (int i = 0; i < appointments.size(); ++i)
            if (appointments.get(i).getDuration().equals(duration))
                fileterdAppointments.add(appointments.get(i));

        return fileterdAppointments;
    }

    public List<Appointment> filterAppointmentsBySport(String sport)
    {
        if (sport == null)
            throw new IllegalArgumentException("Sport cannot be null!");

        this.validator.validateFilterBySport(sport);

        List<Appointment> fileterdAppointments = new ArrayList<>();

        for (int i = 0; i < appointments.size(); ++i)
            if (appointments.get(i).getSport_type().equals(sport))
                fileterdAppointments.add(appointments.get(i));

        return fileterdAppointments;
    }

    public List<Appointment> filterAppointmentsByPrice(Double price)
    {
        if (price == null)
            throw new IllegalArgumentException("Price cannot be null!");

        this.validator.validateFilterByPrice(price);

        List<Appointment> fileterdAppointments = new ArrayList<>();

        for (int i = 0; i < appointments.size(); ++i)
            if (appointments.get(i).getPrice().equals(price))
                fileterdAppointments.add(appointments.get(i));

        return fileterdAppointments;
    }

    public Map<String, Integer> countAppointmentsBySport()
    {
        Map<String, Integer> sportCount = new HashMap<>();

        for (int i = 0; i < appointments.size(); ++i)
        {
            String sport = appointments.get(i).getSport_type();

            if (sportCount.containsKey(sport))
                sportCount.put(sport, sportCount.get(sport) + 1);
            else
                sportCount.put(sport, 1);
        }

        return sportCount;
    }

    public Map<Long, Integer> countAppointmentsByDuration()
    {
        Map<Long, Integer> durationCount = new HashMap<>();

        for (int i = 0; i < appointments.size(); ++i)
        {
            Long duration = appointments.get(i).getDuration();

            if (durationCount.containsKey(duration))
                durationCount.put(duration, durationCount.get(duration) + 1);
            else
                durationCount.put(duration, 1);
        }

        return durationCount;
    }

    public Appointment searchAppointment(String name, Integer age, String sport, Long duration)
    {
        Appointment appointment = new Appointment();

        validator.validateSearch(name, age, sport, duration);

        for (int i = 0; i < appointments.size(); ++i)
        {
            if (appointments.get(i).getPerson().getName().equals(name) && appointments.get(i).getPerson().getAge().equals(age) &&
                appointments.get(i).getSport_type().equals(sport) && appointments.get(i).getDuration().equals(duration))
            {
                appointment = appointments.get(i);
            }
        }

        return appointment;
    }

    public Map<Double, Integer> countAppointmentsByPrice()
    {
        Map<Double, Integer> priceCount = new HashMap<>();

        for (int i = 0; i < appointments.size(); ++i)
        {
            Double price = appointments.get(i).getPrice();

            if (priceCount.containsKey(price))
                priceCount.put(price, priceCount.get(price) + 1);
            else
                priceCount.put(price, 1);
        }

        return priceCount;
    }

    public Map<String, Integer> countAppontmentsBySubscription()
    {
        Map<String, Integer> subscriptionCount = new HashMap<>();

        for (int i = 0; i < appointments.size(); ++i)
        {
            String subscription = appointments.get(i).getPerson().getSubscription();

            if (subscriptionCount.containsKey(subscription))
                subscriptionCount.put(subscription, subscriptionCount.get(subscription) + 1);
            else
                subscriptionCount.put(subscription, 1);
        }

        return subscriptionCount;
    }

    public Map<String, Long> countAppointmentsByActive()
    {
        Map<String, Long> activeCounter = new HashMap<>();

        List<Appointment> appointmentList = new ArrayList<>();


        for (int i = 0; i < appointments.size(); ++i)
            appointmentList.add(appointments.get(i));

        long activeCount = appointmentList.stream().filter(Appointment::isActive).count();
        long inactiveCount = appointmentList.stream().filter(appointment -> !appointment.isActive()).count();

        activeCounter.put("Active", activeCount);
        activeCounter.put("Inactive", inactiveCount);

        return activeCounter;
    }

    public ListImplementation<Appointment> getAllAppontments()
    {
        return appointments;
    }

    public int getTotalAppointments()
    {
        return appointments.size();
    }

    public List<Appointment> getAllAppointmentsAsNormalList()
    {
        List<Appointment> appointmentList = new ArrayList<>();

        for (int i = 0; i < appointments.size(); ++i)
            appointmentList.add(appointments.get(i));

        return appointmentList;
    }

    public void generateThreadAppointments(Integer number, RandomStartAppointments randomStartAppointments, RandomFilePersons randomFilePersons , Lock locker, Random random, Bank bank, FileService fileService) throws IOException {
        List<ThreadService> threadServices = new ArrayList<>();

        for (int i = 0; i < number; ++i)
        {
            ThreadService thread = new ThreadService(appointments, randomStartAppointments, randomFilePersons, locker, random, bank, fileService);
            threadServices.add(thread);
            thread.start();
        }

        for (int i = 0; i < number; ++i)
        {
            try
            {
                threadServices.get(i).join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
