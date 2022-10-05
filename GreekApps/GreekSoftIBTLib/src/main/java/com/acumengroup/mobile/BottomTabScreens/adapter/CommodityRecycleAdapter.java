package com.acumengroup.mobile.BottomTabScreens.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.mobile.BottomTabScreens.adapter.holder.CommodityDataHolder;
import com.acumengroup.mobile.BottomTabScreens.adapter.holder.GainerTitleHolder;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SubHeader;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommodityRecycleAdapter extends ExpandableRecyclerViewAdapter<GainerTitleHolder, CommodityDataHolder> {

    private AppCompatActivity activity;
    List<? extends ExpandableGroup> groups;
    CommodityDataHolder holder;
    ExpandableGroup group;
    HashMap<String, ArrayList<String>> tokenList;
    HashMap<String, ArrayList<String>> dataList;

    public CommodityRecycleAdapter(AppCompatActivity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
        this.groups = groups;
        tokenList = new HashMap<String, ArrayList<String>>();
        dataList = new HashMap<String, ArrayList<String>>();

    }


    @Override
    public GainerTitleHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.parent_header_row, parent, false);
        return new GainerTitleHolder(view,activity);
    }


    @Override
    public CommodityDataHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.derivative_child, parent, false);
        return new CommodityDataHolder(view);
    }

    public boolean containsSymbol(String groupName, String token) {

        if (tokenList.containsKey(groupName)) {

            ArrayList<String> list = tokenList.get(groupName);

            return list.contains(token);
        } else
            return false;
    }

    public int indexOf(String groupName, String token) {

        return tokenList.get(groupName).indexOf(token);
    }

    public void addSymbol(String groupName, String chilgroupName, ArrayList<String> token) {

        dataList.put(chilgroupName, token);
        tokenList.put(groupName, token);
    }

    @Override
    public void onBindChildViewHolder(CommodityDataHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        this.holder = holder;
        this.group = group;
//        final SubHeader_v2 data = ((GainerMainHeader_v2) group).getItems().get(childIndex);

        final SubHeader phone = ((GainerMainHeader) group).getItems().get(childIndex);

        holder.onBind(phone.getmItemListArray(), phone.getColor(), group, activity);
    }

    @Override
    public void onBindGroupViewHolder(GainerTitleHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }


    public void expandAllGroups1() {

        onGroupClick(expandableList.getFlattenedGroupIndex(expandableList.getUnflattenedPosition(2)));
    }


    public GainerData getItem(int groupIndex, int childIndex) {

        GainerData data = (GainerData) groups.get(groupIndex).getItems().get(childIndex);
        return data;

    }


}


