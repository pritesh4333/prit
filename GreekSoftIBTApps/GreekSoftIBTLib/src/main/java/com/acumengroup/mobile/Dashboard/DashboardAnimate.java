package com.acumengroup.mobile.Dashboard;

public class DashboardAnimate {

    String name;
    int position;
    int listview_height;
    boolean showProgress;

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public int getListview_height() {
        return listview_height;
    }

    public void setListview_height(int listview_height) {
        this.listview_height = listview_height;
    }

    boolean isExpand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}
