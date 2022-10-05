package com.acumengroup.ui.adapter;

import android.view.View;

/**
 * Created by Arcadia
 */
public class RowHolder {
    private View[] views;

    public RowHolder(final View row, final int[] viewIds) {
        if (row != null && viewIds != null) {
            views = new View[viewIds.length];
            for (int i = 0; i < viewIds.length; i++)
                views[i] = row.findViewById(viewIds[i]);
        }
    }

    public View[] getViews() {
        return views;
    }
}
