package com.acumengroup.greekmain.chart.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartIndicatorType;
import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartType;
import com.acumengroup.greekmain.chart.AbstractChart;
import com.acumengroup.greekmain.chart.NotifyChartData;
import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.dataset.XYSeries;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.tools.CalculatePoints;
import com.acumengroup.greekmain.chart.trendline.TrendPoint;
import com.acumengroup.greekmain.chart.trendline.TrendSeries;
import com.acumengroup.greekmain.chart.util.GreekChartLogger;
import com.acumengroup.greekmain.core.app.AccountDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * * Created by Arcadia
 */
public class GreekChartView extends View implements OnGestureListener, OnTouchListener {

    public static boolean isPanStarted = false;
    public static ArrayList<String> removeList;
    private final double DOUBLE_TOUCH_RADIUS = 30;
    TrendPoint tempStartTrendLinePoint = null;
    TrendPoint tempEndTrendLinePoint = null;
    TrendPoint tempStartFiboLinePoint = null;
    TrendPoint tempEndFiboLinePoint = null;
    private ArrayList<AbstractChart> mChart;
    private Handler mHandler;
    private Context context;
    private int heightinPercentage;
    private float xCurPos = 0;
    private float yCurPos = 0;
    private float scrollViewPos;
    private float xOldPos = 0;
    private float yOldPos = 0;
    private float oldDoubleXPos = 0;
    private float oldDoubleYPos = 0;
    private boolean crossHairs;
    private boolean magnify;
    // Trend Line
    private boolean trendLine;
    private boolean isStartTrendLine = true;
    private TrendSeries trendSeries;
    private TrendPoint trendPoints = null;
    private boolean isDragTrend = false;
    private boolean isStartTrend = false;
    private boolean isEndTrend = false;
    // Fibo Line
    private boolean fiboLine;
    private boolean isStartFiboLine = true;
    private TrendSeries fiboLineSeries;
    private TrendPoint fiboLinePoints = null;
    private boolean isDragFibonnaciLine = false;
    private boolean isStartFibonnaciLine = false;
    private boolean isEndFibonnaciLine = false;
    private float oldXPos;
    private float oldYPos;
    private ArrayList<ChartSettings> renderer;
    private float scale;
    private int floatToDipValue;
    private boolean panningEnabled;
    private int textWidth;
    private GestureDetector gestureDetector;
    private float height;
    private float width;
    // Pinch Zoom variables
    private float xCurFirstPos, yCurFirstPos, xCurSecPos, yCurSecPos;
    private float previousDistance = -1;
    private float currentDistance = -1;
    private long lastPinchTouchTime = -1;
    private long lastTouchTime = -1;
    private int mainChart = 0;
    private ArrayList<Point> points;
    private boolean isAPIGreaterThen4 = false;
    private GreekChartListener.onLongClickListener longClickListener;
    private GreekChartListener.onDoubleTapListener doubleTapListener;
    private GreekChartListener.onSingleTapListener singleTapListener;
    private GreekChartListener.onRemoveIndicatorListener removeIndicatorListener;
    private GreekChartListener.onNotifyListener notifyListener;
    private int mCHeight = 0;
    private NotifyChartData notifyChartData = new NotifyChartData();
    private Bitmap originalBitmap = null;
    private Bitmap magnifyBitmap = null;
    private float initialPosition;
    private int newCheck = 100;
    private Canvas canvasss;

