package RandomFile;

import Model.Appointment;
import Model.Bank;
import Model.ListImplementation;
import Model.Person;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/** RANDOM STARTING APPOINTMENTS , THESE APPEAR IN OBSERVABLE LIST WHEN THE APPLICATION STARTS **/

public class RandomStartAppointments {
    private ListImplementation<Appointment> appointments;
    private Random random;
    private RandomFilePersons randomFilePersons;
    private Map<String, Double> sportPricesPerHour;
    private Bank bank;

    public RandomStartAppointments() {}

    public RandomStartAppointments(Random random, ListImplementation<Appointment> appointments, RandomFilePersons randomFilePersons, Map<String, Double> sportPricesPerHour, Bank bank)
    {
        this.random = random;
        this.appointments = appointments;
        this.randomFilePersons = randomFilePersons;
        this.sportPricesPerHour = sportPricesPerHour;
        this.bank = bank;
    }

    public Map<String, Double> getSportPricesPerHour()
    {
        sportPricesPerHour.put("Fotbal", 30.0);
        sportPricesPerHour.put("Volei", 40.00);
        sportPricesPerHour.put("Handbal", 30.00);
        sportPricesPerHour.put("Tenis", 60.00);
        sportPricesPerHour.put("Alergat", 20.00);
        sportPricesPerHour.put("Baschet", 50.00);

        return sportPricesPerHour;
    }

    public LocalDateTime generateRandomDateTime()
    {
        long minDay = LocalDateTime.of(2023, Month.JULY, 12, 10, 0).toEpochSecond(ZoneOffset.UTC);
        long maxDay = LocalDateTime.of(2023, Month.JULY, 15, 23, 0).toEpochSecond(ZoneOffset.UTC);
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        return LocalDateTime.ofEpochSecond(randomDay, 0, java.time.ZoneOffset.UTC);
    }

    public int generateRandomDurationMinutes()
    {
        int[] minutes = {60, 90, 120, 150, 180};
        int randomIndex = ThreadLocalRandom.current().nextInt(0, minutes.length);

        return minutes[randomIndex];
    }

    public Double calculatePrice(String sport_type, Long durationMinutes)
    {
        Double pricePerHour = getSportPricesPerHour().get(sport_type);
        double durationHours = Integer.parseInt(String.valueOf(durationMinutes)) / 60.00;

        return pricePerHour * durationHours;
    }

    public void generateStartAppointmentsFile() throws IOException {
        ListImplementation<Person> persons = randomFilePersons.loadPersons();

        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("appointments.txt"));

            for (int i = 0; i < 10; ++i)
            {
                Person person = persons.get(random.nextInt(persons.size()));
                LocalDateTime start = generateRandomDateTime();
                LocalDateTime end = start.plus(generateRandomDurationMinutes(), ChronoUnit.MINUTES);
                Long duration = ChronoUnit.MINUTES.between(start, end);
                String[] sports = {"Fotbal", "Baschet", "Tenis", "Volei", "Alergat", "Handbal"};
                String sport_type = sports[random.nextInt(sports.length)];
                Double price = calculatePrice(sport_type, duration);

                LocalDateTime currentTime = LocalDateTime.now();
                boolean activeAppointment = currentTime.isAfter(start) && currentTime.isBefore(end);

                Appointment appointment = new Appointment();
                appointment.setPerson(person);
                appointment.setStart(start);
                appointment.setEnd(end);
                appointment.setDuration(duration);
                appointment.setSport_type(sport_type);
                appointment.setPrice(price);
                appointment.setActive(activeAppointment);

                bufferedWriter.write(appointment.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ListImplementation<Appointment> loadAppointments() throws IOException {
        List<String> fileAppointments = Files.readAllLines(Paths.get("appointments.txt"));

        for (String line : fileAppointments)
        {
            String args[] = line.split(" ");
            Person person = new Person();

            person.setName(args[4]);
            person.setAge(Integer.valueOf(args[6]));
            person.setSubscription(args[8]);
            person.setBalance(Double.parseDouble(args[10]));

            Appointment appointment = new Appointment();

            appointment.setPerson(person);
            appointment.setStart(LocalDateTime.parse(args[13]));
            appointment.setEnd(LocalDateTime.parse(args[15]));
            appointment.setDuration(Long.parseLong(args[17]));
            appointment.setSport_type(args[19]);
            appointment.setPrice(Double.valueOf(args[21]));
            appointment.setActive(Boolean.parseBoolean(args[23]));

            double newBalance = appointment.getPerson().getBalance() - appointment.getPrice();
            appointment.getPerson().setBalance(newBalance);

            if (appointment.personHasSubscription())
                bank.addToBank(appointment.getPrice() / 2);
            else
                bank.addToBank(appointment.getPrice());

            appointments.add(appointment);
        }

        return appointments;
    }
}
