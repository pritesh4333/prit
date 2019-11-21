package in.co.vyapari.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import in.co.vyapari.model.response.dto.FirmDTO;

public class Firm implements Parcelable {

    private String Id;
    private String Name;
    private String Code;
    private int GSTRegistrationType;
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
    private ArrayList<Bank> Banks;
    private String Note;
    private String WebAddress;
    private boolean IsActive;

    public Firm() {
    }

    public Firm(String id) {
        this.Id = id;
    }

    public Firm(FirmDTO firmDTO) {
        Id = firmDTO.getId();
        Name = firmDTO.getName();
        Code = firmDTO.getCode();
        GSTRegistrationType = firmDTO.getGSTRegistrationType();
        AddressText = firmDTO.getAddress();
        Country = firmDTO.getCountry();
        State = firmDTO.getState();
        City = firmDTO.getCity();
        District = firmDTO.getDistrict();
        ZipCode = firmDTO.getZipCode();
        GSTIN = firmDTO.getGSTIN();
        PAN = firmDTO.getPAN();
        Contacts = firmDTO.getContacts();
        //Employees = firmDTO.getEmployees();
        ShippingAddresses = firmDTO.getShippingAddresses();
        Banks = firmDTO.getBanks();
        Note = firmDTO.getNote();
        WebAddress = firmDTO.getWebAddress();
        IsActive = firmDTO.isActive();
    }

    protected Firm(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Code = in.readString();
        GSTRegistrationType = in.readInt();
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
        ShippingAddresses = in.createTypedArrayList(Address.CREATOR);
        Banks = in.createTypedArrayList(Bank.CREATOR);
        Note = in.readString();
        WebAddress = in.readString();
        IsActive = in.readByte() != 0;
    }

    public static final Creator<Firm> CREATOR = new Creator<Firm>() {
        @Override
        public Firm createFromParcel(Parcel in) {
            return new Firm(in);
        }

        @Override
        public Firm[] newArray(int size) {
            return new Firm[size];
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

    public int getGSTRegistrationType() {
        return GSTRegistrationType;
    }

    public void setGSTRegistrationType(int GSTRegistrationType) {
        this.GSTRegistrationType = GSTRegistrationType;
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

    public ArrayList<Address> getShippingAddresses() {
        return ShippingAddresses;
    }

    public void setShippingAddresses(ArrayList<Address> shippingAddresses) {
        ShippingAddresses = shippingAddresses;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeString(Code);
        parcel.writeInt(GSTRegistrationType);
        parcel.writeString(AddressText);
        parcel.writeParcelable(Country, i);
        parcel.writeParcelable(State, i);
        parcel.writeParcelable(City, i);
        parcel.writeParcelable(District, i);
        parcel.writeString(ZipCode);
        parcel.writeString(GSTIN);
        parcel.writeString(PAN);
        parcel.writeTypedList(Contacts);
        //parcel.writeTypedList(Employees);
        parcel.writeTypedList(ShippingAddresses);
        parcel.writeTypedList(Banks);
        parcel.writeString(Note);
        parcel.writeString(WebAddress);
        parcel.writeByte((byte) (IsActive ? 1 : 0));
    }
}
