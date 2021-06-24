package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;

import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartType;
import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.util.MathHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Arcadia
 *         <p/>
 *         Line and Area Chart
 */
public class LineChart extends XYChart {

    private int green_color = 0xFF7DC575;
    private int red_color = 0xFFFF5F5F;

    /**
     * Builds a new line chart instance.
     *
     * @param context  - application context
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public LineChart(Context context, XYMultipleSeriesDataset dataset, ChartSettings renderer) {
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

        if (mRenderer.getChartType() == ChartType.StreamingChart) {
            // Streaming Chart
            for (int i = 0; i < mDataset.getSeriesCount(); i++) {
                float points[] = pointsHashtable.get(mDataset.getSeriesAt(i).getTitle());

                float minClose = MathHelper.getMinValueFromEvenOrder(points);
                Float f;
                int length = points.length;
                for (int j = 0; j < length; j++) {
                    f = points[j];
                    if (f.isNaN()) points[j] = minClose;
                }

                paint.setColor(mRenderer.getColors()[i]);
                drawPath(canvas, points, paint, false);
            }
        } else {
            // Line Chart or Area Chart
            if (mRenderer.getChartType() == ChartType.LineChart || mRenderer.getChartType() == ChartType.AreaChart || mRenderer.getChartType() == ChartType.SparkLineChart || mRenderer.getChartType() == ChartType.SparkAreaChart) {
                float points[] = pointsHashtable.get(ChartConstants.CLOSE);

                float minClose = MathHelper.getMinValueFromEvenOrder(points);
                Float f;
                int length = points.length;
                for (int i = 0; i < length; i++) {
                    f = points[i];
                    if (f.isNaN()) points[i] = minClose;
                }

                float[] fillPoints = null;
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
                        paint.setShader(new LinearGradient(0, 0, 0, mRenderer.getGradientHeight(), mRenderer.getGradientStartColor(), mRenderer.getGradientEndColor(), Shader.TileMode.CLAMP));
                    }
                    drawPath(canvas, fillPoints, paint, true);
                    paint.setShader(null);
                    paint.setStrokeWidth(mRenderer.getLineChartLineWidth() + 1);
                }
                paint.setStyle(Paint.Style.STROKE);

                // May be Line Chart or Area Chart
                paint.setColor(mRenderer.getLineColor());
                drawPath(canvas, points, paint, false);

            } else if (mRenderer.getChartType() == ChartType.SparkLineColoredChart) {

                paint.setStyle(Paint.Style.FILL);
                float points[] = pointsHashtable.get(ChartConstants.CLOSE);
                paint.setColor(mRenderer.getLineColor());

                double close = mRenderer.getPrevClose();
                float f = (float) (mRenderer.getBottom() - mRenderer.getyPixelsPerUnit() * (close - mRenderer.getYAxisMin()));

                float x;
                float y;
                for (int k = 0; k < points.length; k += 2) {
                    x = points[k];
                    y = points[k + 1];

                    if (f < y) paint.setColor(red_color);
                    else paint.setColor(green_color);

                    if (k + 3 <= points.length) {

                        if ((f > y && f < points[k + 3]) || (f < y && f > points[k + 3])) {

                            if (f > points[k + 3]) {
                                paint.setColor(red_color);
                                canvas.drawLine(x, y, x + ((f - y) / (points[k + 3] - y)) * (points[k + 2] - x), f, paint);
                                paint.setColor(green_color);
                                canvas.drawLine(x + ((f - y) / (points[k + 3] - y)) * (points[k + 2] - x), f, points[k + 2], points[k + 3], paint);

                            } else {

                                paint.setColor(green_color);
                                canvas.drawLine(x, y, x + ((f - y) / (points[k + 3] - y)) * (points[k + 2] - x), f, paint);
                                paint.setColor(red_color);

                                canvas.drawLine(x + ((f - y) / (points[k + 3] - y)) * (points[k + 2] - x), f, points[k + 2], points[k + 3], paint);

                            }

                        } else {
                            canvas.drawLine(x, y, points[k + 2], points[k + 3], paint);
                        }

                    }
                }

            } else if (mRenderer.getChartType() == ChartType.SparkAreaColoredChart) {

                float points[] = pointsHashtable.get(ChartConstants.CLOSE);

                double close = ((Double) (mRenderer.getPrevClose())).doubleValue();

                final float referencePoint = (float) (mRenderer.getBottom() - mRenderer.getyPixelsPerUnit() * (close - mRenderer.getYAxisMin()));

                // Above
                float[] fillPoints = points;

                List<Float> boundsPoints = new ArrayList<Float>();
                boolean add = false;

                if (fillPoints[1] < referencePoint) {
                    boundsPoints.add(fillPoints[0]);
                    boundsPoints.add(fillPoints[1]);
                    add = true;
                }
                for (int i = 3; i < fillPoints.length; i += 2) {

                    float prevValue = fillPoints[i - 2];
                    float value = fillPoints[i];

                    if (prevValue < referencePoint && value > referencePoint || prevValue > referencePoint && value < referencePoint) {
                        float prevX = fillPoints[i - 3];
                        float x = fillPoints[i - 1];
                        boundsPoints.add(prevX + (x - prevX) * (referencePoint - prevValue) / (value - prevValue));
                        boundsPoints.add(referencePoint);
                        if (value > referencePoint) {
                            i += 2;
                            add = false;
                        } else {
                            boundsPoints.add(x);
                            boundsPoints.add(value);
                            add = true;
                        }
                    } else {
                        if (add || value <= referencePoint) {
                            boundsPoints.add(fillPoints[i - 1]);
                            boundsPoints.add(value);
                        }
                    }
                }

                fillPoints = new float[boundsPoints.size() + 4];
                for (int i = 0; i < boundsPoints.size(); i++) {
                    fillPoints[i] = boundsPoints.get(i);
                }

                int length = boundsPoints.size();
                if (length > 0) {
                    fillPoints[0] = (fillPoints[0] + 1);
                    fillPoints[length] = (fillPoints[length - 2]);
                    fillPoints[(length + 1)] = referencePoint;
                    fillPoints[(length + 2)] = fillPoints[0];
                    fillPoints[(length + 3)] = fillPoints[(length + 1)];
                    for (int i = 0; i < length + 4; i += 2) {
                        if (fillPoints[i + 1] < 0) {
                            fillPoints[i + 1] = 0f;
                        }
                    }

                    paint.setColor(green_color);
                    paint.setStyle(Paint.Style.STROKE);
                    drawPath(canvas, fillPoints, paint, true);

                }


                // Below
                fillPoints = points;
                boundsPoints = new ArrayList<Float>();

                add = false;

                if (fillPoints[1] > referencePoint) {
                    boundsPoints.add(fillPoints[0]);
                    boundsPoints.add(fillPoints[1]);
                    add = true;
                }

                for (int i = 3; i < fillPoints.length; i += 2) {

                    float prevValue = fillPoints[i - 2];
                    float value = fillPoints[i];

                    if (referencePoint == value) value -= 0.00001;

                    if (prevValue < referencePoint && value > referencePoint || prevValue > referencePoint && value < referencePoint) {
                        float prevX = fillPoints[i - 3];
                        float x = fillPoints[i - 1];
                        boundsPoints.add(prevX + (x - prevX) * (referencePoint - prevValue) / (value - prevValue));
                        boundsPoints.add(referencePoint);
                        if (value < referencePoint) {
                            i += 2;
                            add = false;
                        } else {
                            boundsPoints.add(x);
                            boundsPoints.add(value);
                            add = true;
                        }
                    } else {
                        if (add || value >= referencePoint) {
                            boundsPoints.add(fillPoints[i - 1]);
                            boundsPoints.add(value);
                        }
                    }
                }

                fillPoints = new float[boundsPoints.size() + 4];
                for (int i = 0; i < boundsPoints.size(); i++) {
                    fillPoints[i] = boundsPoints.get(i);
                }

                length = boundsPoints.size();
                if (length > 0) {
                    fillPoints[0] = (fillPoints[0] + 1);
                    fillPoints[length] = (fillPoints[length - 2]);
                    fillPoints[(length + 1)] = referencePoint;
                    fillPoints[(length + 2)] = fillPoints[0];
                    fillPoints[(length + 3)] = fillPoints[(length + 1)];
                    for (int i = 0; i < length + 4; i += 2) {
                        if (fillPoints[i + 1] < 0) {
                            fillPoints[i + 1] = 0f;
                        }
                    }
                    paint.setColor(red_color);
                    paint.setStyle(Paint.Style.STROKE);
                    drawPath(canvas, fillPoints, paint, true);

                }


            }

        }

    }

    private void recalculatePoints(float[] points, float median) {

        PointF[] pointArr = new PointF[points.length * 2];
        int k = 0;
        for (int i = 0; i < points.length; i++) {
            pointArr[k] = new PointF();
            pointArr[k].x = points[i];
            pointArr[k++].y = points[++i];
        }
        recalculatePoints(pointArr, median);

    }

    private void recalculatePoints(PointF[] points, float median) {

        ArrayList<PointF> newPoints = new ArrayList<PointF>();

        for (int i = 0; i < points.length; i++) {
            newPoints.add(points[i]);
            if (i < points.length - 1 && (median > points[i].y && median < points[i + 1].y || (median < points[i].y && median > points[i + 1].y))) {
                PointF p = new PointF();
                PointF p1 = points[i];
                PointF p2 = points[i + 1];
                p.y = median;
                p.x = p1.x + ((median - p1.y) / (p2.y - p1.y)) * (p2.x - p1.x);
                newPoints.add(p);
            }

        }
    }

}