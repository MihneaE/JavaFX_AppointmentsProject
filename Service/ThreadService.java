package Service;

import Model.Appointment;
import Model.Bank;
import Model.ListImplementation;
import Model.Person;
import RandomFile.RandomFilePersons;
import RandomFile.RandomStartAppointments;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.locks.Lock;

/** THREAD SERVICE IMPLEMENTATION, RESPONSIBLE FOR GENERATE N APPOINTMENTS **/
public class ThreadService extends Thread {
    private final ListImplementation<Appointment> appointments;
    private final RandomStartAppointments randomStartAppointments;
    private final RandomFilePersons randomFilePersons;
    private final Random random;
    private final Lock locker;
    private final Bank bank;
    private ListImplementation<Person> persons;
    private FileService fileService;

    public ThreadService(ListImplementation<Appointment> appointments, RandomStartAppointments randomStartAppointments, RandomFilePersons randomFilePersons, Lock locker, Random random, Bank bank, FileService fileService) throws IOException {
        this.appointments = appointments;
        this.randomStartAppointments = randomStartAppointments;
        this.randomFilePersons = randomFilePersons;
        this.locker = locker;
        this.random = random;
        this.bank = bank;
        this.persons = randomFilePersons.loadPersons();
        this.fileService = fileService;
    }

    @Override
    public void run() {

        Person person = persons.get(random.nextInt(persons.size()));
        LocalDateTime start = randomStartAppointments.generateRandomDateTime();
        LocalDateTime end = start.plus(randomStartAppointments.generateRandomDurationMinutes(), ChronoUnit.MINUTES);
        Long duration = ChronoUnit.MINUTES.between(start, end);
        String[] sports = {"Fotbal", "Baschet", "Tenis", "Volei", "Alergat", "Handbal"};
        String sport_type = sports[random.nextInt(sports.length)];
        Double price = randomStartAppointments.calculatePrice(sport_type, duration);

        Appointment appointment = new Appointment();
        appointment.setPerson(person);
        appointment.setStart(start);
        appointment.setEnd(end);
        appointment.setDuration(duration);
        appointment.setSport_type(sport_type);
        appointment.setPrice(price);

        LocalDateTime currentTime = LocalDateTime.now();
        boolean activeAppointment = currentTime.isAfter(start) && currentTime.isBefore(end);
        appointment.setActive(activeAppointment);

        synchronized (appointment.getPerson())
        {
            Double newBalance = person.getBalance() - appointment.getPrice();
            appointment.getPerson().setBalance(newBalance);

            Person newPerson = appointment.getPerson();
            fileService.updatePersonsAfterOperation(persons, newPerson);
        }

        synchronized (bank)
        {
            if (appointment.personHasSubscription())
                bank.addToBank(appointment.getPrice() / 2);
            else
                bank.addToBank(appointment.getPrice());
        }

        synchronized (locker) {
            this.appointments.add(appointment);
        }
    }
}

