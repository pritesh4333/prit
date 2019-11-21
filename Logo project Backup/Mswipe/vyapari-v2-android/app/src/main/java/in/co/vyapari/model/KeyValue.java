package in.co.vyapari.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bekir.Dursun on 31.10.2017.
 */

public class KeyValue implements Parcelable{

    private String Key;
    private String Value;
      private String Logicalref;

    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        Key = key;
        Value = value;
    }
    public KeyValue(String key, String value,String Logicref) {
        Key = key;
        Value = value;
        Logicalref=Logicref;
    }

    protected KeyValue(Parcel in) {
        Key = in.readString();
        Value = in.readString();
        Logicalref=in.readString();
    }


    public static final Creator<KeyValue> CREATOR = new Creator<KeyValue>() {
        @Override
        public KeyValue createFromParcel(Parcel in) {
            return new KeyValue(in);
        }

        @Override
        public KeyValue[] newArray(int size) {
            return new KeyValue[size];
        }
    };

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Key);
        parcel.writeString(Value);
        parcel.writeString(Logicalref);
    }

    public String getLogicalref() {
        return Logicalref;
    }

    public void setLogicalref(String logicalref) {
        Logicalref = logicalref;
    }
}
