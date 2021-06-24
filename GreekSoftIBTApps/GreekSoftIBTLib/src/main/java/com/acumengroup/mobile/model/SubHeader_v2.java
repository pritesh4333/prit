package com.acumengroup.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData_v2;

import java.util.ArrayList;

public class SubHeader_v2 implements Parcelable {

    String color;
    ArrayList<GainerData_v2> mItemListArray;

    public SubHeader_v2(String color, ArrayList<GainerData_v2> mItemListArray) {
        this.color = color;
        this.mItemListArray = mItemListArray;
    }

    protected SubHeader_v2(Parcel in) {
        color = in.readString();
        mItemListArray = in.createTypedArrayList(GainerData_v2.CREATOR);
    }

    public static final Creator<SubHeader_v2> CREATOR = new Creator<SubHeader_v2>() {
        @Override
        public SubHeader_v2 createFromParcel(Parcel in) {
            return new SubHeader_v2(in);
        }

        @Override
        public SubHeader_v2[] newArray(int size) {
            return new SubHeader_v2[size];
        }
    };

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<GainerData_v2> getmItemListArray() {
        return mItemListArray;
    }

    public void setmItemListArray(ArrayList<GainerData_v2> mItemListArray) {
        this.mItemListArray = mItemListArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeTypedList(mItemListArray);
    }
}