    public GreekChartView(Context paramContext, ArrayList<AbstractChart> paramAbstractChart, ArrayList<ChartSettings> renderer, float height, float width) {
        super(paramContext);

        // To restrict 2.0 version methods in 1.6 version

        isAPIGreaterThen4 = Integer.parseInt(Build.VERSION.SDK) >= 5;

        this.mChart = paramAbstractChart;
        this.mHandler = new Handler();
        this.context = paramContext;
        this.crossHairs = renderer.get(mainChart).isShowCrossHairs();
        this.magnify = renderer.get(mainChart).isMagnify();
        this.trendLine = false;
        this.fiboLine = false;
        this.renderer = renderer;
        this.panningEnabled = renderer.get(mainChart).isPanningEnabled();
        this.height = height;
        this.width = width;
        gestureDetector = new GestureDetector(this);
        this.setOnTouchListener(this);
        points = new ArrayList<Point>();

        // Using Scale density we can handle text size in different screen
        // resolution
        scale = context.getResources().getDisplayMetrics().density;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasss = canvas;

        if (renderer.get(mainChart).getChartType() != ChartType.StreamingChart) {
            // Not Streaming Chart

            // Renderer - Contains settings of each chart.
            // Renderer Size = No. of Charts need to draw.

            Paint paint = new Paint();
            paint.setTextSize(renderer.get(mainChart).getChartTitleTextSize());

            if (renderer.get(mainChart).getTypeface() != null)
                paint.setTypeface(renderer.get(mainChart).getTypeface());

            if (renderer.get(mainChart).isShowYLabels())
                textWidth = (int) (paint.measureText(renderer.get(mainChart).getTextWidthCalculateText()) - convertFloatToDip(10));
            else textWidth = (int) paint.measureText("HHH");

            int y = 0;

            points.clear();

            boolean isOutlineIndicatorAvailable = false;

            float x = 0;
            for (int i = 0; i < renderer.size(); i++) {

                Point point = new Point();
                point.x = 0;
                point.y = 0;

                // removeList contains the indicator's name that need to be
                // removed.(Remove Outline Indicator at runtime)
                if (removeList.size() > 0) {
                    String labelName = renderer.get(i).getIndicatorLabel();
                    // lableName ex:- 1) MACD(12,25,6) 2) AVGPRICE 3) SMA(14)
                    if (labelName.contains("(")) {
                        labelName = labelName.replace("(", ":").replace(",", ":").replace(")", "");
                    }
                    // If the Indicator removed at runtime. Continue drawing
                    // next
                    // indicator...
                    if (removeList.contains(labelName)) {
                        continue;
                    }
                }

                if (i == 0) {
                    // Set the main chart height. ( Default Main Chart height is
                    // 65%
                    // of ChartLayout. )
                    heightinPercentage = renderer.get(mainChart).getMainChartHeightinPercentage();
                    if (renderer.get(mainChart).getChartType() != ChartType.SparkLineChart && renderer.get(mainChart).getChartType() != ChartType.SparkAreaChart && renderer.get(mainChart).getChartType() != ChartType.SparkLineColoredChart && renderer.get(mainChart).getChartType() != ChartType.SparkAreaColoredChart) {
                        if (!renderer.get(mainChart).isShowVolumeChart()) {
                            if (renderer.size() - removeList.size() == 1) {
                                heightinPercentage = 95;
                                renderer.get(mainChart).setShowXLabels(true);
                            }
                        }
                    }
                } else {
                    // For Other Charts like Volume, Outline Indicator.. the
                    // default
                    // height is 30% of ChartLayout
                    heightinPercentage = renderer.get(mainChart).getOtherChartsHeightinPercentage();

                    // Check whether the indicator is outline, if it is outline
                    // then
                    // draw a legend and close button.
                    if (renderer.get(i).getIndicatorType() == ChartIndicatorType.OUTLINE || renderer.get(i).isShowCloseBtnForVolumeChart()) {

                        isOutlineIndicatorAvailable = true;

                        paint.setColor(renderer.get(i).getOutlineIndPositiveColor());
                        paint.setTextSize(convertFloatToDip(renderer.get(i).getChartTitleTextSize()));
                        Rect measeureHeightRect = new Rect();
                        paint.getTextBounds("a", 0, 1, measeureHeightRect);
                        paint.setStrokeWidth(1);
                        paint.setStyle(Paint.Style.FILL);
                        canvas.drawText(renderer.get(i).getIndicatorLabel(), x + renderer.get(mainChart).getLeftMargin(), y + (measeureHeightRect.height() + 5), paint);
                        y += (int) (height * 3) / 100;
                        if (renderer.get(mainChart).isShowCloseBtnForIndicators()) {

                            float closeX = width - (renderer.get(i).getLeftMargin() + renderer.get(i).getRightMargin());

                            paint.setColor(Color.RED);
                            paint.setStyle(Paint.Style.FILL);
                            canvas.drawCircle(closeX + renderer.get(mainChart).getLeftMargin(), y - convertFloatToDip(0), convertFloatToDip(5), paint);
                            paint.setColor(Color.WHITE);
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setStrokeWidth(1);
                            canvas.drawCircle(closeX + renderer.get(mainChart).getLeftMargin(), y - convertFloatToDip(0), convertFloatToDip(5), paint);
                            paint.setStrokeWidth(2);
                            canvas.drawLine((closeX + renderer.get(mainChart).getLeftMargin()) - convertFloatToDip(3), y - convertFloatToDip(0), closeX + renderer.get(mainChart).getLeftMargin() + convertFloatToDip(3), y - convertFloatToDip(0), paint);

                            // Save the X & Y Points of Close Button. ( Used to
                            // measure
                            // on SingleTap to remove Particular Indicator ).
                            point.x = (int) closeX + renderer.get(mainChart).getLeftMargin();
                            point.y = (int) (y - convertFloatToDip(3));

                        }

//                        EventBus.getDefault().post("closevolumechart");

                    }
                }

                points.add(point);

                renderer.get(mainChart).setGradientHeight((int) (height * heightinPercentage) / 100);

                mChart.get(i).draw(canvas, x, y, width, (int) (height * heightinPercentage) / 100);

                y += (int) (height * heightinPercentage) / 100;

            }

            y += (int) (height * 5) / 100;
            // Save the height. Used in onMeasure to set custom height
            mCHeight = y;

            // Draw CrossHairs if enabled. No CrossHairs for Spark line Chart.
            if (crossHairs && renderer.get(mainChart).getChartType()
                    != ChartType.SparkLineChart && renderer.get(mainChart).getChartType()
                    != ChartType.SparkAreaChart && renderer.get(mainChart).getChartType()
                    != ChartType.SparkLineColoredChart && renderer.get(mainChart).getChartType()
                    != ChartType.SparkAreaColoredChart) {

                if (xCurPos == 0) {
                    xCurPos = (width - textWidth) / 2;
                }
                // If the main and other charts are joined, then draw CrossHairs
                // for
                // all the charts.
                if (renderer.get(mainChart).isJoinCharts()) {

                    y -= (int) (height * 5) / 100;
                    mChart.get(mainChart).drawCrossHairs(canvas, xCurPos, yCurPos, width, y);


                } else {
                    // Draw CrossHairs only for main chart.

                    if (isOutlineIndicatorAvailable) {
                        mChart.get(mainChart).drawCrossHairs(canvas, xCurPos, yCurPos, width, (int) (height * renderer.get(mainChart).getMainChartHeightinPercentage()) / 100);
                    } else {
                        mChart.get(mainChart).drawCrossHairs(canvas, xCurPos, yCurPos, width, (int) (height * 95) / 100);
                    }
                }
            }

            if (magnify) {
                mChart.get(mainChart).drawMagnifier(canvas, magnifyBitmap, xCurPos, yCurPos, width, (int) (height * renderer.get(mainChart).getMainChartHeightinPercentage()) / 100);
            }

        } else {
            // Streaming Chart

            mChart.get(mainChart).drawStreaming(canvas, 0, 0, this.width, (int) (height * 95) / 100);

        }

    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onMeasure(int, int)
     *
     * To set custom height.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mCHeight != 0) {

            heightMeasureSpec = mCHeight;

            int desiredWidth = 100;
            int desiredHeight = mCHeight;

            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            int width;
            int height;

            // Measure Width
            if (widthMode == MeasureSpec.EXACTLY) {
                // Must be this size
                width = widthSize;
            } else if (widthMode == MeasureSpec.AT_MOST) {
                // Can't be bigger than...
                width = Math.min(desiredWidth, widthSize);
            } else {
                // Be whatever you want
                width = desiredWidth;
            }

            // Measure Height
            if (heightMode == MeasureSpec.EXACTLY) {
                // Must be this size
                height = heightSize;
            } else if (heightMode == MeasureSpec.AT_MOST) {
                // Can't be bigger than...
                height = Math.min(desiredHeight, heightSize);
            } else {
                // Be whatever you want
                height = desiredHeight;
            }

            // MUST CALL THIS
            setMeasuredDimension(width, height);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * Convert float into dip ( density independent pixels ) used to support
     * various screen resolution.
     *
     * @param floatValue
     * @return
     */
    private float convertFloatToDip(float floatValue) {
        floatToDipValue = (int) (floatValue * scale);
        return floatToDipValue;
    }

    public void setOnLongPressListener(GreekChartListener.onLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setOnDoubleTapListener(GreekChartListener.onDoubleTapListener doubleTapListener) {
        this.doubleTapListener = doubleTapListener;
    }

    public void setOnSingleTapListener(GreekChartListener.onSingleTapListener singleTapListener) {
        this.singleTapListener = singleTapListener;
    }

    public void setOnRemoveIndicatorListener(GreekChartListener.onRemoveIndicatorListener removeIndicatorListener) {
        this.removeIndicatorListener = removeIndicatorListener;
    }

    public void setOnNotifyListener(GreekChartListener.onNotifyListener notifyListener) {
        this.notifyListener = notifyListener;
    }

    public void addInlineindicator(ArrayList<Date> xValues, double[] yValues, String seriesName) {
        XYMultipleSeriesDataset dataset = renderer.get(mainChart).getDataset();
        XYSeries series = new XYSeries(seriesName);
        int seriesLength = xValues.size();
        for (int k = 0; k < seriesLength; k++) {
            series.add(xValues.get(k).getTime(), yValues[k]);
        }
        renderer.get(mainChart).getYAxisValue().add(yValues);
        dataset.addSeries(series);
        renderer.get(mainChart).setDataset(dataset);
        repaint();
    }

    /**
     * Set true to enable CrossHairs. <br>
     * Set false to disable CrossHairs.
     *
     * @param crossHairs
     */
    public void setCrossHairs(boolean crossHairs) {
        this.crossHairs = crossHairs;
        this.trendLine = false;
        notifyChartData.setCrossHairEnabled(crossHairs);
        if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);
        repaint();
    }

    public void setMagnify(boolean magnify) {
        setTrendLine(false);
        originalBitmap = null;
        magnifyBitmap = null;
        this.magnify = magnify;
        this.crossHairs = false;
        for (int k = 0; k < renderer.size(); k++) {
            renderer.get(k).setMagnifyMovable(false);
        }
        repaint();
    }

    public void setMagnifyMovable(boolean magnifyMovable) {
        setTrendLine(false);
        this.crossHairs = false;
        originalBitmap = null;
        magnifyBitmap = null;
        this.magnify = magnifyMovable;
        for (int k = 0; k < renderer.size(); k++) {
            renderer.get(k).setMagnifyMovable(magnifyMovable);
        }
        repaint();
    }

    public boolean getFibonacciLine() {
        return fiboLine;
    }

    public void setFibonacciLine(boolean fiboLine) {
        this.crossHairs = false;
        this.magnify = false;
        this.trendLine = true;
        this.fiboLine = fiboLine;

        for (int k = 0; k < renderer.size(); k++) {
            renderer.get(k).setFiboLine(fiboLine);
            renderer.get(k).setDeleteFiboLine(false);
        }
        repaint();

    }

    public boolean getDeleteFibonacciLine() {
        return renderer.get(mainChart).isDeleteFiboLine();
    }

    public void deleteFibonacciLine(boolean fiboLine) {
        this.crossHairs = false;
        this.magnify = false;

        if (fiboLine) {
            if (renderer.get(mainChart).getFiboLineSeries().size() == 0) {
                Toast.makeText(context, "No Fibonnaci Line exists to delete", Toast.LENGTH_SHORT).show();
                fiboLine = false;
            }
        }

        this.fiboLine = fiboLine;
        for (int k = 0; k < renderer.size(); k++) {
            renderer.get(k).setDeleteFiboLine(fiboLine);
        }
        repaint();
    }

    public boolean getTrendLine() {
        return trendLine;
    }

    public void setTrendLine(boolean trendLine) {
        this.crossHairs = false;
        this.magnify = false;
        this.fiboLine = false;
        this.trendLine = trendLine;
        for (int k = 0; k < renderer.size(); k++) {
            renderer.get(k).setTrendline(trendLine);
            renderer.get(k).setDeleteTrendline(false);
        }
        repaint();
    }

    public boolean getDeleteTrendLine() {
        return renderer.get(mainChart).isDeleteTrendline();
    }

    public void deleteTrendLine(boolean trendLine) {
        this.crossHairs = false;
        this.magnify = false;

        if (trendLine) {
            if (renderer.get(mainChart).getTrendSeries().size() == 0) {
                Toast.makeText(context, "No Trend Series to delete", Toast.LENGTH_SHORT).show();
                trendLine = false;
            }
        }

        this.trendLine = trendLine;
        for (int k = 0; k < renderer.size(); k++) {
            renderer.get(k).setDeleteTrendline(trendLine);
        }
        repaint();
    }

    private void repaint() {
        this.mHandler.post(new Runnable() {
            public void run() {
                invalidate();
            }
        });
    }

    public void reDraw(double virtualValues, ArrayList<ChartSettings> renderer) {
        this.renderer = renderer;
        renderer.get(mainChart).setVirtualValues(virtualValues);
        notifyChartData.setVirtualValues(virtualValues);
        if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);

        repaint();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Check Panning or CrossHairs is enabled. If enabled then go ahead
        if ((!panningEnabled) && (!crossHairs) && (!trendLine) && (!magnify) && (!fiboLine))
            return false;

        // Check whether the charts ( Main, Volume & Outline Charts ) are joined
        // or not. If joined then touch should work on all charts else only main
        // chart.
        if (!renderer.get(mainChart).isJoinCharts()) {
            if (!(event.getY() < (height * renderer.get(mainChart).getMainChartHeightinPercentage() / 100)))
                return false;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (magnify) {
                // Clear the drawn magnify bitmap
                magnifyBitmap = null;
                repaint();
            }
        }


        float newPosition = event.getX();
        if (initialPosition - newPosition == 0) {//has stopped

            Log.e("GreekchartView", "on End =====================>>>");
        } else {
            initialPosition = getScrollY();
        }


        // if(trendLine)
//         cursorAtMouseDown = new TrendPoint( event.getX(), event.getY());

        gestureDetector.onTouchEvent(event);

        return true;
    }

