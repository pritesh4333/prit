package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartType;
import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.util.MathHelper;

import java.util.Hashtable;

/**
 * Created by Arcadia
 *         <p/>
 *         CandleStick and OHLC Chart.
 */
public class CandleOHLCChart extends XYChart {

    private float candleStickWidth = 3;
    private int ohlcWidth = 3;

    /**
     * Builds a new line chart instance.
     *
     * @param context  - application context
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public CandleOHLCChart(Context context, XYMultipleSeriesDataset dataset, ChartSettings renderer) {
        super(context, dataset, renderer);
    }

    /**
     * The graphical representation of a series.
     *
     * @param canvas     the canvas to paint
     * @param paint      the paint to be used for drawing
     * @param points     the array of points to be used for drawing the series
     * @param yAxisValue the minimum value of the y axis
     */
    @Override
    public void drawSeries(Canvas canvas, Paint paint, Hashtable<String, float[]> pointsHashtable, float yAxisValue) {

        paint.setStyle(Paint.Style.FILL);

        float highPoints[] = pointsHashtable.get(ChartConstants.HIGH);
        float lowPoints[] = pointsHashtable.get(ChartConstants.LOW);
        float openPoints[] = pointsHashtable.get(ChartConstants.OPEN);
        float closePoints[] = pointsHashtable.get(ChartConstants.CLOSE);

        float minHigh = MathHelper.getMinValueFromEvenOrder(highPoints);
        float minLow = MathHelper.getMinValueFromEvenOrder(lowPoints);
        float minOpen = MathHelper.getMinValueFromEvenOrder(openPoints);
        float minClose = MathHelper.getMinValueFromEvenOrder(closePoints);
        Float f;

        float drawWidth = 0;

        // Increase the Chart point width according to Zoom Level
        if (mRenderer.getChartType() == ChartType.CandleStickChart) {
            if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
                drawWidth = (candleStickWidth * (mRenderer.getIntervalBtwPoints() / 10));
            } else {
                drawWidth = candleStickWidth;
            }
        } else if (mRenderer.getChartType() == ChartType.OHLCChart) {
            if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
                drawWidth = (ohlcWidth * (mRenderer.getIntervalBtwPoints() / 10));
            } else {
                drawWidth = ohlcWidth;
            }
        }

        // Depends upon Zoom in/out the end point space need to be
        // increased/decreased.
        mRenderer.setEndPointSpace(drawWidth + 5);

        // High & Low ( High & Low points drawing format is same in both Candle
        // Stick & OHLC. )
        for (int i = 0; i < highPoints.length; i += 2) {

            // If open value is greater then close value then set positive color
            // else negative color
            if (openPoints[i + 1] >= closePoints[i + 1]) {
                paint.setColor(mRenderer.getCandlePositiveColor());
            } else {
                paint.setColor(mRenderer.getCandleNegativeColor());
            }

            f = new Float(highPoints[i + 1]);
            if (f.isNaN()) highPoints[i + 1] = minHigh;
            f = new Float(lowPoints[i + 1]);
            if (f.isNaN()) lowPoints[i + 1] = minLow;
            f = new Float(openPoints[i + 1]);
            if (f.isNaN()) openPoints[i + 1] = minOpen;
            f = new Float(closePoints[i + 1]);
            if (f.isNaN()) closePoints[i + 1] = minClose;

            paint.setStrokeWidth(1);

            // High & Low
            canvas.drawLine(highPoints[i], highPoints[i + 1], lowPoints[i], lowPoints[i + 1], paint);

            if (mRenderer.getChartType() == ChartType.CandleStickChart) {

                // We can't draw rectangle, if top and bottom(open and
                // close) values are same.
                // If open and close values are same, increase close
                // value+=2
                if (openPoints[i + 1] == closePoints[i + 1]) {
                    closePoints[i + 1] = openPoints[i + 1] + 2;
                }

                // Draw rectangle for candle stick using open & close value
                // Due to issue with clipRect for drawRect, we are using
                // drawLine.
                paint.setStrokeWidth(drawWidth * 2);
                canvas.drawLine(openPoints[i], openPoints[i + 1], openPoints[i], closePoints[i + 1], paint);

            } else if (mRenderer.getChartType() == ChartType.OHLCChart) {
                // Open
                canvas.drawLine(openPoints[i], openPoints[i + 1], openPoints[i] - drawWidth, openPoints[i + 1], paint);
                // Close
                canvas.drawLine(closePoints[i], closePoints[i + 1], closePoints[i] + drawWidth, closePoints[i + 1], paint);
            }

        }

    }

}