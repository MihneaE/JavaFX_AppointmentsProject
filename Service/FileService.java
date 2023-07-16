package Service;

import Model.Appointment;
import Model.ListImplementation;
import Model.Person;
import RandomFile.RandomFilePersons;

import java.io.*;
import java.util.Random;

/** FILE SERVICE IMPLEMENTATION **/

public class FileService {

    private BufferedWriter writerAppointments;
    private BufferedWriter writePersons;
    private RandomFilePersons randomFilePersons;

    public FileService(RandomFilePersons randomFilePersons) throws IOException {

        this.writerAppointments = new BufferedWriter(new FileWriter("appointments___.txt"));
        this.writePersons = new BufferedWriter(new FileWriter("persons___.txt"));
        this.randomFilePersons = randomFilePersons;
    }

    public void addAppointmentToFile(ListImplementation<Appointment> appointments) {

        try
        {
            for (int i = 0; i < appointments.size(); ++i)
            {
                writerAppointments.write(appointments.get(i).toString());
                writerAppointments.newLine();
            }

            writerAppointments.flush();
            writerAppointments.write("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addPersonToFile(Person person)
    {
        boolean Ok = false;

        for (int i = 0; i < randomFilePersons.getPersons().size(); ++i)
        {
            if (person.equals(randomFilePersons.getPersons().get(i)))
                Ok = true;
        }

        if (!Ok)
        {
            randomFilePersons.getPersons().add(person);

            try
            {
                for (int i = 0; i < randomFilePersons.getPersons().size(); ++i)
                {
                    writePersons.write(randomFilePersons.getPersons().get(i).toString());
                    writePersons.newLine();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void removeAppointmentFromFile(Appointment appointment)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("appointments.txt"));

            String lineToRemove = appointment.toString();
            String currentLine;

            while ((currentLine = reader.readLine()) != null)
            {
                String trimLine = currentLine.trim();

                if (trimLine.equals(lineToRemove))
                    continue;

                writerAppointments.write(trimLine);
                writerAppointments.newLine();
            }

            writerAppointments.flush();
            writerAppointments.write("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void updateAppointmentToFile(ListImplementation<Appointment> appointments, int index, Appointment appointment)
    {
        try
        {
            for (int i = 0; i < index; ++i)
            {
                writerAppointments.write(appointments.get(i).toString());
                writerAppointments.newLine();
            }

            writerAppointments.write(appointment.toString());
            writerAppointments.newLine();

            for (int i = index + 1; i < appointments.size(); ++i)
            {
                writerAppointments.write(appointments.get(i).toString());
                writerAppointments.newLine();
            }

            writerAppointments.flush();
            writerAppointments.write("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void updatePersonsAfterOperation(ListImplementation<Person> persons, Person newPerson)
    {
        File file = new File("persons.txt");

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (int i = 0; i < persons.size(); ++i)
            {
                if (persons.get(i).equals(newPerson))
                {
                    writer.write(newPerson.toString2());
                    writer.newLine();
                }
                else
                {
                    writer.write(persons.get(i).toString2());
                    writer.newLine();
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
