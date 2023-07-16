package RandomFile;

import Model.ListImplementation;
import Model.Person;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/** RANDOM PERSON GENERATOR, LIKE A MINI DATABASE **/
public class RandomFilePersons {
    private Random random;
    ListImplementation<Person> persons;

    public RandomFilePersons(Random random, ListImplementation<Person> persons)
    {
        this.random = random;
        this.persons = persons;
    }

    public void generatePersons()
    {
        String[] names = {"PETRU", "ION", "VASILE", "MIHAI", "GEORGE", "ALINA", "ANAMARIA", "ELENA", "MARIA", "GABRIELA", "VLAD", "ALEX", "MATEI", "COR", "FLAVIU", "ARTHUR", "ROBERT", "ANDREI", "DARIUS"};
        String[] subscriptions = {"YES", "NO"};

        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("persons.txt"));

            for (int i = 0; i < 20; ++i)
            {
                String name = names[random.nextInt(names.length)];
                String subscription = subscriptions[random.nextInt(subscriptions.length)];
                int age = 18 + random.nextInt(50);
                int balance = 2000 + random.nextInt(3000);

                String person = String.format("Name: %s Age: %d Subscription: %s Balance: %d", name, age, subscription, balance);

                bufferedWriter.write(person);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public ListImplementation<Person> loadPersons() throws IOException {
        List<String> filePersons = Files.readAllLines(Paths.get("persons.txt"));

        for (String line : filePersons)
        {
            String[] args = line.split(" ");
            Person person = new Person();

            person.setName(args[1]);
            person.setAge(Integer.valueOf(args[3]));
            person.setSubscription(args[5]);
            person.setBalance(Double.valueOf(args[7]));

            persons.add(person);
        }

        return persons;
    }

    public ListImplementation<Person> getPersons() {
        return persons;
    }
}
