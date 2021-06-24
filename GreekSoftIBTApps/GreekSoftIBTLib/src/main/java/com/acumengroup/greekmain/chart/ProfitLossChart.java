package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.util.MathHelper;

import java.util.Hashtable;

/**
 * Created by Arcadia
 *         <p/>
 *         Profit Loss Chart
 */
public class ProfitLossChart extends XYChart {

    /**
     * Builds a new line chart instance.
     *
     * @param context  - application context
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public ProfitLossChart(Context context, XYMultipleSeriesDataset dataset, ChartSettings renderer) {
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

        paint.setStrokeWidth(mRenderer.getLineChartLineWidth());

		/*if (mRenderer.getChartType() == ChartType.StreamingChart) {
            // Streaming Chart
			for (int i = 0; i < mDataset.getSeriesCount(); i++) {
				float points[] = pointsHashtable.get(mDataset.getSeriesAt(i)
						.getTitle());

				float minClose = ((float) MathHelper
						.getMinValueFromEvenOrder(points));
				Float f;
				int length = points.length;
				for (int j = 0; j < length; j++) {
					f = new Float(points[j]);
					if (f.isNaN())
						points[j] = minClose;
				}

				paint.setColor(mRenderer.getColors()[i]);
				drawPath(canvas, points, paint, false);
			}
		} else {*/
        // Line Chart or Area Chart

        float points[] = pointsHashtable.get(ChartConstants.PL_CHART_PLO);

        float minClose = MathHelper.getMinValueFromEvenOrder(points);
        Float f;
        int length = points.length;
        for (int i = 0; i < length; i++) {
            f = new Float(points[i]);
            if (f.isNaN()) points[i] = minClose;
        }

			/*float[] fillPoints = null;
			// Area Chart
			if (mRenderer.isFillBelowLine()) {
				paint.setColor(mRenderer.getFillBelowLineColor());
				fillPoints = new float[length + 4];
				System.arraycopy(points, 0, fillPoints, 0, length);
				points[0] += 1.0F;
				fillPoints[length] = fillPoints[(length - 2)];
				fillPoints[(length + 1)] = yAxisValue;
				fillPoints[(length + 2)] = fillPoints[0];
				fillPoints[(length + 3)] = fillPoints[(length + 1)];
				paint.setStyle(Paint.Style.FILL);
				if (mRenderer.isApplyGradientColor()) {
					paint.setShader(new LinearGradient(0, 0, 0, mRenderer
							.getGradientHeight(), mRenderer
							.getGradientStartColor(), mRenderer
							.getGradientEndColor(), Shader.TileMode.CLAMP));
				}
				drawPath(canvas, fillPoints, paint, true);
				paint.setShader(null);
				paint.setStrokeWidth(mRenderer.getLineChartLineWidth() + 1);
			}*/

        paint.setStyle(Paint.Style.STROKE);

        // May be Line Chart or Area Chart
        paint.setColor(mRenderer.getLineColor());
        drawPath(canvas, points, paint, false);

//		}

    }

}