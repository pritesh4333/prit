package com.acumengroup.greekmain.chart.draw;

import com.acumengroup.greekmain.chart.NotifyChartData;

/**
 * * Created by Arcadia
 */
public class GreekChartListener {

    public interface onLongClickListener {
        void onLongClick();
    }

    public interface onDoubleTapListener {
        void onDoubleTap();
    }

    public interface onSingleTapListener {
        void onSingleTap();
    }

    public interface onRemoveIndicatorListener {
        void onRemoveIndicator(String indicator);
    }

    public interface onNotifyListener {
        void onNotifyData(NotifyChartData notifyData);
    }

    public interface onIndicatorMissingListener {
        void onIndicatorMissing(String indicatorsName);
    }


}