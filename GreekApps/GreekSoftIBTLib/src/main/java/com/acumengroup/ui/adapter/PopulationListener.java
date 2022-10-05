package com.acumengroup.ui.adapter;

import android.view.View;

/**
 * Created by Arcadia
 */
@Deprecated
public interface PopulationListener {

    void populateFrom(View v, int position, CommonRowData row, View[] views);

    // Call when the row is created first time.
    void onRowCreate(View[] views);

}
