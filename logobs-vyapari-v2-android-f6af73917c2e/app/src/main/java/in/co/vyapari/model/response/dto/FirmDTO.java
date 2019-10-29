package in.co.vyapari.model.response.dto;

import java.util.ArrayList;

import in.co.vyapari.model.Address;
import in.co.vyapari.model.Bank;
import in.co.vyapari.model.Contact;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.KeyValue;

public class FirmDTO {

    private String Id;
    private String Name;
    private String Code;
    private int GSTRegistrationType;
    private String DisplayName;
    private String AddressText;
    private KeyValue Country;
    private KeyValue State;
    private KeyValue City;
    private KeyValue District;
    private String ZipCode;
    private String GSTIN;
    private String PAN;
    private ArrayList<Contact> Contacts = new ArrayList<>();
    //private ArrayList<Employee> Employees = new ArrayList<>();
    private ArrayList<Address> ShippingAddresses = new ArrayList<>();
    private ArrayList<Bank> Banks = new ArrayList<>();
    private String Note;
    private String WebAddress;
    private boolean IsActive;

    public FirmDTO(Firm firm) {
        if (firm != null) {
            Id = firm.getId();
            Name = firm.getName();
            Code = firm.getCode();
            AddressText = firm.getAddress();
            Country = firm.getCountry();
            State = firm.getState();
            City = firm.getCity();
            District = firm.getDistrict();
            ZipCode = firm.getZipCode();
            GSTIN = firm.getGSTIN();
            PAN = firm.getPAN();
            Contacts = firm.getContacts();
            //Employees = firmDTO.getEmployees();
            ShippingAddresses = firm.getShippingAddresses();
            Banks = firm.getBanks();
            Note = firm.getNote();
            WebAddress = firm.getWebAddress();
            IsActive = firm.isActive();
        }
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCode() {
        return Code;
    }

    public int getGSTRegistrationType() {
        return GSTRegistrationType;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public String getAddress() {
        return AddressText;
    }

    public KeyValue getCountry() {
        return Country;
    }

    public KeyValue getState() {
        return State;
    }

    public KeyValue getCity() {
        return City;
    }

    public KeyValue getDistrict() {
        return District;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public String getGSTIN() {
        return GSTIN;
    }

    public String getPAN() {
        return PAN;
    }

    public ArrayList<Contact> getContacts() {
        return Contacts;
    }

    /*public ArrayList<Employee> getEmployees() {
        return Employees;
    }*/

    public ArrayList<Address> getShippingAddresses() {
        return ShippingAddresses;
    }

    public ArrayList<Bank> getBanks() {
        return Banks;
    }

    public String getNote() {
        return Note;
    }

    public String getWebAddress() {
        return WebAddress;
    }

    public boolean isActive() {
        return IsActive;
    }
}
