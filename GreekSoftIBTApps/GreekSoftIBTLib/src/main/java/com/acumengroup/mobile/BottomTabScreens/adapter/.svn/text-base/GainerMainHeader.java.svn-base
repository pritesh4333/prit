package com.acumengroup.mobile.BottomTabScreens.adapter;

import com.acumengroup.mobile.model.SubHeader;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;


public class GainerMainHeader extends ExpandableGroup<SubHeader> {


   private String marketType;
   private ArrayList<SubHeader> childList;

    public GainerMainHeader(ArrayList<SubHeader> items, String marketType) {
        super(marketType,items);
        this.childList=items;

    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public ArrayList<SubHeader> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<SubHeader> childList) {
        this.childList = childList;
    }
}
