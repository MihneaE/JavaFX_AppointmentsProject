package Validator;

import Model.Appointment;
import Model.ListImplementation;

import java.time.Duration;
import java.util.Objects;
import java.util.SortedSet;

/**  HERE IS THE IMPLEMENTATION OF THE VALIDATOR **/

public class Validator {

    private ListImplementation<Appointment> appointments;

    public Validator(ListImplementation<Appointment> appointments)
    {
        this.appointments = appointments;
    }

    public void validateAdd(Appointment appointment)
    {
        if (appointment.getPerson().getAge() < 0 || appointment.getPerson().getAge() > 100)
            throw new IllegalArgumentException("The value of age is ambiguous!");
        if (!Objects.equals(appointment.getPerson().getSubscription(), "YES") && !Objects.equals(appointment.getPerson().getSubscription(), "NO"))
            throw new IllegalArgumentException("Subscription must be 'YES' or 'NO'!");
        if (appointment.getStart().isAfter(appointment.getEnd()))
            throw new IllegalArgumentException("Start time must be before and time!");
        if (appointment.getEnd().isBefore(appointment.getStart()))
            throw new IllegalArgumentException("End time must be after start time!");
        if (!Objects.equals(appointment.getDuration(),  Duration.between(appointment.getStart(), appointment.getEnd()).toMinutes()))
            throw new IllegalArgumentException("Duration must be difference between start time and end time!");
        if (!Objects.equals(appointment.getSport_type(), "Baschet") && !Objects.equals(appointment.getSport_type(), "Fotbal") &&
                !Objects.equals(appointment.getSport_type(), "Volei") && !Objects.equals(appointment.getSport_type(), "Alergat") && !Objects.equals(appointment.getSport_type(), "Tenis"))
            throw new IllegalArgumentException("This sport doesn't exist in our complex!");
    }

    public void validateRemove(Appointment appointment)
    {
        if (!this.appointments.contains(appointment))
            throw new RuntimeException("This appointment does not exist!");
    }

    public void validateUpdateAppointment(Appointment appointment)
    {
        if (appointment.getPerson().getAge() < 0 || appointment.getPerson().getAge() > 100)
            throw new IllegalArgumentException("The value of age is ambiguous!");
        if (!Objects.equals(appointment.getPerson().getSubscription(), "YES") && !Objects.equals(appointment.getPerson().getSubscription(), "NO"))
            throw new IllegalArgumentException("Subscription must be 'YES' or 'NO'!");
        if (appointment.getStart().isAfter(appointment.getEnd()))
            throw new IllegalArgumentException("Start time must be before and time!");
        if (appointment.getEnd().isBefore(appointment.getStart()))
            throw new IllegalArgumentException("End time must be after start time!");
        if (!Objects.equals(appointment.getDuration(), Duration.between(appointment.getStart(), appointment.getEnd()).toMinutes()))
            throw new IllegalArgumentException("Duration must be difference between start time and end time!");
        if (!Objects.equals(appointment.getSport_type(), "Baschet") && !Objects.equals(appointment.getSport_type(), "Fotbal") &&
                !Objects.equals(appointment.getSport_type(), "Volei") && !Objects.equals(appointment.getSport_type(), "Alergat") && !Objects.equals(appointment.getSport_type(), "Handbal") && !Objects.equals(appointment.getSport_type(), "Handbal") && !Objects.equals(appointment.getSport_type(), "Tenis"))
            throw new IllegalArgumentException("This sport doesn't exist in our complex!");
    }

    public void validateFilterByAge(Integer age)
    {
        if (age < 0)
            throw new IllegalArgumentException("Age can't be negative!");
        if (age > 100)
            throw new IllegalArgumentException("Age is too high!");
    }

    public void validateFilterBySubscription(String subscription)
    {
        if (!Objects.equals(subscription, "YES") && !"NO".equals(subscription))
            throw new IllegalArgumentException("Subscription must be 'YES' or 'NO'!");
    }

    public void validateFilterBySport(String sport)
    {
        if (!Objects.equals(sport, "Baschet") && !"Fotbal".equals(sport) && !"Volei".equals(sport) && !"Alergat".equals(sport) && !"Handbal".equals(sport) && !"Tenis".equals(sport))
            throw new IllegalArgumentException("This sport does not exist!");
    }

    public void validateFilterByDuration(Long duration)
    {
        if (duration < 0)
            throw new IllegalArgumentException("Duration can't be negative!");
        if (duration > 180)
            throw new IllegalArgumentException("Duration is too high!");
    }

    public void validateFilterByPrice(Double price)
    {
        if (price < 0)
            throw new IllegalArgumentException("Price can't be negative!");
        if (price > 500)
            throw new IllegalArgumentException("Price is too high!");
    }

    public void validateFileterByNameAndAge(String name, Integer age)
    {
        if (age < 0)
            throw new IllegalArgumentException("Age cannot be negative!");
        if (age > 100)
            throw new IllegalArgumentException("Age is too high!");
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null!");
        if (age == null)
            throw new IllegalArgumentException("Age cannot be null!");
    }

    public void validateSearch(String name, Integer age, String sport, Long duration)
    {
        if (name == null || age == null || sport == null || duration == null)
            throw new IllegalArgumentException("The fields cannot be null!");
        if (age < 0 || age > 100)
            throw new IllegalArgumentException("Age must be betwneen 0 and 100!");
        if (duration < 0 || duration > 180)
            throw new IllegalArgumentException("Duration must be between 0 and 180!");
    }
}
