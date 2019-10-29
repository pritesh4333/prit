package in.co.vyapari.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class Currency implements Parcelable{

    private String Key;
    private String Value;

    public Currency(String key, String value) {
        Key = key;
        Value = value;
    }

    protected Currency(Parcel in) {
        Key = in.readString();
        Value = in.readString();
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    public String getKey() {
        return Key;
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
        parcel.writeString(Key);
        parcel.writeString(Value);
    }
}
