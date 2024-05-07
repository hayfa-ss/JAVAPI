package entities;


import java.util.Date;

public class Payment {
   private int id;
   private String firstName;
   private String lastName;
   private String email;
   private String paymentOptions;
   private Date date;
   private int amount ;

    public Payment() {
    }

    public Payment(String firstName, String lastName, String email, String paymentOptions, Date date, int amount) {
        this.amount = amount;
        this.date = date;
        this.paymentOptions = paymentOptions;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Payment(int id, String firstName, String lastName, String email, String paymentOptions, Date date, int amount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.paymentOptions = paymentOptions;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(String paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
