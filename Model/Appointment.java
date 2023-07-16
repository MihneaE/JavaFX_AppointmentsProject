package Model;

import java.time.LocalDateTime;

/** IN THIS SECTION IS DEFINED APPOINTMENT CLASS, WITH FIELDS , GETTERS AND SETTERS, AND OVERRIDE FUNCTIONS EQUALS, HASHCODE, CLONE AND TO-STRING **/

public class Appointment {
    private Person person;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long duration;
    private String sport_type;
    private Double price;
    private boolean active;


    public Appointment() {}

    public Appointment(Person person, LocalDateTime start, LocalDateTime end, Long duration, String sport_type, Double price)
    {
        this.person = person;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.sport_type = sport_type;
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setSport_type(String sport_type) {
        this.sport_type = sport_type;
    }

    public String getSport_type() {
        return sport_type;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }

    public boolean personHasSubscription()
    {
        return this.person.hasSubcription();
    }

    @Override
    public String toString() {
        return "<<< Person: " + this.person + " Start_time: " + this.start + " End_time: " + this.end + " Duration: " + this.duration + " Sport_type: " + this.sport_type + " Price: " + this.price + " Is_active: " + this.active + " >>>";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Appointment))
            return false;

        Appointment appointment = (Appointment) obj;

        return person.equals(appointment.person) && start.equals(appointment.start) && end.equals(appointment.end) && duration.equals(appointment.duration) && sport_type.equals(appointment.sport_type) && price.equals(appointment.price);
    }

    @Override
    public int hashCode() {
        int hash = person.hashCode();
        hash = 31 * hash + start.hashCode();
        hash = 31 * hash + end.hashCode();
        hash = 31 * hash + duration.hashCode();
        hash = 31 * hash + sport_type.hashCode();
        hash = 31 * hash + price.hashCode();
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Appointment appointment = (Appointment) super.clone();
        appointment.start = (LocalDateTime) super.clone();
        appointment.end = (LocalDateTime) super.clone();
        return appointment;
    }
}
