package com.acumengroup.greekmain.chart.draw;

import android.view.MotionEvent;

/**
 * * Created by Arcadia <br>
 *         <br>
 *         To handle non API level 4 methods. <br>
 *         These class methods are supported from API level 5.
 */
public class ChartWrapper {

    public static int getPointerCount(MotionEvent event) {
        return event.getPointerCount();
    }

    static float getX(MotionEvent event, int pointerIndex) {
        return event.getX(pointerIndex);
    }

    /**
     * @param event
     * @param pointerIndex
     * @return
     */
    static float getY(MotionEvent event, int pointerIndex) {
        return event.getY(pointerIndex);
    }

}
