package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Company implements Parcelable {

    private String Id;
    private String Name;
    private String Code;
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
    private boolean IsActive;

    public Company() {
    }

    protected Company(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Code = in.readString();
        AddressText = in.readString();
        Country = in.readParcelable(KeyValue.class.getClassLoader());
        State = in.readParcelable(KeyValue.class.getClassLoader());
        City = in.readParcelable(KeyValue.class.getClassLoader());
        District = in.readParcelable(KeyValue.class.getClassLoader());
        ZipCode = in.readString();
        GSTIN = in.readString();
        PAN = in.readString();
        Contacts = in.createTypedArrayList(Contact.CREATOR);
        //Employees = in.createTypedArrayList(Employee.CREATOR);
        Warehouses = in.createTypedArrayList(Address.CREATOR);
        Banks = in.createTypedArrayList(Bank.CREATOR);
        Note = in.readString();
        WebAddress = in.readString();
        IsActive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
        dest.writeString(Code);
        dest.writeString(AddressText);
        dest.writeParcelable(Country, flags);
        dest.writeParcelable(State, flags);
        dest.writeParcelable(City, flags);
        dest.writeParcelable(District, flags);
        dest.writeString(ZipCode);
        dest.writeString(GSTIN);
        dest.writeString(PAN);
        dest.writeTypedList(Contacts);
        //dest.writeTypedList(Employees);
        dest.writeTypedList(Warehouses);
        dest.writeTypedList(Banks);
        dest.writeString(Note);
        dest.writeString(WebAddress);
        dest.writeByte((byte) (IsActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getAddress() {
        return AddressText;
    }

    public void setAddress(String address) {
        AddressText = address;
    }

    public KeyValue getCountry() {
        return Country;
    }

    public void setCountry(KeyValue country) {
        Country = country;
    }

    public KeyValue getState() {
        return State;
    }

    public void setState(KeyValue state) {
        State = state;
    }

    public KeyValue getCity() {
        return City;
    }

    public void setCity(KeyValue city) {
        City = city;
    }

    public KeyValue getDistrict() {
        return District;
    }

    public void setDistrict(KeyValue district) {
        District = district;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getGSTIN() {
        return GSTIN;
    }

    public void setGSTIN(String GSTIN) {
        this.GSTIN = GSTIN;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public ArrayList<Contact> getContacts() {
        return Contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        Contacts = contacts;
    }

  /*public ArrayList<Employee> getEmployees() {
        return Employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        Employees = employees;
    }*/

    public ArrayList<Address> getWarehouses() {
        return Warehouses;
    }

    public void setWarehouses(ArrayList<Address> shippingAddresses) {
        Warehouses = shippingAddresses;
    }

    public ArrayList<Bank> getBanks() {
        return Banks;
    }

    public void setBanks(ArrayList<Bank> banks) {
        Banks = banks;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getWebAddress() {
        return WebAddress;
    }

    public void setWebAddress(String webAddress) {
        WebAddress = webAddress;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

}
