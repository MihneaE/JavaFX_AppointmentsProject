package Model;

/** BANK IMPLEMENTATION, RESPONSABLE FOR TOTAL AMOUNT**/
public class Bank {
    private Double totalAmount;

    public Bank()
    {
        this.totalAmount = 0.0;
    }

    public Bank(Double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public synchronized void addToBank(Double ammount)
    {
        this.totalAmount += ammount;
    }

    public synchronized void lessFromBank(Double ammount)
    {
        this.totalAmount -= ammount;
    }
}
