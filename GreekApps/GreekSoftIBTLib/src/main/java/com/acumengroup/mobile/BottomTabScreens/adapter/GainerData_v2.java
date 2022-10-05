package com.acumengroup.mobile.BottomTabScreens.adapter;


import android.os.Parcel;
import android.os.Parcelable;

public class GainerData_v2 implements Parcelable {
    private String name;
    private String description;
    private String exchange;
    private String ltp;
    private String ltp1;
    private String ltp2;
    private String change;
    private String perchange;


    private String lotSize;
    private String assetType;
    private String volume;
    private String token;
    private String token1;
    private String token2;
    private String ticksize;
    private String multipliyer;
    private String expiry;
    private String optType;
    private String strkeprice;


    protected GainerData_v2(Parcel in) {
        name = in.readString();
        description = in.readString();
        lotSize=in.readString();
        ltp=in.readString();
        ltp1=in.readString();
        ltp2=in.readString();
        change=in.readString();
        exchange=in.readString();
        assetType=in.readString();
        volume=in.readString();
        token=in.readString();
        token1=in.readString();
        token2=in.readString();
        ticksize=in.readString();
        multipliyer=in.readString();
        strkeprice=in.readString();
        optType=in.readString();
        expiry=in.readString();
//        perchange=in.readString();


    }

    public static final Creator<GainerData_v2> CREATOR = new Creator<GainerData_v2>() {
        @Override
        public GainerData_v2 createFromParcel(Parcel in) {
            return new GainerData_v2(in);
        }

        @Override
        public GainerData_v2[] newArray(int size) {
            return new GainerData_v2[size];
        }
    };

    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    public String getPerchange() {
        return perchange;
    }

    public void setPerchange(String perchange) {
        this.perchange = perchange;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }


    public String getLtp1() {
        return ltp1;
    }

    public void setLtp1(String ltp1) {
        this.ltp1 = ltp1;
    }

    public String getLtp2() {
        return ltp2;
    }

    public void setLtp2(String ltp2) {
        this.ltp2 = ltp2;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GainerData_v2(String name) {
        this.name = name;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getTicksize() {
        return ticksize;
    }

    public void setTicksize(String ticksize) {
        this.ticksize = ticksize;
    }

    public String getMultipliyer() {
        return multipliyer;
    }

    public void setMultipliyer(String multipliyer) {
        this.multipliyer = multipliyer;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getStrkeprice() {
        return strkeprice;
    }

    public void setStrkeprice(String strkeprice) {
        this.strkeprice = strkeprice;
    }



    public GainerData_v2(String name,String token, String exchange, String ltp, String change, String perchange,String assetType) {
        this.name = name;
        this.exchange=exchange;
//       this.lotSize = lotSize;
        this.ltp = ltp;
        this.token=token;
      /*  this.ltp1 = ltp1;
        this.ltp2 = ltp2;*/
        this.change = change;
        this.perchange = perchange;
        this.assetType = assetType;
        /*this.assetType=assettype;
        this.token=token;
        this.token1=token1;
        this.token2=token2;
        this.volume=volume;
        this.ticksize=tickSize;
        this.multipliyer=multipliyer;
        this.expiry=expiry;
        this.optType=optType;
        this.strkeprice=strikeprice;*/
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(lotSize);
        dest.writeString(ltp);
        dest.writeString(ltp1);
        dest.writeString(ltp2);
        dest.writeString(change);
        dest.writeString(perchange);
        dest.writeString(assetType);
        dest.writeString(token);
        dest.writeString(token1);
        dest.writeString(token2);
        dest.writeString(volume);
        dest.writeString(ticksize);
        dest.writeString(multipliyer);
        dest.writeString(expiry);
        dest.writeString(optType);
        dest.writeString(strkeprice);

    }


}

