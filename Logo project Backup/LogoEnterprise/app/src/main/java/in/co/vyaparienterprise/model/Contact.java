package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class Contact implements Parcelable {

    private String Id;
    private int Type;
    private String Value;

    public Contact(int Type, String Value) {
        this.Type = Type;
        this.Value = Value;
    }

    protected Contact(Parcel in) {
        Id = in.readString();
        Type = in.readInt();
        Value = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getId() {
        return Id;
    }

    public int getType() {
        return Type;
    }

    public String getValue() {
        return Value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeInt(Type);
        parcel.writeString(Value);
    }
}