    private Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // paint.setStyle(Style.STROKE);
        // paint.setColor(Color.GREEN);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        // return _bmp;
        return output;
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation

        Matrix matrix = new Matrix();

        // resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

    private void magnifyView() {

        if (originalBitmap == null) originalBitmap = toBitmapFromView();

        int magnifyRadius = renderer.get(mainChart).getMagnifyRadius();

        int xPos = (int) xCurPos - (magnifyRadius / 2);
        int yPos = (int) yCurPos - (magnifyRadius / 2);

        if (xPos < 0) {
            xPos = 0;
        } else if ((xPos + magnifyRadius) >= originalBitmap.getWidth()) {
            xPos = originalBitmap.getWidth() - magnifyRadius;
        }

        if (yPos < 0) {
            yPos = 0;
        } else if ((yPos + magnifyRadius) >= originalBitmap.getHeight()) {
            yPos = originalBitmap.getHeight() - magnifyRadius;
        }

        magnifyBitmap = Bitmap.createBitmap(originalBitmap, xPos, yPos, magnifyRadius, magnifyRadius);

        magnifyBitmap = getResizedBitmap(magnifyBitmap, magnifyRadius * 2, magnifyRadius * 2);

        magnifyBitmap = getCroppedBitmap(magnifyBitmap);

        // imageView.setImageBitmap(magnifyBitmap);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        isDragTrend = false;
        isStartTrend = false;
        isEndTrend = false;

        isDragFibonnaciLine = false;
        isStartFibonnaciLine = false;
        isEndFibonnaciLine = false;

        // Get the old(on very first touch) X position
        oldXPos = event.getX();
        oldYPos = event.getY();
        previousDistance = -1;

        // Cross Hairs
        if (crossHairs) {
            xCurPos = event.getX();
            yCurPos = event.getY();
            repaint();
        }

        if (magnify) {
            xCurPos = event.getX();
            yCurPos = event.getY();
            magnifyView();
            repaint();
            return false;
        }

        // cursorAtMouseDown = new Point((int) event.getX(), (int)
        // event.getY());

        if (trendLine) trendPoints = new TrendPoint(event.getX(), event.getY());

        if (renderer.get(mainChart).getTrendSeries().size() > 0) {
            TrendSeries trendSeries = renderer.get(mainChart).getTrendSeries().get(0);

            tempStartTrendLinePoint = new TrendPoint(trendSeries.getxStartPos(), trendSeries.getyStartPos());
            tempEndTrendLinePoint = new TrendPoint(trendSeries.getxEndPos(), trendSeries.getyEndPos());
        }

        if (fiboLine) fiboLinePoints = new TrendPoint(event.getX(), event.getY());

        if (renderer.get(mainChart).getFiboLineSeries().size() > 0) {
            TrendSeries fiboLineSeries = renderer.get(mainChart).getFiboLineSeries().get(0);

            tempStartFiboLinePoint = new TrendPoint(fiboLineSeries.getxStartPos(), fiboLineSeries.getyStartPos());
            tempEndFiboLinePoint = new TrendPoint(fiboLineSeries.getxEndPos(), fiboLineSeries.getyEndPos());
        }
        if (trendLine) {
            for (TrendSeries series : renderer.get(mainChart).getTrendSeries()) {
                isDragTrend = false;
                isStartTrend = false;
                isEndTrend = false;

                if (isPointOnLine(new TrendPoint(event.getX(), event.getY()), series.getStart(), series.getEnd())) {

                    if (renderer.get(mainChart).isDeleteTrendline()) {
                        renderer.get(mainChart).deleteTrendSeries();
                        setTrendLine(false);
                        Toast.makeText(context, "Trend Series Deleted.", Toast.LENGTH_SHORT).show();
                        repaint();
                        break;
                    }

                    List<TrendPoint> coordinates = new ArrayList<TrendPoint>();
                    coordinates.add(series.getStart());
                    coordinates.add(new TrendPoint(((series.getStart().x + series.getEnd().x) / 2), (series.getStart().y + series.getEnd().y) / 2));
                    coordinates.add(series.getEnd());

                    TrendPoint finalPoint = minDistance(coordinates, new TrendPoint(event.getX(), event.getY()));

                    if (finalPoint == null) {
                    } else {
                        series.setPointTouched(true);
                        if (coordinates.indexOf(finalPoint) == 0) isStartTrend = true;
                        else if (coordinates.indexOf(finalPoint) == 1) {
                            isDragTrend = true;
                            tempStartTrendLinePoint = new TrendPoint(series.getxStartPos(), series.getyStartPos());
                            tempEndTrendLinePoint = new TrendPoint(series.getxEndPos(), series.getyEndPos());
                        } else if (coordinates.indexOf(finalPoint) == 2) isEndTrend = true;
                        repaint();
                        return true;
                    }
                } else {
                    series.setPointTouched(false);
                }
            }
        }

        if (fiboLine) {
            for (TrendSeries series : renderer.get(mainChart).getFiboLineSeries()) {
                isDragFibonnaciLine = false;
                isStartFibonnaciLine = false;
                isEndFibonnaciLine = false;

                if (isPointOnLine(new TrendPoint(event.getX(), event.getY()), series.getStart(), series.getEnd())) {

                    if (renderer.get(mainChart).isDeleteFiboLine()) {
                        renderer.get(mainChart).deleteFiboLineSeries();
                        setFibonacciLine(false);
                        Toast.makeText(context, "Trend Series Deleted.", Toast.LENGTH_SHORT).show();
                        repaint();
                        break;
                    }

                    List<TrendPoint> coordinates = new ArrayList<TrendPoint>();
                    coordinates.add(series.getStart());
                    coordinates.add(new TrendPoint(((series.getStart().x + series.getEnd().x) / 2), (series.getStart().y + series.getEnd().y) / 2));
                    coordinates.add(series.getEnd());

                    TrendPoint finalPoint = minDistance(coordinates, new TrendPoint(event.getX(), event.getY()));

                    if (finalPoint == null) {
                    } else {
                        series.setPointTouched(true);
                        if (coordinates.indexOf(finalPoint) == 0) isStartFibonnaciLine = true;
                        else if (coordinates.indexOf(finalPoint) == 1) {
                            isDragFibonnaciLine = true;
                            tempStartFiboLinePoint = new TrendPoint(series.getxStartPos(), series.getyStartPos());
                            tempEndFiboLinePoint = new TrendPoint(series.getxEndPos(), series.getyEndPos());
                        } else if (coordinates.indexOf(finalPoint) == 2) isEndFibonnaciLine = true;
                        repaint();
                        return true;
                    }
                } else {
                    series.setPointTouched(false);
                }
            }
        }
        return false;
    }

