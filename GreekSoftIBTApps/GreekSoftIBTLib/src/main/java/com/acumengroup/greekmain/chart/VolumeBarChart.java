package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.settings.ChartSettings;

import java.util.Hashtable;

/**
 * Created by Arcadia
 *         <p/>
 *         Volume Bar Chart
 */
public class VolumeBarChart extends XYChart {

    private float volumeWidth = 3;

    /**
     * Builds a new line chart instance.
     *
     * @param context  - application context
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public VolumeBarChart(Context context, XYMultipleSeriesDataset dataset, ChartSettings renderer) {
        super(context, dataset, renderer);
    }

    /**
     * The graphical representation of a series.
     *
     * @param canvas4    the canvas to paint
     * @param paint      the paint to be used for drawing
     * @param points     the array of points to be used for drawing the series
     * @param yAxisValue the minimum value of the y axis
     */
    @Override
    public void drawSeries(Canvas canvas, Paint paint, Hashtable<String, float[]> pointsHashtable, float yAxisValue) {

        paint.setColor(mRenderer.getVolumeBarColor());
        paint.setStyle(Paint.Style.FILL);

        float curVolumeWidth = volumeWidth;

        float points[] = pointsHashtable.get(ChartConstants.VOLUME);

        // Increase the Bar width according to Zoom Level
        if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
            curVolumeWidth = (curVolumeWidth * (mRenderer.getIntervalBtwPoints() / 10));

        }

        paint.setStrokeWidth(curVolumeWidth);
        float x;
        float y;
        for (int k = 0; k < points.length; k += 2) {
            x = points[k];
            y = points[k + 1];
//			canvas.drawRect(x, y, x - curVolumeWidth, yAxisValue, paint);

//			Due to issue with clipRect for drawRect, we are using drawLine.
            canvas.drawLine(x, y, x, yAxisValue, paint);
        }

    }

}
