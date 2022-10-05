
package com.acumengroup.mobile.BottomTabScreens.adapter;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.mobile.BottomTabScreens.adapter.holder.DerivativeChildDataHolder;
import com.acumengroup.mobile.BottomTabScreens.adapter.holder.GainerTitleHolder;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.UserKycValidation;
import com.acumengroup.mobile.model.SubHeader;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DerivativeRecycleAdapter extends ExpandableRecyclerViewAdapter<GainerTitleHolder, DerivativeChildDataHolder> {

    private Context context;
    private List<? extends ExpandableGroup> groups;
    private HashMap<String, HashMap<String, ArrayList<String>>> tokenList;
    private HashMap<String, ArrayList<String>> dataList;
    private DerivativeChildDataHolder holder;
    private ExpandableGroup group;
    private ServiceResponseHandler serviceResponseHandler;
    private ArrayList<String> expiryList;
    private GainerTitleHolder titleHolder;
    public static boolean isExpandedOIA;
    public static JSONObject jsonObject;

    public DerivativeRecycleAdapter(Context context, ArrayList<String> expiryList, List<? extends ExpandableGroup> groups, ServiceResponseHandler serviceResponseHandler) {
        super(groups);
        this.context = context;
        this.groups = groups;
        this.expiryList = expiryList;
        tokenList = new HashMap<String, HashMap<String, ArrayList<String>>>();
        dataList = new HashMap<String, ArrayList<String>>();
        this.serviceResponseHandler = serviceResponseHandler;
    }

    public boolean containsSymbol(String groupName, String childGroupName, String token) {
        try {
            if (tokenList.get(groupName).containsKey(childGroupName)) {

                ArrayList<String> list = tokenList.get(groupName).get(childGroupName);

                if (list != null) {
                    return list.contains(token);

                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch(Exception e){
            Log.e("containsSymbol","containsSymbol");
        }
        return false;
    }

    public int indexOf(String groupName, String childGroupName, String token) {

        return tokenList.get(groupName).get(childGroupName).indexOf(token);
    }

    public void addSymbol(String groupName, String chilgroupName, ArrayList<String> token) {
        try{
            dataList.put(chilgroupName, token);
            tokenList.put(groupName, dataList);
        }catch(Exception e){
            Log.e("derivative","GainerData");
        }
    }


    public GainerData getItem(int groupIndex, int childGroupIndex, int childIndex) {
        try {
            SubHeader data = (SubHeader) groups.get(groupIndex).getItems().get(childGroupIndex);
            return data.getmItemListArray().get(childIndex);
        }catch(Exception e){
            Log.e("derivative","GainerData");
        }
        return  null;
    }


    @Override
    public GainerTitleHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.parent_header_derivative, parent, false);
            GainerTitleHolder gainerTitleHolder = new GainerTitleHolder(view, context);
            return gainerTitleHolder;
        }catch(Exception e){
            Log.e("derivative","GainerTitleHolder");
        }
        return  null;
    }


    @Override
    public DerivativeChildDataHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.derivative_child_layout, parent, false);

            return new DerivativeChildDataHolder(view,jsonObject);
        }catch(Exception e){

            Log.e("derivative","DerivativeChildDataHolder");
        }
        return null;

    }
    public void getDataRollOverIndexData(JSONObject response){
        this.jsonObject=response;
    }
    @Override
    public void onBindChildViewHolder(DerivativeChildDataHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        try {
            this.holder = holder;
            this.group = group;
            final SubHeader phone = ((GainerMainHeader) group).getItems().get(childIndex);

            if (isGroupExpanded(group)) {
                titleHolder.collapse();
                isExpandedOIA = group.getTitle().equalsIgnoreCase("OPEN INTEREST ANALYSIS");
            } else {
                titleHolder.expand();
                isExpandedOIA = false;
            }
            holder.onBind(phone.getmItemListArray(), expiryList, phone.getColor(), group, context,
                    flatPosition, childIndex, serviceResponseHandler);
        }catch (Exception e){
            Log.e("derivative","date change fast scrolling issue catched");
        }

    }


    @Override
    public void onBindGroupViewHolder(GainerTitleHolder holder, int flatPosition, ExpandableGroup group) {
        try{
            this.titleHolder = holder;
            holder.setGroupName(group);
            if (isGroupExpanded(group)) {
                holder.collapse();
                isExpandedOIA = group.getTitle().equalsIgnoreCase("OPEN INTEREST ANALYSIS");
            } else {
                holder.expand();
                isExpandedOIA = false;
            }

            if (isGroupExpanded(group)) {
                holder.collapse();
            } else {
                holder.expand();
            }
            if (isGroupExpanded(group)) {
                holder.collapse();
            } else {
                holder.expand();
            }
            if (isGroupExpanded(group)) {
                holder.collapse();
            } else {
                holder.expand();
            }
        }catch (Exception e){
            Log.e("onBindGroupViewHolder","onBindGroupViewHolder");
        }
    }


}
