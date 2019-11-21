package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

import in.co.vyaparienterprise.util.Utils;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class Address implements Parcelable {

    private String Id;
    private String Code;
    private String Description;
    private String AddressText;
    private KeyValue Country;
    private KeyValue State;
    private KeyValue City;
    private KeyValue District;
    private String ZipCode;

    public Address() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Address) {
            Address that = (Address) o;
            return (AddressText.equals(that.AddressText)) && Description.equals(that.Description)
                    && Utils.equalsKeyValue(Country, that.Country) && Utils.equalsKeyValue(State, that.State);
        }
        return false;
    }

    protected Address(Parcel in) {
        Id = in.readString();
        Code = in.readString();
        Description = in.readString();
        AddressText = in.readString();
        Country = in.readParcelable(KeyValue.class.getClassLoader());
        State = in.readParcelable(KeyValue.class.getClassLoader());
        City = in.readParcelable(KeyValue.class.getClassLoader());
        District = in.readParcelable(KeyValue.class.getClassLoader());
        ZipCode = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAddressText() {
        return AddressText;
    }

    public void setAddressText(String addressText) {
        AddressText = addressText;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Code);
        parcel.writeString(Description);
        parcel.writeString(AddressText);
        parcel.writeParcelable(Country, i);
        parcel.writeParcelable(State, i);
        parcel.writeParcelable(City, i);
        parcel.writeParcelable(District, i);
        parcel.writeString(ZipCode);
    }
}