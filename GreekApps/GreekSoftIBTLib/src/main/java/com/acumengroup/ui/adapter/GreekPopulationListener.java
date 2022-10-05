package com.acumengroup.ui.adapter;

import android.view.View;

/**
 * @param <T> Created by Arcadia
 */
public interface GreekPopulationListener<T> {

    void populateFrom(View v, int position, final T row, View[] views);

    /**
     * @param views <br>
     *              Called when the row is created first time.
     */
    void onRowCreate(View[] views);

}