    public TrendPoint minDistance(List<TrendPoint> coordinates, TrendPoint someCoord) {
        float minDistance = Float.MAX_VALUE;
        TrendPoint result = null;
        for (TrendPoint coord : coordinates) {
            float distance = (float) Math.sqrt(Math.pow((coord.x - someCoord.x), 2) + (Math.pow((coord.y - someCoord.y), 2)));
            if (distance < minDistance) {
                result = coord;
                minDistance = distance;
            }
        }

        return result;
    }

    private TrendSeries setXYTrendPositions(TrendSeries series) {
        float xStart = series.getxStartPos();
        float xEnd = series.getxEndPos();
        float yStart = series.getyStartPos();
        float yEnd = series.getyEndPos();

        if (xStart > xEnd) {

            float xTemp = xStart;
            xStart = xEnd;
            xEnd = xTemp;

            float yTemp = yStart;
            yStart = yEnd;
            yEnd = yTemp;

            trendSeries.setxStartPos(xStart);
            trendSeries.setyStartPos(yStart);
            trendSeries.setxEndPos(xEnd);
            trendSeries.setyEndPos(yEnd);

        }
        return trendSeries;
    }

    private TrendSeries setXYFibonnaciPositions(TrendSeries series) {
        float xStart = series.getxStartPos();
        float xEnd = series.getxEndPos();
        float yStart = series.getyStartPos();
        float yEnd = series.getyEndPos();

        if (xStart > xEnd) {

            float xTemp = xStart;
            xStart = xEnd;
            xEnd = xTemp;

            float yTemp = yStart;
            yStart = yEnd;
            yEnd = yTemp;

            fiboLineSeries.setxStartPos(xStart);
            fiboLineSeries.setyStartPos(yStart);
            fiboLineSeries.setxEndPos(xEnd);
            fiboLineSeries.setyEndPos(yEnd);

        }
        return fiboLineSeries;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float arg2, float arg3) {

//        if (!crossHairs) {

        // Get the current X & Y Position
        xCurPos = event2.getX();
        yCurPos = event2.getY();

        // Get the old X & Y Position
        xOldPos = event1.getX();
        yOldPos = event1.getY();

        if ((xOldPos - xCurPos) > 0) {
            //right swipe---- make service call
//            if()
        }

if(renderer.size()>1) {
    Log.e("GreekChartView", "renderer 1====>" + renderer.get(1).getEndIndex());
    Log.e("GreekChartView", "renderer 0====>" + renderer.get(0).getAxisWidth());
    Log.e("GreekChartView", "renderer 0====>" + renderer.get(0).getEndIndex());


        AccountDetails.setEndIndex(renderer.get(1).getEndIndex());
        AccountDetails.setStartIndex(renderer.get(1).getStartIndex());

        if (renderer.get(1).getEndIndex() <= 120) {

//            if (!crossHairs) {
                EventBus.getDefault().post("MakeService");
//            }

        }
}


        // Check panning is enabled and cross hair is disabled. B'coz
        // panning and cross hair can't be enabled at a same time.
        if (crossHairs /*&& !trendLine && !magnify && !fiboLine*/) {
            repaint();
            return true;
        } else if (trendLine) {
            for (TrendSeries series : renderer.get(mainChart).getTrendSeries()) {

                if (series.isPointTouched()) {
                    if (isDragTrend) {

                        float diffX = event2.getX() - trendPoints.x;
                        float diffY = event2.getY() - trendPoints.y;

                        Log.e("GreekChartView", "isDragTrend====Reached====>>>>");

                        if (tempStartTrendLinePoint != null) {
                            trendSeries.setxStartPos(tempStartTrendLinePoint.getX() + diffX);
                            trendSeries.setxStart(-1);
                            trendSeries.setyStartPos((tempStartTrendLinePoint.getY() + diffY));
                            trendSeries.setyStart(-1);

                            trendSeries.setxEndPos((tempEndTrendLinePoint.getX() + diffX));
                            trendSeries.setxEnd(-1);
                            trendSeries.setyEndPos((tempEndTrendLinePoint.getY() + diffY));
                            trendSeries.setyEnd(-1);
                        }

                        repaint();
                        return true;

                    } else if (isStartTrend) {

                        trendSeries.setxStartPos(event2.getX());
                        trendSeries.setxStart(-1);
                        trendSeries.setyStartPos(event2.getY());
                        trendSeries.setyStart(-1);

                        invalidate();
                        return true;

                    } else if (isEndTrend) {

                        trendSeries.setxEndPos(event2.getX());
                        trendSeries.setxStart(-1);
                        trendSeries.setyEndPos(event2.getY());
                        trendSeries.setxStart(-1);

                        invalidate();
                        return true;

                    }

                }
            }
            //repaint();
            //return false;
        } else if (magnify) {
            magnifyView();
            repaint();
            return false;
        } else if (fiboLine) {
            for (TrendSeries series : renderer.get(mainChart).getFiboLineSeries()) {

                if (series.isPointTouched()) {
                    if (isDragFibonnaciLine) {

                        float diffX = event2.getX() - fiboLinePoints.x;
                        float diffY = event2.getY() - fiboLinePoints.y;


                        if (tempStartFiboLinePoint != null) {
                            fiboLineSeries.setxStartPos(tempStartFiboLinePoint.getX() + diffX);
                            fiboLineSeries.setxStart(-1);
                            fiboLineSeries.setyStartPos((tempStartFiboLinePoint.getY() + diffY));
                            fiboLineSeries.setyStart(-1);

                            fiboLineSeries.setxEndPos((tempEndFiboLinePoint.getX() + diffX));
                            fiboLineSeries.setxEnd(-1);
                            fiboLineSeries.setyEndPos((tempEndFiboLinePoint.getY() + diffY));
                            fiboLineSeries.setyEnd(-1);
                        }


                        repaint();
                        return true;

                    } else if (isStartFibonnaciLine) {

                        fiboLineSeries.setxStartPos(event2.getX());
                        fiboLineSeries.setxStart(-1);
                        fiboLineSeries.setyStartPos(event2.getY());
                        fiboLineSeries.setyStart(-1);

                        invalidate();
                        return true;

                    } else if (isEndFibonnaciLine) {

                        fiboLineSeries.setxEndPos(event2.getX());
                        fiboLineSeries.setxStart(-1);
                        fiboLineSeries.setyEndPos(event2.getY());
                        fiboLineSeries.setxStart(-1);

                        invalidate();
                        return true;

                    }

                }
            }
            repaint();
            return false;

        }

        // Get the pointer count ( Touch count in screen )
        int pointerCount = isAPIGreaterThen4 ? ChartWrapper.getPointerCount(event2) : 1;

        // If pointer count not equal to 2, then do panning
        if (pointerCount != 2) {
            // Panning

            // Used to stop panning suddenly after pinch zoom.
            if (lastPinchTouchTime != -1) {
                long now = new Date().getTime();
                if ((lastPinchTouchTime + 750) > now) {
                    return false;
                }
            }

            // If X current position is greater then old X position, pan
            // left.
            if (xCurPos > oldXPos) {
                // Left Panning

                // Pan all the charts
                int startMarginValue = 0;
                float distancePanned = xCurPos - oldXPos;
                for (int i = 0; i < renderer.size(); i++) {

                    double curVirtualValue = renderer.get(i).getVirtualValues();

                    if ((curVirtualValue - distancePanned) > startMarginValue) {

                        renderer.get(i).setVirtualValues(curVirtualValue - distancePanned);
                        notifyChartData.setVirtualValues(curVirtualValue - distancePanned);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                        renderer.get(i).setPannedPixels(renderer.get(i).getPannedPixels() + distancePanned);

                        notifyChartData.setPannedPixels(renderer.get(i).getPannedPixels() + distancePanned);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);
                    } else {
                        renderer.get(i).setVirtualValues(startMarginValue);
                        notifyChartData.setVirtualValues(startMarginValue);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                        if (renderer.get(mainChart).isYAxisRightAlign()) {
                            renderer.get(i).setPannedPixels((float) (renderer.get(i).getPannedPixels() + curVirtualValue));
                        } else {
                            renderer.get(i).setPannedPixels(startMarginValue);
                        }

                        notifyChartData.setPannedPixels((float) (renderer.get(i).getPannedPixels() + curVirtualValue));
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                    }
                }

            } else {
                // If X current position is lesser then old x position, pan
                // right.
                // Right Panning

                int startMarginValue = 0;
                float distancePanned = oldXPos - xCurPos;
                for (int i = 0; i < renderer.size(); i++) {

                    double curVirtualValue = renderer.get(i).getVirtualValues();

                    if ((curVirtualValue + distancePanned) + renderer.get(i).getWidth() <= renderer.get(i).getMaxX()) {

                        renderer.get(i).setVirtualValues(curVirtualValue + distancePanned);

                        notifyChartData.setVirtualValues(curVirtualValue + distancePanned);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                        renderer.get(i).setPannedPixels(renderer.get(i).getPannedPixels() - distancePanned);

                        notifyChartData.setPannedPixels(renderer.get(i).getPannedPixels() - distancePanned);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                    } else {
                        // Right End
                        renderer.get(i).setVirtualValues(renderer.get(i).getMaxX() - renderer.get(i).getWidth());
                        notifyChartData.setVirtualValues(renderer.get(i).getMaxX() - renderer.get(i).getWidth());
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                        if (renderer.get(mainChart).isYAxisRightAlign()) {
                            renderer.get(i).setPannedPixels(startMarginValue);
                        } else {
                            renderer.get(i).setPannedPixels((float) (renderer.get(i).getMaxX() - renderer.get(i).getWidth()));
                        }

                        notifyChartData.setPannedPixels(startMarginValue);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                        notifyChartData.setPannedPixels(startMarginValue);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                    }

                }
            }

            oldXPos = xCurPos;

            repaint();

        } else {
            // If pointer count equal to 2, then do pinch zoom
            // Pinch Zoom

            // Multi-touch supports starts from API Level 5.
            // If API Level greater then 4, go ahead.
            if (!isAPIGreaterThen4) return false;

            // Check pinch zoom is enabled or not. If enabled, go ahead
            if (!renderer.get(mainChart).isPinchZoom()) {
                return false;
            }

            // Currently we are not supporting pinch zoom for Streaming Chart
            if (renderer.get(mainChart).getChartType() == ChartType.StreamingChart)
                return false;

            // Get the X & Y position of event2(2nd touch point).
            // Used for pinch zoom.
            if (isAPIGreaterThen4) {
                xCurFirstPos = ChartWrapper.getX(event2, 0);
                yCurFirstPos = ChartWrapper.getY(event2, 0);
                xCurSecPos = ChartWrapper.getX(event2, 1);
                yCurSecPos = ChartWrapper.getY(event2, 1);
            }

            // Calculate the distance between two points
            currentDistance = (float) Math.sqrt(Math.pow(xCurSecPos - xCurFirstPos, 2) + Math.pow(yCurSecPos - yCurFirstPos, 2));

            if (previousDistance != -1) {
                float interval = renderer.get(mainChart).getIntervalBtwPoints();

                // If the previous distance is greater then current distance,
                // Zoom Out
                if (previousDistance > currentDistance) {
                    // Zoom Out ( Ex:- 50 Points -> 60 Points )
                    interval -= renderer.get(mainChart).getZoomRate();

                    int length = 0;
                    if (renderer.get(mainChart).getChartType() == ChartType.ProfitLossChart) {
                        length = renderer.get(mainChart).getxLabelSeries().length;
                    } else {
                        length = renderer.get(mainChart).getxLabelValue().size();
                    }

                    float baseInterval = ((renderer.get(mainChart).getWidth() - (renderer.get(mainChart).getEndPointSpace() * 2)) / (length - 1));

                    if (interval < baseInterval) {
                        interval = baseInterval;
                    }
                } else {
                    // If the previous distance is lesser then current distance,
                    // Zoom In
                    // Zoom in ( Ex:- 60 Points -> 50 Points )

                    // Check current interval is lesser then max zoom rate.
                    if (interval < renderer.get(mainChart).getMaxZoomRate()) {
                        interval += renderer.get(mainChart).getZoomRate();
                        if (interval > renderer.get(mainChart).getMaxZoomRate())
                            interval = renderer.get(mainChart).getMaxZoomRate();
                    }
                }

                renderer.get(mainChart).setIntervalBtwPoints(interval);
                notifyChartData.setIntervalBtwPoints(interval);
                if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);

                if (renderer.get(mainChart).getChartType() == ChartType.ProfitLossChart) {
                    new CalculatePoints().calculateXPointsForProfitLossChart(renderer.get(mainChart), interval);
                } else {
                    new CalculatePoints().calculateXPoints(renderer.get(mainChart), interval);
                }

                List<Double> xPoints = renderer.get(mainChart).getxAxisPositions();
                double maxX = renderer.get(mainChart).getMaxX();

                for (int i = 0; i < renderer.size(); i++) {

                    renderer.get(i).setMaxX(maxX);
                    renderer.get(i).setxAxisPositions(xPoints);
                    renderer.get(i).setIntervalBtwPoints(interval);

                    int endIndex = renderer.get(i).getEndIndex();
                    int startIndex = renderer.get(i).getStartIndex();

                    int midIndex = (endIndex - startIndex) / 2;
                    midIndex = startIndex + midIndex;

                    double val =0.00;
                    if(xPoints.size()>midIndex) {
                        xPoints.get(midIndex);
                    }

                    double virtual = (val - (renderer.get(mainChart).getWidth() / 2));

                    if ((renderer.get(mainChart).getWidth() / 2) >= val) {
                        virtual = 0;
                    }

                    if ((virtual + renderer.get(mainChart).getWidth()) > renderer.get(mainChart).getMaxX()) {
                        virtual = renderer.get(mainChart).getMaxX() - renderer.get(mainChart).getWidth();

                        renderer.get(i).setPannedPixels(0);
                        notifyChartData.setPannedPixels(0);
                        if (notifyListener != null)
                            notifyListener.onNotifyData(notifyChartData);

                    }

                    renderer.get(i).setVirtualValues(virtual);
                    notifyChartData.setVirtualValues(virtual);
                    if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);

                }

            }

            previousDistance = currentDistance;
            lastPinchTouchTime = new Date().getTime();
            repaint();

        }

        return false;
        /*} else {
            repaint();
            return false;
        }*/

    }

    private boolean isPointOnLine(TrendPoint point, TrendPoint start, TrendPoint end) {
        boolean flag = false;
        double d1 = distance(point, start, 0);
        double d2 = distance(point, end, 0);
        double d = distance(start, end, 0);
        flag = Math.abs(d - (d1 + d2)) < 3;

        if (!flag) {
            d1 = distance(point, start, 0);
            d2 = distance(point, end, 0);
            d = distance(start, end, 0);
            flag = Math.abs(d - (d1 + d2)) < 3;
        }
        return flag;
    }

    private double distance(TrendPoint p, TrendPoint q, int status) {
        double dx = (p.x - status) - (q.x); // horizontal difference
        double dy = (p.y - status) - (q.y); // vertical difference
        return Math.sqrt(dx * dx + dy * dy); // distance using Pythagoras theorem
    }

    private boolean isCenterPoint(TrendPoint point, TrendPoint start, TrendPoint end) {
        float x, y;
        x = ((start.x + 100 + end.x) / 2);
        y = ((start.y + 100 + end.y) / 2);
        return Math.abs(x - point.x) <= 5 && Math.abs(y - point.y) <= 5;
    }


    @Override
    public boolean onSingleTapUp(MotionEvent event) {

        // Return if it is streaming chart
        if (renderer.get(mainChart).getChartType() == ChartType.StreamingChart) {
            return true;
        }

        if (renderer.get(mainChart).isSingleTapOverride()) {
            // Single Tap Overrode
            if (singleTapListener != null) {
                singleTapListener.onSingleTap();
            }
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (magnify) {
                // Clear the drawn magnify bitmap
                magnifyBitmap = null;
                repaint();
            }
        }

        // Cross Hairs
        if (crossHairs) {
            xCurPos = event.getX();
            yCurPos = event.getY();
        } else if (trendLine) {

            if (renderer.get(mainChart).isDeleteTrendline()) {
                return true;
            }

            if (renderer.get(mainChart).getTrendSeries().size() < 1) {
                long now = new Date().getTime();
                if ((lastPinchTouchTime + 750) > now) {
                    return false;
                } else {
                    if (isStartTrendLine) {
                        trendSeries = new TrendSeries();
                        trendSeries.setxStartPos(oldXPos);
                        trendSeries.setxEndPos(oldXPos);
                        trendSeries.setyStartPos(oldYPos);
                        trendSeries.setyEndPos(oldYPos);
                        isStartTrendLine = false;
                    } else {

                        trendSeries.setxEndPos(oldXPos);
                        trendSeries.setyEndPos(oldYPos);
                        isStartTrendLine = true;
                        trendSeries = setXYTrendPositions(trendSeries);
                        renderer.get(mainChart).addTrendSeries(trendSeries);

                    }
                    repaint();
                }
                lastPinchTouchTime = now;
            }
        } else if (magnify) {
            xCurPos = event.getX();
            yCurPos = event.getY();
        } else if (fiboLine) {
            if (renderer.get(mainChart).isDeleteFiboLine()) {
                return true;
            }

            if (renderer.get(mainChart).getFiboLineSeries().size() < 1) {
                long now = new Date().getTime();
                if ((lastPinchTouchTime + 750) > now) {
                    return false;
                } else {
                    if (isStartFiboLine) {
                        fiboLineSeries = new TrendSeries();
                        fiboLineSeries.setxStartPos(oldXPos);
                        fiboLineSeries.setxEndPos(oldXPos);
                        fiboLineSeries.setyStartPos(oldYPos);
                        fiboLineSeries.setyEndPos(oldYPos);
                        isStartFiboLine = false;
                    } else {

                        fiboLineSeries.setxEndPos(oldXPos);
                        fiboLineSeries.setyEndPos(oldYPos);
                        isStartFiboLine = true;
                        fiboLineSeries = setXYFibonnaciPositions(fiboLineSeries);
                        renderer.get(mainChart).addFiboLineSeries(fiboLineSeries);

                    }
                    repaint();
                }
                lastPinchTouchTime = now;
            }

        } else {
            // Remove Indicator or Double Click Action
            int X = (int) event.getX();
            int Y = (int) event.getY();

            // Check whether the X & Y points tapped on Outline Indicator Remove
            // icon.
            for (int i = 0; i < points.size(); i++) {

                if (points.get(i).x == 0) continue;

                // check if inside the bounds of the ball (circle)
                // get the center for the ball
                int centerX = (int) (points.get(i).x + convertFloatToDip(5));
                int centerY = (int) (points.get(i).y + convertFloatToDip(5));

                // calculate the radius from the touch to the center of the ball
                double radCircle = Math.sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y) * (centerY - Y)));

                // if the radius is smaller then 10 (radius of a ball is 5),
                // then
                // it must be on the ball
                if (radCircle < convertFloatToDip(10)) {

                    // Ex:- MACD(12,25,6) - MACD:12:25:6
                    String indicatorName = renderer.get(i).getIndicatorLabel().replace("(", ":").replace(",", ":").replace(")", "");

                    if (indicatorName.equalsIgnoreCase(ChartConstants.VOLUME)) {
                        renderer.get(mainChart).setShowVolumeChart(false);
                    }

                    removeList.add(indicatorName);

                    // Send removed indicator name to user
                    if (removeIndicatorListener != null) {
                        removeIndicatorListener.onRemoveIndicator(indicatorName);
                    }

                    GreekChartLogger.logMsg("Indicator Removed = " + indicatorName);

                    break;
                }

            }

            long thisTime = System.currentTimeMillis();
            if (thisTime - lastTouchTime < 250) {
                // Double tap

                float interval = renderer.get(mainChart).getIntervalBtwPoints();

                // Check whether the tap position is more or less near
                if (in_circle(oldDoubleXPos, oldDoubleYPos, event.getX(), event.getY())) {

                    if (renderer.get(mainChart).isDoubleTapOverride()) {
                        // Double Tap Overrode
                        if (doubleTapListener != null) {
                            doubleTapListener.onDoubleTap();

                        }
                        return true;
                    }

//                    EventBus.getDefault().post("false");
                    if (panningEnabled) {

                        boolean normalZoom = false;

                        if (renderer.get(mainChart).getIntervalBtwPoints() == renderer.get(mainChart).getBaseIntervalBtwPoints()) {
                            // Full zoom Out

                            normalZoom = false;

                            int length = 0;
                            if (renderer.get(mainChart).getChartType() == ChartType.ProfitLossChart) {
                                length = renderer.get(mainChart).getxLabelSeries().length;
                            } else {
                                length = renderer.get(mainChart).getxLabelValue().size();
                            }

                            interval = ((renderer.get(mainChart).getWidth() - (renderer.get(mainChart).getEndPointSpace() * 2)) / (length - 1));

                            renderer.get(mainChart).setPannedPixels(0);
                            notifyChartData.setPannedPixels(0);
                            if (notifyListener != null)
                                notifyListener.onNotifyData(notifyChartData);

                        } else {

                            // Normal Zoom Out

                            normalZoom = true;
                            interval = renderer.get(mainChart).getBaseIntervalBtwPoints();

                        }

                        renderer.get(mainChart).setIntervalBtwPoints(interval);

                        if (renderer.get(mainChart).getChartType() == ChartType.ProfitLossChart) {
                            new CalculatePoints().calculateXPointsForProfitLossChart(renderer.get(mainChart), interval);
                        } else {
                            new CalculatePoints().calculateXPoints(renderer.get(mainChart), interval);
                        }

                        List<Double> xPoints = renderer.get(mainChart).getxAxisPositions();

                        double maxX = renderer.get(mainChart).getMaxX();

                        float pannedPixels = renderer.get(mainChart).getPannedPixels();

                        notifyChartData.setIntervalBtwPoints(interval);
                        if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);

                        for (int i = 0; i < renderer.size(); i++) {

                            renderer.get(i).setMaxX(maxX);
                            renderer.get(i).setxAxisPositions(xPoints);

                            renderer.get(i).setIntervalBtwPoints(interval);

                            if (!normalZoom) {

                                double virtual = (maxX - renderer.get(mainChart).getWidth());

                                if (renderer.get(mainChart).getWidth() >= maxX) {
                                    virtual = 0;
                                }

                                renderer.get(i).setVirtualValues(virtual);
                                notifyChartData.setVirtualValues(virtual);
                                if (notifyListener != null)
                                    notifyListener.onNotifyData(notifyChartData);

                                renderer.get(i).setPannedPixels(pannedPixels);
                                notifyChartData.setPannedPixels(pannedPixels);
                                if (notifyListener != null)
                                    notifyListener.onNotifyData(notifyChartData);

                            } else {

                                int endIndex = renderer.get(i).getEndIndex();
                                int startIndex = renderer.get(i).getStartIndex();

                                int midIndex = (endIndex - startIndex) / 2;
                                midIndex = startIndex + midIndex;

                                double val = xPoints.get(midIndex);

                                double virtual = (val - (renderer.get(mainChart).getWidth() / 2));

                                if ((renderer.get(mainChart).getWidth() / 2) >= val) {
                                    virtual = 0;
                                }

                                if ((virtual + renderer.get(mainChart).getWidth()) > renderer.get(mainChart).getMaxX()) {
                                    virtual = renderer.get(mainChart).getMaxX() - renderer.get(mainChart).getWidth();

                                    renderer.get(i).setPannedPixels(0);
                                    notifyChartData.setPannedPixels(0);
                                    if (notifyListener != null)
                                        notifyListener.onNotifyData(notifyChartData);

                                }

                                renderer.get(i).setVirtualValues(virtual);
                                notifyChartData.setVirtualValues(virtual);
                                if (notifyListener != null)
                                    notifyListener.onNotifyData(notifyChartData);

                                renderer.get(i).setPannedPixels((float) (renderer.get(mainChart).getMaxX() - (virtual + renderer.get(mainChart).getWidth())));
                                notifyChartData.setPannedPixels((float) (renderer.get(mainChart).getMaxX() - (virtual + renderer.get(mainChart).getWidth())));
                                if (notifyListener != null)
                                    notifyListener.onNotifyData(notifyChartData);

                            }
                        }
                    }

                }

                lastTouchTime = -1;
            } else {
                // Too slow
                lastTouchTime = thisTime;
            }

            oldDoubleXPos = event.getX();
            oldDoubleYPos = event.getY();
        }

        repaint();
        return true;
    }

    private boolean in_circle(double center_x, double center_y, double x, double y) {
        double dist = Math.sqrt(Math.pow((center_x - x), 2) + Math.pow((center_x - x), 2));
        return dist <= DOUBLE_TOUCH_RADIUS;
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {

        try {
            return this.gestureDetector.onTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        if (longClickListener != null) {
            longClickListener.onLongClick();
            xCurPos = event.getX();
            yCurPos = event.getY();
        }
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * @return As view
     */
    public View chartView() {
        return this;
    }

    /**
     * @return Chart as Bitmap
     */
    public Bitmap toBitmapFromView() {
        View v = this;
        Bitmap b = Bitmap.createBitmap((int) renderer.get(mainChart).getChartWidth(), (int) renderer.get(mainChart).getChartHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

   /* @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.v("On Double tAb","Doubletap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.v("On Double tAb","Doubletap");
        return false;
    }*/
}