package Model;

/** IN THIS SECTION IS DEFINED PERSON CLASS, WITH FIELDS, GETTER AND SETTERS, AND OVERRIDE FUNCTIONS CLONE, TO-STRING, HASHCODE AND EQUALS **/

public class Person {
    private String name;
    private Integer age;
    private String subscription;
    private Double balance;
    private boolean hasSubcription;

    public Person() {}

    public Person(String name, Integer age, String subscription, Double balance)
    {
        this.name = name;
        this.age = age;
        this.subscription = subscription;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Double getBalance() {
        return balance;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public boolean hasSubcription()
    {
        return this.hasSubcription;
    }


    @Override
    public String toString() {
        return "<<< " + "Name: " + name + " Age: " + age + " Subscription: " + subscription + " Balance: " + balance + " >>>";
    }

    public String toString2()
    {
        return "Name: " + name + "Age: " + age + "Subscription: " + subscription + "Balance: " + balance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();

        return person;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Person))
            return false;

        Person p = (Person) obj;

        return name.equals(p.name) && age.equals(p.age) && subscription.equals(p.subscription) && balance.equals(p.balance);
    }

    @Override
    public int hashCode() {
        int hash = name.hashCode();
        System.out.println(hash);
        hash = 31 * hash + age.hashCode();
        hash = 31 * hash + subscription.hashCode();
        hash = 31 * hash + balance.hashCode();
        return hash;
    }
}
