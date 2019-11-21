package in.co.vyapari.model.response.dto;

import android.graphics.Bitmap;

import java.util.ArrayList;

import in.co.vyapari.model.Address;
import in.co.vyapari.model.Bank;
import in.co.vyapari.model.Contact;
import in.co.vyapari.model.KeyValue;

public class CompanyDTO {

    private String Id;
    private String Name;
    private String Code;
    private String Image;
    private Bitmap ImageBitmap;
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
    private ArrayList<Address> Warehouses = new ArrayList<>();
    private ArrayList<Bank> Banks = new ArrayList<>();
    private String Note;
    private String WebAddress;
    private boolean CanEditable;
    private boolean IsActive;

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCode() {
        return Code;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Bitmap getImageBitmap() {
        /*if (getImage() != null) {
            String data = getImage().replace("\n", "");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }*/
        return null;
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

    public ArrayList<Address> getWarehouses() {
        return Warehouses;
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

    public boolean isCanEditable() {
        return CanEditable;
    }

    public boolean isActive() {
        return IsActive;
    }
}
