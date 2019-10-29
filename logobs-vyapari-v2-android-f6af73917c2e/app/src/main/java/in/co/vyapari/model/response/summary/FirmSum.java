package in.co.vyapari.model.response.summary;

import java.util.ArrayList;

import in.co.vyapari.model.Contact;
import in.co.vyapari.model.Employee;
import in.co.vyapari.model.Firm;

public class FirmSum {

    private String Id;
    private String Type;
    private String Name;
    private String State;
    private String City;
    private double Balance;
    private double Debit;
    private double Credit;
    private ArrayList<Contact> Contacts;
    private ArrayList<Employee> Employees;
    private boolean IsActive;

    public FirmSum(Firm firm) {
        this.Id = firm.getId();
        this.Name = firm.getName();
        this.State = firm.getState().getValue();
        this.City = firm.getCity().getValue();
        this.Contacts = firm.getContacts();
        this.IsActive = firm.isActive();
    }

    public String getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public String getState() {
        return State;
    }

    public String getCity() {
        return City;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public double getDebit() {
        return Debit;
    }

    public double getCredit() {
        return Credit;
    }

    public ArrayList<Contact> getContacts() {
        return Contacts;
    }

    public ArrayList<Employee> getEmployees() {
        return Employees;
    }

    public boolean isActive() {
        return IsActive;
    }
}
