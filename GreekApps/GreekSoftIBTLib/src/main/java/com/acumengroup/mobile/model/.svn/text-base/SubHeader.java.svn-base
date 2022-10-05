package com.acumengroup.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.acumengroup.mobile.BottomTabScreens.adapter.GainerData;

import java.util.ArrayList;

public class SubHeader implements Parcelable {

    String color;
    ArrayList<GainerData> mItemListArray;

    public SubHeader(String color, ArrayList<GainerData> mItemListArray) {
        this.color = color;
        this.mItemListArray = mItemListArray;
    }

    protected SubHeader(Parcel in) {
        color = in.readString();
        mItemListArray = in.createTypedArrayList(GainerData.CREATOR);
    }

    public static final Creator<SubHeader> CREATOR = new Creator<SubHeader>() {
        @Override
        public SubHeader createFromParcel(Parcel in) {
            return new SubHeader(in);
        }

        @Override
        public SubHeader[] newArray(int size) {
            return new SubHeader[size];
        }
    };

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<GainerData> getmItemListArray() {
        return mItemListArray;
    }

    public void setmItemListArray(ArrayList<GainerData> mItemListArray) {
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
