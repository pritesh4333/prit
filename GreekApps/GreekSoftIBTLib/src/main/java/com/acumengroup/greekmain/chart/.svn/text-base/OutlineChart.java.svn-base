package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.xml.IndicatorData;

import java.util.Hashtable;

/**
 * Created by Arcadia
 *         <p/>
 *         Outline Indicator Charts
 */
public class OutlineChart extends XYChart {

    private String HISTOGRAM = "histogram";

    /**
     * Builds a new line chart instance.
     *
     * @param context  - application context
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public OutlineChart(Context context, XYMultipleSeriesDataset dataset, ChartSettings renderer) {
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

        paint.setStrokeWidth(mRenderer.getIndicatorLineWidth());
        paint.setColor(mRenderer.getOutlineIndPositiveColor());
        paint.setStyle(Paint.Style.STROKE);

        Hashtable hashtable = mRenderer.getFullIndicatorData();

        if (hashtable == null) return;

        // For outline indicators only one settings will be available. So give
        // 0 as index directly.
        IndicatorData data = (IndicatorData) hashtable.get(mRenderer.getIndicatorKeyValue().get(0).split(VALUE_SEPERATOR)[0]);

        String inputValue = "";
        if (mRenderer.getIndicatorKeyValue().get(0).contains(VALUE_SEPERATOR)) {
            inputValue = mRenderer.getIndicatorKeyValue().get(0).substring(mRenderer.getIndicatorKeyValue().get(0).indexOf(VALUE_SEPERATOR));
        }

        for (String outputKey : data.getOutputKey()) {
            String lineType = data.getOutputLineType().get(data.getOutputKey().indexOf(outputKey));

//			For volume indicator - no need to consider input values
            if (outputKey.equalsIgnoreCase(ChartConstants.VOLUME)) {
                inputValue = "";
            }

            if (pointsHashtable.containsKey(outputKey + inputValue)) {
                float points[] = pointsHashtable.get(outputKey + inputValue);

                if (mRenderer.getxAxisPositions().get(mRenderer.getEndIndex() - 1) == mRenderer.getxAxisPositions().get(mRenderer.getxAxisPositions().size() - 1)) {
                    for (int i = 0; i < points.length; i++) {
                        points[points.length - 2] = mRenderer.getWidth() + mRenderer.getLeftMargin();
                    }
                }

                if (lineType.equalsIgnoreCase(HISTOGRAM)) {
                    int length = points.length;
                    float x;
                    float y;

                    paint.setStyle(Paint.Style.FILL);
                    float curOHLCWidth = 3;

                    if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
                        curOHLCWidth = (3 * (mRenderer.getIntervalBtwPoints() / 10));
                    }

                    paint.setStrokeWidth(curOHLCWidth);
                    for (int k = 0; k < length; k += 2) {
                        x = points[k];
                        y = points[k + 1];

//						canvas.drawRect(x, y, x - curOHLCWidth, yAxisValue,
//								paint);

//						Due to issue with clipRect for drawRect, we are using drawLine.
                        canvas.drawLine(x, y, x, yAxisValue, paint);
                    }
                } else {
                    // Plot Line
                    drawPath(canvas, points, paint, false);
                }
            }
        }

    }

}