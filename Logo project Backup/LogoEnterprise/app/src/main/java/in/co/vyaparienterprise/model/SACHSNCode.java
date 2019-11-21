package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SACHSNCode implements Parcelable{




    /**
     * Created by Bekir.Dursun on 25.10.2017.
     */


    private String Logicalref;
        private String Key;
        private String Value;

        public SACHSNCode(String Logicalref,String key, String value) {
            Logicalref=Logicalref;
            Key = key;
            Value = value;
        }

        protected SACHSNCode(Parcel in) {
            Logicalref=in.readString();
            Key = in.readString();
            Value = in.readString();
        }

        public static final Creator<in.co.vyaparienterprise.model.SACHSNCode> CREATOR = new Creator<in.co.vyaparienterprise.model.SACHSNCode>() {
            @Override
            public in.co.vyaparienterprise.model.SACHSNCode createFromParcel(Parcel in) {
                return new in.co.vyaparienterprise.model.SACHSNCode(in);
            }

            @Override
            public in.co.vyaparienterprise.model.SACHSNCode[] newArray(int size) {
                return new in.co.vyaparienterprise.model.SACHSNCode[size];
            }
        };

        public String getKey() {
            return Key;
        }

        public String getValue() {
            return Value;
        }

        public String getLogicalref() {
        return Logicalref;
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


}
