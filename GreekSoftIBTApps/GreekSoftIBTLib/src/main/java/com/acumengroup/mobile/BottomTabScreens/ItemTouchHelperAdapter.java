package com.acumengroup.mobile.BottomTabScreens;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void OnItemDismiss(int position);

}
