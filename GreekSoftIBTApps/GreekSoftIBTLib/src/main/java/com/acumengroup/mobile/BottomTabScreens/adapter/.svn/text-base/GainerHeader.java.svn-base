package com.acumengroup.mobile.BottomTabScreens.adapter;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;


public class GainerHeader extends ExpandableGroup<GainerData> {


   private String marketType;
   private ArrayList<GainerData> childList;

    public GainerHeader(ArrayList<GainerData> items, String marketType) {
        super(marketType,items);
        this.childList=items;
        this.marketType=marketType;

    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public ArrayList<GainerData> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<GainerData> childList) {
        this.childList = childList;
    }
}
