package com.acumengroup.mobile.menu;

import android.content.Context;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.menu.ScreenDetails;
import com.acumengroup.ui.menu.SubMenu;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MenuAdapter extends BaseExpandableListAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<ScreenDetails> groupList;
    private Map<String, Vector<SubMenu>> childCollection;
    private int groupItemSelection = -1;
    private int childItemSelection = -1;

    public MenuAdapter(AppCompatActivity context, List<ScreenDetails> groups, Map<String, Vector<SubMenu>> menuCollections) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.groupList = groups;
        this.childCollection = menuCollections;
    }

    public void updateMenu(List<ScreenDetails> groups, Map<String, Vector<SubMenu>> menuCollections) {
        clear();
        this.groupList = groups;
        this.childCollection = menuCollections;
        notifyDataSetChanged();
    }

    public void clear() {
        this.groupList.clear();
        this.childCollection.clear();
        this.groupItemSelection = -1;
        this.childItemSelection = -1;
        notifyDataSetChanged();
    }

    public SubMenu getChild(int groupPosition, int childPosition) {
        ScreenDetails scr = groupList.get(groupPosition);
        return childCollection.get(scr.getTitle()).get(childPosition);
    }

    public Vector<SubMenu> getChildList(int groupPosition) {
        return childCollection.get(groupList.get(groupPosition).getTitle());
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_menu_child, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        convertView.setBackgroundColor(0);
        SubMenu subMen = getChild(groupPosition, childPosition);
        holder.item.setText(subMen.getTitle());

        if (subMen.isEnabled()) {
            holder.item.setTextColor(Color.BLACK);
            holder.arrowImageView.setVisibility(View.GONE);

            if (groupPosition == groupItemSelection && childPosition == childItemSelection) {
                holder.setMenuItemSelection();
            } else
                holder.setMenuItems();
        } else {
            holder.item.setTextColor(mContext.getResources().getColor(R.color.white_subheading));
            holder.arrowImageView.setVisibility(View.VISIBLE);
        }

        if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
            holder.item.setTextColor(mContext.getResources().getColor(R.color.black));

        }
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        ScreenDetails src = groupList.get(groupPosition);
        return childCollection.get(src.getTitle()).size();
    }

    public ScreenDetails getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    public int getGroupCount() {
        return groupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {

        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.row_menu_group, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        convertView.setBackgroundColor(0);
        ScreenDetails details = getGroup(position);
        String subGroupName = details.getTitle();

        holder.groupImageView.setImageResource(details.getIcon());
        holder.headerTextView.setText(subGroupName);
        holder.arrowImageView.setImageDrawable(null);

        if (position == groupItemSelection && !details.isParentView() ||
                holder.headerTextView.getText().equals("Open an Account") ||
                holder.headerTextView.getText().equals("Login to Trade") ||
                holder.headerTextView.getText().equals("Sign In / Up as Guest")) {

            holder.setHeaderSelection();

        } else {
            holder.setMenuHeaders();
        }

        if (details.isParentView()) {
            if (isExpanded) {
                if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
                    holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    holder.linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.common_red_bg));
                    holder.headerTextView.setTextColor(mContext.getResources().getColor(R.color.white));



                } else {
                    holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    holder.linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.common_red_bg));
                    holder.headerTextView.setTextColor(mContext.getResources().getColor(R.color.white));

                }
            } else {
                if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
                    holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);

                } else {
                    holder.arrowImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

                }
            }
        }

        if (!details.isEnabled()) {
            if(AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")){
                holder.arrowImageView.setImageResource(R.drawable.ic_lock_black_24dp);
            }else{
                holder.arrowImageView.setImageResource(R.drawable.ic_lock_white_24dp);
            }

        }




        return convertView;

    }

    public void setMenuSelection(int groupSel) {
        this.groupItemSelection = groupSel;
        this.childItemSelection = -1;
        notifyDataSetChanged();
    }

    public void setChildMenuSelection(int groupSel, int childSelection) {
        this.groupItemSelection = groupSel;
        this.childItemSelection = childSelection;
        notifyDataSetChanged();
    }

    class HeaderViewHolder {
        private final RelativeLayout linearLay;
        private final GreekTextView headerTextView;
        private final ImageView arrowImageView;
        private final ImageView groupImageView;

        public HeaderViewHolder(View row) {
            linearLay = row.findViewById(R.id.main);
            groupImageView = row.findViewById(R.id.imgGroupImage);
            headerTextView = row.findViewById(R.id.txtMenuNames);
            arrowImageView = row.findViewById(R.id.imgFocusImage);
        }

        public void setMenuHeaders() {
            if (headerTextView != null) {

                if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.row_color));
                    headerTextView.setTextColor(mContext.getResources().getColor(R.color.black));

                } else {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.bajaj_gray));
                    headerTextView.setTextColor(mContext.getResources().getColor(R.color.white));

                }
            }
        }

        public void setHeaderSelection() {
            if (headerTextView != null) {
                if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.selectColor));
                    headerTextView.setTextColor(mContext.getResources().getColor(R.color.black));

                } else {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.common_red_bg));
                    headerTextView.setTextColor(mContext.getResources().getColor(R.color.white));


                }
            }
        }
    }


    class ChildViewHolder {
        private final GreekTextView item;
        private final ImageView arrowImageView;
        private final LinearLayout linearLay;

        public ChildViewHolder(View row) {
            linearLay = row.findViewById(R.id.childitemlayout);
            item = row.findViewById(R.id.childitem);
            arrowImageView = row.findViewById(R.id.imgFocusImage);
        }

        public void setMenuItems() {
            if (item != null) {


                if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.row_color));
                    item.setTextColor(mContext.getResources().getColor(R.color.black));
                } else {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.bajaj_black));
                    item.setTextColor(mContext.getResources().getColor(R.color.white));
                }

            }
        }

        public void setMenuItemSelection() {
            if (item != null) {
                if (AccountDetails.getThemeFlag(mContext).equalsIgnoreCase("white")) {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.common_red_bg));
                    item.setTextColor(mContext.getResources().getColor(R.color.black));
                } else {
                    linearLay.setBackgroundColor(mContext.getResources().getColor(R.color.common_red_bg));
                    item.setTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        }
    }
}