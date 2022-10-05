package com.acumengroup.mobile.BottomTabScreens.adapter;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.mobile.BottomTabScreens.adapter.holder.GainerDataHolder;
import com.acumengroup.mobile.BottomTabScreens.adapter.holder.GainerTitleHolder;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.SubHeader_v2;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverViewRecycleAdapter extends ExpandableRecyclerViewAdapter<GainerTitleHolder, GainerDataHolder> {

    private AppCompatActivity activity;
    List<? extends ExpandableGroup> groups;
    GainerDataHolder holder;
    ExpandableGroup group;
    HashMap<String, ArrayList<String>> tokenList;

    public OverViewRecycleAdapter(AppCompatActivity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
        this.groups = groups;
        tokenList = new HashMap<String, ArrayList<String>>();
    }


    @Override
    public GainerTitleHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.parent_header_row, parent, false);
        return new GainerTitleHolder(view,activity);
    }


    @Override
    public GainerDataHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.overview_child, parent, false);
        return new GainerDataHolder(view);
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

    public void addSymbol(String groupName, ArrayList<String> token) {

        tokenList.put(groupName, token);
    }

    @Override
    public void onBindChildViewHolder(GainerDataHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        this.holder = holder;
        this.group = group;
        final SubHeader_v2 data = ((GainerMainHeader_v2) group).getItems().get(childIndex);

        holder.onBind(data.getmItemListArray(), group, activity);
    }

    @Override
    public void onBindGroupViewHolder(GainerTitleHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }

    public GainerData getItem(int groupIndex, int childIndex) {

        GainerData data = (GainerData) groups.get(groupIndex).getItems().get(childIndex);
        return data;

    }


    //do Not Delete.

   /* public GainerData getItem(int position) {



        GainerData data = ((GainerHeader) group).getItems().get(position);

//        GainerData data = ((GainerHeader) groups).getItems().get(childIndex);
        return data;

    }*/


}


