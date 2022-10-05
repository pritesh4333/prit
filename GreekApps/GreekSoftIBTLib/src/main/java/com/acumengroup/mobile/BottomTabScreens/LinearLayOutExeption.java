package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearLayOutExeption extends LinearLayoutManager {


    public LinearLayOutExeption(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }



    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
