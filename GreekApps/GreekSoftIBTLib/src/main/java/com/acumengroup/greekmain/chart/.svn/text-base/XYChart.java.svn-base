package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartIndicatorType;
import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartType;
import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.dataset.XYSeries;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.draw.GreekChartListener;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.trendline.TrendPoint;
import com.acumengroup.greekmain.chart.trendline.TrendSeries;
import com.acumengroup.greekmain.chart.util.GreekDateFormatter;
import com.acumengroup.greekmain.chart.util.MathHelper;
import com.acumengroup.greekmain.chart.xml.IndicatorData;
import com.acumengroup.greekmain.core.app.AccountDetails;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public abstract class XYChart extends AbstractChart {

    private final static NumberFormat FORMAT = NumberFormat.getNumberInstance();
    protected XYMultipleSeriesDataset mDataset;
    protected ChartSettings mRenderer;
    protected int PSARWidth = 3;
    protected String VALUE_SEPERATOR = ":";
    private DecimalFormat numberFormat = new DecimalFormat("####0.00");
    private float scale;
    private Rect measeureHeightRect;
    private GreekChartListener.onNotifyListener notifyListener;
    private int extraSpaceInBottoom = 10;
    // Touch Point Rectangle Surround Space between text & box
    private int surroundSpace = 5;
    // For displaying OHLC during cross hair
    private List<Float> openValues = new ArrayList<>();
    private List<Float> highValues = new ArrayList<>();
    private List<Float> lowValues = new ArrayList<>();
    // For Cross Hairs
    private List<Float> xValue = new ArrayList<>();
    private List<Float> yValue = new ArrayList<>();
    private int INLINE_IND_COLORS[] = {0xFF9b664b, 0xFF81d300, 0xFFa148ed, 0xFF669cff, 0xFF00cb69, 0xFFff7538, 0xFF00c2bb, 0xFF999999, 0xFFff48fd, 0xFFffb400, 0xFF00b540, 0xFF00a2ff};
    private NotifyChartData notifyChartData = new NotifyChartData();

    public XYChart(Context context, XYMultipleSeriesDataset paramXYMultipleSeriesDataset, ChartSettings paramXYMultipleSeriesRenderer) {
        this.mDataset = paramXYMultipleSeriesDataset;
        this.mRenderer = paramXYMultipleSeriesRenderer;
        scale = context.getResources().getDisplayMetrics().density;
        measeureHeightRect = new Rect();

        String value = "";
        for (int i =0; i < mRenderer.getyLabelDecimalPoint(); i++) {
            value += "0";
        }
        if (mRenderer.getyLabelDecimalPoint() == 0) {
            numberFormat = new DecimalFormat("####0");
        } else {
            numberFormat = new DecimalFormat("####0." + "00");
        }

        setDecimalFormatSymbols(numberFormat);

    }

    private void setDecimalFormatSymbols(DecimalFormat formatter) {
        if (formatter != null) {
            // For Multilanguage Support - Set Decimal separator as '.'
            DecimalFormatSymbols custom = new DecimalFormatSymbols();
            custom.setDecimalSeparator('.');
            formatter.setDecimalFormatSymbols(custom);
        }
    }

    /**
     * @param floatValue
     * @return - Multiple with the scale density of the screen.
     */
    private float convertFloatToDip(float floatValue) {
        return (floatValue * scale);
    }

    /*
     * (non-Javadoc)
     *
     * @see AbstractChart#drawCrossHairs(android.graphics.Canvas,
     * float, float, float, float)
     *
     * Draw Cross Hair
     */
    @Override
    public void drawCrossHairs(Canvas canvas, float xPos, float yPos, float width, float height) {

        float left, top, right, bottom;

        left = mRenderer.getLeftMargin();
        right = width - mRenderer.getRightMargin();
        top = mRenderer.getTopMargin();
        bottom = height - mRenderer.getBottomMargin();

        // Clicked on Y label or Margin area
        if (xPos > right) {
            xPos = right;
        } else if (xPos < left) {
            xPos = left;
        }

        float xSelectedPoint = closestValue(xPos, xValue);
        int index = xValue.indexOf(xSelectedPoint);
        if (index == -1) index = 0;
        float ySelectedPoint = yValue.get(index);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
        paint.setColor(mRenderer.getCrossHairsColor());
        paint.setStrokeWidth(mRenderer.getCrossHairsWidth());
        paint.getTextBounds("a", 0, 1, measeureHeightRect);

        if (mRenderer.getTypeface() != null) {
            paint.setTypeface(mRenderer.getTypeface());
        }

        // X Axis Line
        canvas.drawLine(left, ySelectedPoint, right, ySelectedPoint, paint);

        // Y Axis Line
        canvas.drawLine(xSelectedPoint, top, xSelectedPoint, bottom, paint);

        String myDateFormat = "yyyy/MM/dd";
        if (mRenderer.getCrossHairCustomDateFormat() == null) {
            switch (mRenderer.getCrossHairDateFormat()) {
                case DEFAULT_yyyyMMdd:
                    myDateFormat = "yyyy/MM/dd kk:mm";
                    break;
                case DAY_MONTH_YEAR_ddMMyyyy:
                    myDateFormat = "dd/MM/yyyy";
                    break;
                case HOUR_MINUTES_HHmm:
                    myDateFormat = "kk:mm";
                    break;
                case MONTH_DAY_MMdd:
                    myDateFormat = "MM/dd";
                    break;
                case MONTH_DAY_YEAR_MMddyyyy:
                    myDateFormat = "MM/dd/yyyy";
                    break;
            }
        } else {
            myDateFormat = mRenderer.getCrossHairCustomDateFormat();
        }
        String yLabel;
        if (AccountDetails.getIsscriptcurr()) {
//            yLabel= /*numberFormat.format(*/mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index] + ""/*)*/;
            yLabel=  String.format("%.4f", mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index]);
        }else{
             yLabel = String.format("%.2f", mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index]);
        }
//        String yLabel = /*numberFormat.format(*/mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index] + ""/*)*/;
        String xLabel = "";
        if (mRenderer.getxLabelValue() != null) {
            xLabel = (String) android.text.format.DateFormat.format(myDateFormat, mRenderer.getxLabelValue().get(mRenderer.getStartIndex() + index));
        } else if (mRenderer.getxLabelSeries() != null) {
            xLabel = "" + mRenderer.getxLabelSeries()[mRenderer.getStartIndex() + index];
        }

        int cornerRadius = 5;

        // Y Position Round Rectangle
        if (mRenderer.isYAxisRightAlign()) {
            RectF yRoundRectF = new RectF(right, (ySelectedPoint - convertFloatToDip(surroundSpace)) - (measeureHeightRect.height() / 2), right + (paint.measureText(String.valueOf(yLabel))) + convertFloatToDip(surroundSpace * 2), (ySelectedPoint + convertFloatToDip(surroundSpace)) + (measeureHeightRect.height() / 2));

            canvas.drawRoundRect(yRoundRectF, convertFloatToDip(cornerRadius), convertFloatToDip(cornerRadius), paint);
        } else {
            RectF yRoundRectF = new RectF(left - (paint.measureText(String.valueOf(yLabel)) + convertFloatToDip(surroundSpace * 2)), (ySelectedPoint - convertFloatToDip(surroundSpace)) - (measeureHeightRect.height() / 2), left, (ySelectedPoint + convertFloatToDip(surroundSpace)) + (measeureHeightRect.height() / 2));

            canvas.drawRoundRect(yRoundRectF, convertFloatToDip(cornerRadius), convertFloatToDip(cornerRadius), paint);
        }

        // X Position Round Rectangle
        RectF xRoundRectF = new RectF((xSelectedPoint - convertFloatToDip(surroundSpace)) - (int) paint.measureText(xLabel) / 2, (height - convertFloatToDip(extraSpaceInBottoom)) - measeureHeightRect.height(), xSelectedPoint + ((int) paint.measureText(xLabel) / 2) + convertFloatToDip(surroundSpace), ((height - convertFloatToDip(extraSpaceInBottoom - (surroundSpace * 2)))));

        canvas.drawRoundRect(xRoundRectF, convertFloatToDip(cornerRadius), convertFloatToDip(cornerRadius), paint);

        // Draw junction circle
        canvas.drawCircle(xSelectedPoint, ySelectedPoint, convertFloatToDip(4), paint);

        paint.setColor(mRenderer.getCrossHairsMeasureTextColor());
        paint.setTextAlign(Align.CENTER);

        // X Position Text
        canvas.drawText(xLabel, xSelectedPoint, bottom + (measeureHeightRect.height() / 2) + convertFloatToDip(surroundSpace), paint);

        // Y Position Text
        if (mRenderer.isYAxisRightAlign()) {
            paint.setTextAlign(Align.LEFT);
            canvas.drawText(yLabel, right + convertFloatToDip(surroundSpace), ySelectedPoint + convertFloatToDip(surroundSpace), paint);
        } else {
            paint.setTextAlign(Align.LEFT);
            canvas.drawText(yLabel, ((left - paint.measureText(String.valueOf(yLabel)) - convertFloatToDip(surroundSpace))), ySelectedPoint + convertFloatToDip(surroundSpace), paint);
        }

        // Show brief date in the left corner of chart.

        String myMainChartDateFormat = "MMM dd,yyyy HH:mm:ss";
        switch (mRenderer.getCrossHairMainChartDateFormat()) {
            case DEFAULT_MMMddyyyy_HHmmss:
                myMainChartDateFormat = "MMM dd,yyyy HH:mm:ss";
                break;
            case MONTH_DAY_YEAR_MMMddyyyy:
                myMainChartDateFormat = "MMM dd,yyyy";
                break;
            case DAY_MONTH_YEAR_ddMMyyyy:
                myMainChartDateFormat = "dd/MM/yyyy";
                break;
            case HOUR_MINUTES_HHmm:
                myMainChartDateFormat = "kk:mm";
                break;
            case MONTH_DAY_MMdd:
                myMainChartDateFormat = "MM/dd";
                break;
            case MONTH_DAY_YEAR_MMddyyyy:
                myMainChartDateFormat = "MM/dd/yyyy";
                break;
        }

        DateFormat formatter = new SimpleDateFormat(myMainChartDateFormat, Locale.ENGLISH);

        String dateValue = "";
        if (mRenderer.isShowVolumeChart()) {
            dateValue = formatter.format(mRenderer.getxLabelValue().get(mRenderer.getStartIndex() + index)) + " Vol:" + (long) mRenderer.getVolumeValues()[mRenderer.getStartIndex() + index];
        } else {

            if (mRenderer.getxLabelValue() != null) {
                dateValue = formatter.format(mRenderer.getxLabelValue().get(mRenderer.getStartIndex() + index));
            }
        }

        // Draw date text
        if (mRenderer.isMainChart()) {
            if (mRenderer.isShowCrossHairDate()) {
                paint.setTextSize(convertFloatToDip(mRenderer.getDateFontSize()));
                paint.setColor(mRenderer.getDateTextColor());
                Rect measeureHeightRect = new Rect();
                paint.getTextBounds("a", 0, 1, measeureHeightRect);
                paint.setTextAlign(Align.LEFT);

                canvas.drawText(dateValue, left + (convertFloatToDip(5)), top + 70 + (convertFloatToDip(8) + (measeureHeightRect.height() / 2)), paint);

                if (mRenderer.isShowOHLCOnCrossHair()) {


                    if (mRenderer.getChartType() == ChartType.OHLCChart || mRenderer.getChartType() == ChartType.CandleStickChart) {
                        int vIndex = mRenderer.getStartIndex() + index;
                        double openvalue = ((double[]) mRenderer.getChartData().get("open"))[vIndex];
                        double highvalue = ((double[]) mRenderer.getChartData().get("high"))[vIndex];
                        double closevalue = ((double[]) mRenderer.getChartData().get("close"))[vIndex];
                        double lowvalue = ((double[]) mRenderer.getChartData().get("low"))[vIndex];

                        //canvas.drawText("O : " + + highValues.get(vIndex) + " ; L : " + lowValues.get(vIndex) + " ; C : " + yLabel, left + (convertFloatToDip(5)), top+70+ (convertFloatToDip(8) + 3 * measeureHeightRect.height()), paint);

                        canvas.drawText("O: " + openvalue + "  H:" + highvalue + "  L: " + lowvalue + "  C: " + yLabel, left + (convertFloatToDip(5)), top + 70 + (convertFloatToDip(8) + 3 * measeureHeightRect.height()), paint);
                    }
                }/*else{

                    if (mRenderer.getChartType() == ChartType.OHLCChart || mRenderer.getChartType() == ChartType.CandleStickChart) {
                        int vIndex= mRenderer.getStartIndex()+index;
                        double openvalue= ((double[]) mRenderer.getChartData().get("open"))[57];
                        double highvalue= ((double[]) mRenderer.getChartData().get("high"))[57];
                        double closevalue= ((double[]) mRenderer.getChartData().get("close"))[57];
                        double lowvalue= ((double[]) mRenderer.getChartData().get("low"))[57];

                        //canvas.drawText("O : " + + highValues.get(vIndex) + " ; L : " + lowValues.get(vIndex) + " ; C : " + yLabel, left + (convertFloatToDip(5)), top+70+ (convertFloatToDip(8) + 3 * measeureHeightRect.height()), paint);

                        canvas.drawText("O: " + openvalue+ "  H:" + highvalue + "  L: " + lowvalue + "  C: " + yLabel, left + (convertFloatToDip(5)), top+70+ (convertFloatToDip(8) + 3 * measeureHeightRect.height()), paint);
                    }
                }*/
            }
        }
    }

    /**
     * @param nearValue - Set the near by value
     * @param values    - Array list to find closest value
     * @return closest value from the given array
     */
    private float closestValue(float nearValue, List<Float> values) {
        float min = Integer.MAX_VALUE;
        float closest = nearValue;

        for (Float v : values) {
            final float diff = Math.abs(v - nearValue);
            if (diff < min) {
                min = diff;
                closest = v;
            }
        }

        return closest;
    }

    private double closestValue(double nearValue, List<Double> values) {
        double min = Integer.MAX_VALUE;
        double closest = nearValue;

        for (Double v : values) {
            final double diff = Math.abs(v - nearValue);
            if (diff < min) {
                min = diff;
                closest = v;
            }
        }

        return closest;
    }

    /**
     * For Y Labels we need to get the minimum & maximum value. From that we can
     * measure other value
     *
     * @param start           start value
     * @param end             final value
     * @param approxNumLabels desired number of labels
     * @return collection containing {start value, end value, increment}
     */
    /*
     * private List<Double> getYLabels(final double start, final double end,
     * final int approxNumLabels) {
     *
     * if (mRenderer.getShowYGridLastlabel()) { int approxNoLabels =
     * approxNumLabels; FORMAT.setMaximumFractionDigits(5); List<Double> labels
     * = new ArrayList<Double>();
     *
     * if (mRenderer.isMainChart()) { approxNoLabels++; }
     *
     * float startValue = (float) ((end - start) / (approxNoLabels - 1));
     *
     * for (int j = 0; j < approxNoLabels; j++) {
     *
     * double d = 0; if (mRenderer.isMainChart()) { d = start + (j *
     * startValue); } else { d = start + j * ((end - start) / approxNoLabels); }
     *
     * try { labels.add(d); } catch (Exception localParseException) { } } return
     * labels; } else {
     *
     * FORMAT.setMaximumFractionDigits(5); List<Double> labels = new
     * ArrayList<Double>(); for (int j = 0; j < approxNumLabels; j++) { double d
     * = start + j * ((end - start) / approxNumLabels); try { labels.add(d); }
     * catch (Exception localParseException) { } } return labels; } }
     */

    // Wrote for profit loss chart.
    private List<Double> getYLabels(final double start, final double end, final int approxNumLabels) {

        if (mRenderer.getShowYGridLastlabel()) {
            int approxNoLabels = approxNumLabels;
            if(AccountDetails.getIsscriptcurr()) {
                FORMAT.setMaximumFractionDigits(4);
            }else{
                FORMAT.setMaximumFractionDigits(2);
            }
            List<Double> labels = new ArrayList<>();

            if (mRenderer.isMainChart()) {
                approxNoLabels++;
            }

            float startValue = (float) ((end - start) / (approxNoLabels - 1));

            for (int j = 0; j < approxNoLabels; j++) {

                double d;
                if (mRenderer.isMainChart()) {
                    d = start + (j * startValue);
                } else {
                    d = start + j * ((end - start) / approxNoLabels);
                }

                try {
                    labels.add(d);
                } catch (Exception ignored) {
                }
            }
            return labels;
        } else {

//            FORMAT.setMaximumFractionDigits(5);
            if(AccountDetails.getIsscriptcurr()) {
                FORMAT.setMaximumFractionDigits(4);
            }else{
                FORMAT.setMaximumFractionDigits(2);
            }
            List<Double> labels = new ArrayList<>();
            for (int j = 0; j < approxNumLabels; j++) {
                double d = start + j * ((end - start) / approxNumLabels);
                String stringdouble;
                if(AccountDetails.getIsscriptcurr()) {
                    stringdouble=  String.format("%.4f",d);
                }else{
                    stringdouble=  String.format("%.2f",d);
                }
                try {
                    labels.add(Double.parseDouble(stringdouble));
                } catch (Exception ignored) {
                }
            }
            return labels;
        }
    }

    /**
     * @param height - Chart Height for Y Labels
     * @param width  - Chart Width for X Labels
     * @param paint  - paint to measure the text size
     */
    private void calculateXYLabelCount(int height, float width, Paint paint) {

        // Measure X & Y Labels according to width & height if default values
        // exists else show labels according to user mentioned.

        // ----------------- X Label -----------------

        // getXLabelsNo() default value is -1. If value is -1, then X labels
        // count will be
        // depends upon the chart width else custom count.
        if (mRenderer.getXGridCount() == -1) {
            mRenderer.setXGridCount((int) (width / (paint.measureText("MM/DD") * 1.8)));

            // If the calculated count is less then 2, then set count as 2.
            if (mRenderer.getXGridCount() < 2) {
                mRenderer.setXGridCount(2);
            }
        }

        // ----------------- Y Label -----------------

        if (mRenderer.isMainChart()) {
            // Main Chart height will be greater then other charts. So multiply
            // the text height * 5 for single text.
            if (mRenderer.getYGridCount() == -1) {

                mRenderer.setYGridCount(height / ((measeureHeightRect.height() * 5)));
            }
        } else {
            mRenderer.setYGridCount(height / ((measeureHeightRect.height() * 3)));
        }

        // If the calculated count is less then 1, then set count as 1.
        if (mRenderer.getYGridCount() < 1) {
            mRenderer.setYGridCount(1);
        }
    }

    private Hashtable<String, float[]> calculateXY(float bottom, double yPixelsPerUnit, List<Double> xPoints) {

        xValue.clear();
        yValue.clear();
        openValues.clear();
        highValues.clear();
        lowValues.clear();

        List<XYSeries> series = mDataset.getSeries();

        Hashtable<String, float[]> pointsHashtable = new Hashtable<>();

        for (int j = 0; j < series.size(); j++) {
            int length = (mRenderer.getEndIndex() - mRenderer.getStartIndex()) * 2;

            float point[] = new float[length];

            int k = mRenderer.getStartIndex();

            for (int i10 = 0; i10 < length; i10 += 2, k++) {

                // X Value
//                if(xPoints.size()>0)
                point[i10] = (float) (xPoints.get(k) - mRenderer.getVirtualValues() + mRenderer.getLeftMargin());
                // Y Value
                point[(i10 + 1)] = (float) (bottom - (yPixelsPerUnit * (series.get(j).getY(k) - mRenderer.getYAxisMin())));

                /*
                 * if (j != 0 || (mRenderer.getChartType() !=
                 * ChartType.OHLCChart && mRenderer .getChartType() !=
                 * ChartType.CandleStickChart) || (!mRenderer.isMainChart())) {
                 *
                 * // If the end point is greater then chart plotting width, //
                 * make // last point as width. if (point[i10] > right) { float
                 * curValue = point[i10]; float prevValue = (float)
                 * (xPoints.get(k - 1) - mRenderer.getVirtualValues() +
                 * mRenderer .getLeftMargin()); float pointDiff = curValue -
                 * prevValue; float marginDiff = curValue - right;
                 *
                 * float yCurValue = point[(i10 + 1)]; float yPrevValue =
                 * (float) (bottom - yPixelsPerUnit (series.get(j).getY(k - 1) -
                 * mRenderer .getYAxisMin()));
                 *
                 * float hght = 0f; if (yPrevValue > yCurValue) { hght =
                 * yPrevValue - yCurValue; } else { hght = yCurValue -
                 * yPrevValue; }
                 *
                 * float widPerc = (marginDiff / pointDiff) * 100;
                 *
                 * float perHghtValue = (hght * (100 - widPerc)) / 100;
                 *
                 * // X Value point[i10] = right;
                 *
                 * // Y Value if (yPrevValue > yCurValue) { point[i10 + 1] =
                 * yPrevValue - perHghtValue; } else { point[i10 + 1] =
                 * yPrevValue + perHghtValue; } }
                 *
                 * if (point[i10] < left) { float curValue = point[i10]; float
                 * nextValue = (float) (xPoints.get(k + 1) -
                 * mRenderer.getVirtualValues() + mRenderer .getLeftMargin());
                 * float pointDiff = nextValue - curValue; float marginDiff =
                 * left - curValue;
                 *
                 * float yCurValue = point[(i10 + 1)]; float yNextValue =
                 * (float) (bottom - yPixelsPerUnit (series.get(j).getY(k + 1) -
                 * mRenderer .getYAxisMin()));
                 *
                 * float hght = 0f; if (yNextValue > yCurValue) { hght =
                 * yNextValue - yCurValue; } else { hght = yCurValue -
                 * yNextValue; }
                 *
                 * float widPerc = (marginDiff / pointDiff) * 100;
                 *
                 * float perHghtValue = (hght * (100 - widPerc)) / 100;
                 *
                 * // X Value point[i10] = left;
                 *
                 * // Y Value if (yNextValue > yCurValue) { point[i10 + 1] =
                 * yNextValue - perHghtValue; } else { point[i10 + 1] =
                 * yNextValue + perHghtValue; } }
                 *
                 * }
                 */

                // xValue & yValue are used for cross hair measurement.
                if (series.get(j).getTitle().equalsIgnoreCase(ChartConstants.CLOSE) || series.get(j).getTitle().equalsIgnoreCase(ChartConstants.PL_CHART_PLO)) {
                    xValue.add(point[(i10)]);
                    yValue.add(point[(i10 + 1)]);
                } else if (series.get(j).getTitle().equalsIgnoreCase(ChartConstants.OPEN)) {
                    openValues.add(point[(i10 + 1)]);
                } else if (series.get(j).getTitle().equalsIgnoreCase(ChartConstants.HIGH)) {
                    highValues.add(point[(i10 + 1)]);
                } else if (series.get(j).getTitle().equalsIgnoreCase(ChartConstants.LOW)) {
                    lowValues.add(point[(i10 + 1)]);
                }
            }

            pointsHashtable.put(series.get(j).getTitle(), point);

        }

        return pointsHashtable;
    }

    private float findStartStopX(int start, int stop, float interval, float startValue) {
        float value;

        if (start <= stop) {
            value = ((stop - start) * interval) + startValue;

        } else {
            value = startValue - ((start - stop) * interval);

        }
        return value;
    }

    private void drawTrendLine(Canvas canvas, Paint paint, float bottom, double yPixelsPerUnit, double yAxisMin, double yAxisMax, double left, double right, double top) {
        // List<TrendSeries> trendSeries = mRenderer.getTrendSeries();

        for (TrendSeries series : mRenderer.getTrendSeries()) {

            /*
             * paint.setColor(Color.MAGENTA); paint.setStyle(Paint.Style.FILL);
             * canvas.drawCircle(series.getNewPoint().x, series.getNewPoint().y,
             * 10, paint);
             *
             * if(0 == 0) continue;
             */

            // One time settings
            if (series.getxStart() == -1) {

                /*
                 * float xStartPos = series.getxStartPos(); int startIndex =
                 * xValue .indexOf(closestValue(xStartPos, xValue)) +
                 * mRenderer.getStartIndex();
                 *
                 * float xEndPos = series.getxEndPos(); int endIndex =
                 * xValue.indexOf(closestValue(xEndPos, xValue)) +
                 * mRenderer.getStartIndex();
                 */

                float xStartPos = series.getxStartPos();
                // int startIndex = xValue
                // .indexOf(closestValue(xStartPos, xValue))
                // + mRenderer.getStartIndex();

                float xEndPos = series.getxEndPos();

                // int endIndex = xValue.indexOf(closestValue(xEndPos, xValue))
                // + mRenderer.getStartIndex();

                int startIndex, endIndex;

                /*
                 * if (xStartPos < left) { int ind = (int) (((left - xStartPos)
                 * + closestValue( xEndPos, xValue)) / mRenderer
                 * .getIntervalBtwPoints()); startIndex = endIndex - ind; }
                 */

                if (xStartPos < xValue.get(0)) {
                    startIndex = (int) Math.floor(((mRenderer.getxAxisPositions().get(mRenderer.getStartIndex()) - (xValue.get(0) - xStartPos)) / mRenderer.getIntervalBtwPoints()));
                } else {
                    startIndex = (int) Math.floor(((mRenderer.getxAxisPositions().get(mRenderer.getStartIndex()) + xStartPos) / mRenderer.getIntervalBtwPoints()));
                }

                if (xEndPos > xValue.get(xValue.size() - 1)) {
                    endIndex = (int) Math.ceil(((((xEndPos - xValue.get(xValue.size() - 1)) + xValue.get(xValue.size() - 1)) + mRenderer.getxAxisPositions().get(mRenderer.getStartIndex())) / mRenderer.getIntervalBtwPoints()));

                } else {
                    endIndex = (int) Math.ceil(((mRenderer.getxAxisPositions().get(mRenderer.getEndIndex() - 1) - (xValue.get(xValue.size() - 1) - xEndPos)) / mRenderer.getIntervalBtwPoints()));
                }

                series.setxStart(startIndex);
                series.setxEnd(endIndex);

                double yStart = yAxisMin + (yAxisMax - yAxisMin) * ((bottom - series.getyStartPos()) / bottom);
                series.setyStart(yStart);
                double yEnd = yAxisMin + (yAxisMax - yAxisMin) * ((bottom - series.getyEndPos()) / bottom);
                series.setyEnd(yEnd);
                double yCenter = ((yStart + yEnd) / 2);
                series.setyCenter(yCenter);
            }

            // Current trend is out of screen.
            if (mRenderer.getEndIndex() < series.getxStart() || mRenderer.getStartIndex() > series.getxEnd()) {
                continue;
            }

            float startY = (float) (bottom - yPixelsPerUnit * ((Double.parseDouble(series.getyStart() + "")) - yAxisMin));
            float stopY = (float) (bottom - yPixelsPerUnit * ((Double.parseDouble(series.getyEnd() + "")) - yAxisMin));

            float startX = findStartStopX(mRenderer.getStartIndex(), series.getxStart(), mRenderer.getIntervalBtwPoints(), xValue.get(0));
            float stopX = findStartStopX(mRenderer.getStartIndex(), series.getxEnd(), mRenderer.getIntervalBtwPoints(), xValue.get(0));

            series.setStart(new TrendPoint(startX, startY));
            series.setEnd(new TrendPoint(stopX, stopY));
            series.setCenter(new TrendPoint((startX + stopX) / 2, (startY + stopY) / 2));

            // Not to jump from one point to another point while drag or
            // creating..
            float f = (series.getxStartPos() - startX);
            if (f > 0) {
                if ((series.getxStartPos() - startX) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxStartPos() > startX) startX = series.getxStartPos();
                }
            } else {
                if ((startX - series.getxStartPos()) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxStartPos() < startX) startX = series.getxStartPos();
                }

            }

            // startX = series.getxStartPos();
            // stopY = series.getxEndPos();

            float f1 = (series.getxEndPos() - stopX);
            if (f1 > 0) {
                if ((series.getxEndPos() - stopX) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxEndPos() > stopX) stopX = series.getxEndPos();
                }
            } else {
                if ((stopX - series.getxEndPos()) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxEndPos() < stopX) stopX = series.getxEndPos();
                }

            }

            // Temp
            // startX = series.getxStartPos();
            // stopX = series.getxEndPos();

            // Draw Trend Line
            paint.setColor(Color.GREEN);
            paint.setStyle(Style.STROKE);
            canvas.drawLine(startX, startY, stopX, stopY, paint);

            if (mRenderer.isTrendline()) {

                paint.setColor(Color.MAGENTA);
                paint.setStyle(Style.FILL);

                float circleRadius = 5 * scale;

                if (mRenderer.isDeleteTrendline()) {
                    paint.setColor(Color.WHITE);
                    // Start Circle
                    canvas.drawCircle(startX, startY, circleRadius, paint);

                    // End Circle
                    canvas.drawCircle(stopX, stopY, circleRadius, paint);

                    paint.setColor(Color.BLACK);
                    paint.setStyle(Style.STROKE);
                    // Start Circle
                    canvas.drawCircle(startX, startY, circleRadius, paint);

                    // End Circle
                    canvas.drawCircle(stopX, stopY, circleRadius, paint);

                    paint.setColor(Color.RED);
                    canvas.drawLine(startX - circleRadius - 1, startY, startX + circleRadius - 1, startY, paint);

                    canvas.drawLine(stopX - circleRadius - 1, stopY, stopX + circleRadius - 1, stopY, paint);

                    /*
                     * canvas.drawLine(startX+(circleRadius/2),
                     * startY+(circleRadius/2),
                     * startX+(circleRadius)+circleRadius,
                     * startY+(circleRadius), paint);
                     * canvas.drawLine(startX+circleRadius+(circleRadius/2),
                     * startY+(circleRadius/2),
                     * startX+(circleRadius*2)-circleRadius,
                     * startY+(circleRadius*2), paint);
                     * canvas.drawLine(stopX+(circleRadius/2),
                     * stopY+(circleRadius/2), stopX+circleRadius,
                     * stopY+circleRadius, paint);
                     */

                } else {
                    // Start Circle
                    canvas.drawCircle(startX, startY, circleRadius, paint);

                    // End Circle
                    canvas.drawCircle(stopX, stopY, circleRadius, paint);
                }

                // Center Rectangle
                paint.setColor(Color.BLACK);
                paint.setStyle(Style.STROKE);
                canvas.drawRect(((startX + stopX) / 2) - 5, ((startY + stopY) / 2) - 5, ((startX + stopX) / 2) + 5, ((startY + stopY) / 2) + 5, paint);
            }
        }

    }

    private void drawFiboLine(Canvas canvas, Paint paint, float bottom, double yPixelsPerUnit, double yAxisMin, double yAxisMax, double left, double right, double top) {
        // List<TrendSeries> trendSeries = mRenderer.getTrendSeries();

        for (TrendSeries series : mRenderer.getFiboLineSeries()) {

            if (series.getxStart() == -1) {

                float xStartPos = series.getxStartPos();

                float xEndPos = series.getxEndPos();

                int startIndex, endIndex;


                if (xStartPos < xValue.get(0)) {
                    startIndex = (int) Math.floor(((mRenderer.getxAxisPositions().get(mRenderer.getStartIndex()) - (xValue.get(0) - xStartPos)) / mRenderer.getIntervalBtwPoints()));
                } else {
                    startIndex = (int) Math.floor(((mRenderer.getxAxisPositions().get(mRenderer.getStartIndex()) + xStartPos) / mRenderer.getIntervalBtwPoints()));
                }

                if (xEndPos > xValue.get(xValue.size() - 1)) {

                    endIndex = (int) Math.ceil(((((xEndPos - xValue.get(xValue.size() - 1)) + xValue.get(xValue.size() - 1)) + mRenderer.getxAxisPositions().get(mRenderer.getStartIndex())) / mRenderer.getIntervalBtwPoints()));

                } else {
                    endIndex = (int) Math.ceil(((mRenderer.getxAxisPositions().get(mRenderer.getEndIndex() - 1) - (xValue.get(xValue.size() - 1) - xEndPos)) / mRenderer.getIntervalBtwPoints()));
                }


                series.setxStart(startIndex);
                series.setxEnd(endIndex);

                double yStart = yAxisMin + (yAxisMax - yAxisMin) * ((bottom - series.getyStartPos()) / bottom);
                series.setyStart(yStart);
                double yEnd = yAxisMin + (yAxisMax - yAxisMin) * ((bottom - series.getyEndPos()) / bottom);
                series.setyEnd(yEnd);
                double yCenter = ((yStart + yEnd) / 2);
                series.setyCenter(yCenter);
            }

            // Current trend is out of screen.
            if (mRenderer.getEndIndex() < series.getxStart() || mRenderer.getStartIndex() > series.getxEnd()) {
                continue;
            }

            float startY = (float) (bottom - yPixelsPerUnit * ((Double.parseDouble(series.getyStart() + "")) - yAxisMin));
            float stopY = (float) (bottom - yPixelsPerUnit * ((Double.parseDouble(series.getyEnd() + "")) - yAxisMin));

            float startX = findStartStopX(mRenderer.getStartIndex(), series.getxStart(), mRenderer.getIntervalBtwPoints(), xValue.get(0));
            float stopX = findStartStopX(mRenderer.getStartIndex(), series.getxEnd(), mRenderer.getIntervalBtwPoints(), xValue.get(0));


            series.setStart(new TrendPoint(startX, startY));
            series.setEnd(new TrendPoint(stopX, stopY));
            series.setCenter(new TrendPoint((startX + stopX) / 2, (startY + stopY) / 2));

            // Not to jump from one point to another point while drag or
            // creating..
            float f = (series.getxStartPos() - startX);
            if (f > 0) {
                if ((series.getxStartPos() - startX) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxStartPos() > startX) startX = series.getxStartPos();
                }
            } else {
                if ((startX - series.getxStartPos()) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxStartPos() < startX) startX = series.getxStartPos();
                }

            }

            // startX = series.getxStartPos();
            // stopY = series.getxEndPos();

            float f1 = (series.getxEndPos() - stopX);
            if (f1 > 0) {
                if ((series.getxEndPos() - stopX) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxEndPos() > stopX) stopX = series.getxEndPos();
                }
            } else {
                if ((stopX - series.getxEndPos()) < mRenderer.getIntervalBtwPoints()) {
                    if (series.getxEndPos() < stopX) stopX = series.getxEndPos();
                }

            }

            // Temp
            // startX = series.getxStartPos();
            // stopX = series.getxEndPos();

            // Draw Trend Line
            paint.setColor(Color.RED);
            paint.setStyle(Style.STROKE);
            canvas.drawLine(startX, startY, startX, stopY, paint);

            float xSelectedPoint = closestValue(startX, xValue);
            int index = xValue.indexOf(xSelectedPoint);
            if (index == -1) index = 0;
            // float ySelectedPoint = yValue.get(index);

            paint.setColor(Color.GRAY);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
            canvas.drawLine(startX, startY, startX + 200, startY, paint);
            canvas.drawLine(startX, (float) (startY - (startY - stopY) * .786), startX + 200, (float) (startY - (startY - stopY) * .786), paint);
            canvas.drawLine(startX, (float) (startY - (startY - stopY) * .618), startX + 200, (float) (startY - (startY - stopY) * .618), paint);

            canvas.drawLine(startX, (float) (startY - (startY - stopY) * .5), startX + 200, (float) (startY - (startY - stopY) * .5), paint);
            canvas.drawLine(startX, (float) (startY - (startY - stopY) * .382), startX + 200, (float) (startY - (startY - stopY) * .382), paint);
            canvas.drawLine(startX, (float) (startY - (startY - stopY) * .236), startX + 200, (float) (startY - (startY - stopY) * .236), paint);

            canvas.drawLine(startX, stopY, startX + 200, stopY, paint);

            paint.setColor(Color.BLACK);
            paint.setStyle(Style.FILL);
            String yLabel = numberFormat.format(mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index]);
            paint.setTextSize(12);
            paint.setPathEffect(new DashPathEffect(new float[]{0, 0}, 0));

            paint.getTextBounds("a", 0, 1, measeureHeightRect);

            // canvas.drawText(yLabel, startX+20,
            // (float)(startY-(startY-stopY)*.5)-3, paint);

            canvas.drawText(yLabel, startX + 20, (float) (startY - (startY - stopY) * .5) - 3, paint);
            // canvas.drawText(yLabel, startX+20,
            // (float)(startY-(startY-stopY)*.5)-3, paint);

            // canvas.drawLine(startX, startY, startX+50, stopY, paint);

            if (mRenderer.isFiboLine()) {

                paint.setColor(Color.MAGENTA);
                paint.setStyle(Style.FILL);

                float circleRadius = 5 * scale;

                if (mRenderer.isDeleteFiboLine()) {
                    paint.setColor(Color.WHITE);
                    // Start Circle
                    canvas.drawCircle(startX, startY, circleRadius, paint);

                    // End Circle
                    canvas.drawCircle(stopX, stopY, circleRadius, paint);

                    paint.setColor(Color.BLACK);
                    paint.setStyle(Style.STROKE);
                    // Start Circle
                    canvas.drawCircle(startX, startY, circleRadius, paint);

                    // End Circle
                    canvas.drawCircle(stopX, stopY, circleRadius, paint);

                    paint.setColor(Color.RED);
                    canvas.drawLine(startX - circleRadius - 1, startY, startX - circleRadius - 1, startY, paint);

                    canvas.drawLine(stopX - circleRadius - 1, stopY, stopX + circleRadius - 1, stopY, paint);

                    /*
                     * canvas.drawLine(startX+(circleRadius/2),
                     * startY+(circleRadius/2),
                     * startX+(circleRadius)+circleRadius,
                     * startY+(circleRadius), paint);
                     * canvas.drawLine(startX+circleRadius+(circleRadius/2),
                     * startY+(circleRadius/2),
                     * startX+(circleRadius*2)-circleRadius,
                     * startY+(circleRadius*2), paint);
                     * canvas.drawLine(stopX+(circleRadius/2),
                     * stopY+(circleRadius/2), stopX+circleRadius,
                     * stopY+circleRadius, paint);
                     */

                } else {
                    // Start Circle
                    canvas.drawCircle(startX, startY, circleRadius, paint);

                    // End Circle
                    canvas.drawCircle(startX, stopY, circleRadius, paint);
                }

                // Center Rectangle
                paint.setColor(Color.BLACK);
                paint.setStyle(Style.STROKE);
                paint.setPathEffect(new DashPathEffect(new float[]{0, 0}, 0));

                canvas.drawRect(((startX + startX) / 2) - 5, ((startY + stopY) / 2) - 5, ((startX + startX) / 2) + 5, ((startY + stopY) / 2) + 5, paint);
            }
        }

    }

    @Override
    public void drawMagnifier(Canvas canvas, Bitmap bitmap, float xPos, float yPos, float width, float height) {

        if (bitmap != null) {
            int magnifyRadius = mRenderer.getMagnifyRadius();
            int margin = 5;

            if (mRenderer.isMagnifyMovable()) {

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                canvas.drawCircle(xPos, yPos - bitmap.getWidth(), magnifyRadius, paint);

                paint.setColor(Color.BLACK);
                paint.setStyle(Style.STROKE);
                paint.setStrokeWidth(2);
                canvas.drawCircle(xPos, yPos - bitmap.getWidth(), magnifyRadius, paint);

                canvas.drawBitmap(bitmap, xPos - (bitmap.getWidth() / 2), yPos - (bitmap.getHeight()) - magnifyRadius, null);

                paint.setColor(Color.RED);
                paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
                canvas.drawLine(xPos - (bitmap.getWidth() / 2), yPos - (bitmap.getHeight()) + margin, xPos + (bitmap.getWidth() / 2), yPos - (bitmap.getHeight()) + margin, paint);

                canvas.drawLine(xPos - magnifyRadius + (bitmap.getWidth() / 2), yPos - (bitmap.getHeight()) - magnifyRadius, xPos - magnifyRadius + (bitmap.getWidth() / 2), yPos - magnifyRadius, paint);

                // paint.setPathEffect(null);
                paint.setPathEffect(new DashPathEffect(new float[]{0, 0}, 0));

                float left, top, right, bottom;

                left = mRenderer.getLeftMargin();
                right = width - mRenderer.getRightMargin();
                top = mRenderer.getTopMargin();
                bottom = height - mRenderer.getBottomMargin();

                // Clicked on Y label or Margin area
                if (xPos > right) {
                    xPos = right;
                } else if (xPos < left) {
                    xPos = left;
                }

                float xSelectedPoint = closestValue(xPos, xValue);
                int index = xValue.indexOf(xSelectedPoint);
                if (index == -1) index = 0;

                String myDateFormat = "yyyy/MM/dd";
                switch (mRenderer.getCrossHairDateFormat()) {
                    case DEFAULT_yyyyMMdd:
                        myDateFormat = "yyyy/MM/dd";
                        break;
                    case DAY_MONTH_YEAR_ddMMyyyy:
                        myDateFormat = "dd/MM/yyyy";
                        break;
                    case HOUR_MINUTES_HHmm:
                        myDateFormat = "kk:mm";
                        break;
                    case MONTH_DAY_MMdd:
                        myDateFormat = "MM/dd";
                        break;
                    case MONTH_DAY_YEAR_MMddyyyy:
                        myDateFormat = "MM/dd/yyyy";
                        break;
                }

                String yLabel = numberFormat.format(mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index]);
                String xLabel = (String) android.text.format.DateFormat.format(myDateFormat, mRenderer.getxLabelValue().get(mRenderer.getStartIndex() + index));

                paint.setColor(Color.BLACK);
                paint.setStyle(Style.FILL);
                paint.setTextSize(12);

                paint.getTextBounds("a", 0, 1, measeureHeightRect);

                canvas.drawText(yLabel, xPos + magnifyRadius, yPos - bitmap.getHeight() + (measeureHeightRect.height() / 2), paint);
                canvas.drawText(xLabel, xPos - (paint.measureText(xLabel) / 2), yPos - magnifyRadius + measeureHeightRect.height() + margin, paint);

                return;
            }

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawCircle((width / 2) - (bitmap.getWidth() / 2), magnifyRadius + margin, magnifyRadius, paint);

            paint.setColor(Color.BLACK);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawCircle((width / 2) - (bitmap.getWidth() / 2), magnifyRadius + margin, magnifyRadius, paint);

            canvas.drawBitmap(bitmap, (width / 2) - (bitmap.getWidth()), margin, null);

            paint.setColor(Color.RED);
            paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
            canvas.drawLine((width / 2) - (bitmap.getWidth()), (bitmap.getHeight() / 2) + margin, (width / 2), (bitmap.getHeight() / 2) + margin, paint);

            canvas.drawLine((width / 2) - (bitmap.getWidth() / 2), margin, (width / 2) - (bitmap.getWidth() / 2), (bitmap.getHeight()) + margin, paint);

            paint.setPathEffect(new DashPathEffect(new float[]{0, 0}, 0));
            float left, top, right, bottom;

            left = mRenderer.getLeftMargin();
            right = width - mRenderer.getRightMargin();
            top = mRenderer.getTopMargin();
            bottom = height - mRenderer.getBottomMargin();

            // Clicked on Y label or Margin area
            if (xPos > right) {
                xPos = right;
            } else if (xPos < left) {
                xPos = left;
            }

            float xSelectedPoint = closestValue(xPos, xValue);
            int index = xValue.indexOf(xSelectedPoint);
            if (index == -1) index = 0;

            String myDateFormat = "yyyy/MM/dd";
            switch (mRenderer.getCrossHairDateFormat()) {
                case DEFAULT_yyyyMMdd:
                    myDateFormat = "yyyy/MM/dd";
                    break;
                case DAY_MONTH_YEAR_ddMMyyyy:
                    myDateFormat = "dd/MM/yyyy";
                    break;
                case HOUR_MINUTES_HHmm:
                    myDateFormat = "kk:mm";
                    break;
                case MONTH_DAY_MMdd:
                    myDateFormat = "MM/dd";
                    break;
                case MONTH_DAY_YEAR_MMddyyyy:
                    myDateFormat = "MM/dd/yyyy";
                    break;
            }

            String yLabel = numberFormat.format(mRenderer.getyLabelValue()[mRenderer.getStartIndex() + index]);
            String xLabel = (String) android.text.format.DateFormat.format(myDateFormat, mRenderer.getxLabelValue().get(mRenderer.getStartIndex() + index));

            paint.setColor(Color.BLACK);
            paint.setStyle(Style.FILL);
            paint.setTextSize(12);

            paint.getTextBounds("a", 0, 1, measeureHeightRect);

            canvas.drawText(yLabel, (width / 2), (bitmap.getWidth() / 2) + margin + (measeureHeightRect.height() / 2), paint);
            canvas.drawText(xLabel, (width / 2) - (bitmap.getWidth() / 2) - (paint.measureText(xLabel) / 2), (bitmap.getWidth()) + (margin * 2) + (measeureHeightRect.height()), paint);

        }

    }

    /**
     * The graphical representation of the XY chart.
     *
     * @param canvas the canvas to paint
     * @param xPos   the top left x value of the view to draw
     * @param yPos   the top left y value of the view to draw
     * @param width  the width of the view to draw
     * @param height the height of the view to draw
     */
    public void draw(Canvas canvas, float xPos, int yPos, float width, int height) {


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));

        if (mRenderer.getTypeface() != null) {
            paint.setTypeface(mRenderer.getTypeface());
        }

        // need to change -
        width = width - mRenderer.getRightMargin() - mRenderer.getLeftMargin();

        paint.getTextBounds("a", 0, 1, measeureHeightRect);
        int i = measeureHeightRect.height() + ((int) convertFloatToDip(extraSpaceInBottoom));

        // Left or X starting position
        float left = xPos + mRenderer.getLeftMargin();

        // Top or Y starting position
        int top = yPos + mRenderer.getTopMargin();

        // Right position or Chart Drawing End Position
        float right = width + mRenderer.getLeftMargin();

        mRenderer.setRight(right);
        mRenderer.setLeft(left);

        float bottom;
        if (mRenderer.isShowXLabels()) bottom = yPos + height - i;
        else {
            if (mRenderer.isShowVolumeChart()) bottom = yPos + height;
            else bottom = yPos + height - convertFloatToDip(5);
        }

        mRenderer.setChartHeight(convertFloatToDip(height));

        drawBackground(mRenderer, canvas, xPos, yPos, (int) width, height, paint);

        mRenderer.setWidth(width);

        List<Double> xPoints = mRenderer.getxAxisPositions();

        getStartEndIndex(xPoints, width);

        calculateYAxisMinMax();

        double yAxisMin = mRenderer.getYAxisMin();
        double yAxisMax = mRenderer.getYAxisMax();

        // Calculate the Y Axis Pixels Per Unit.
        double yPixelsPerUnit = (float) ((bottom - top) / (yAxisMax - yAxisMin));

        mRenderer.setyPixelsPerUnit(yPixelsPerUnit);
        mRenderer.setBottom(bottom);

        // This hashtable will contain key and corresponding values.
        Hashtable<String, float[]> pointsHashtable = calculateXY(bottom, yPixelsPerUnit, xPoints);

        // Chart Title
        if (mRenderer.isShowChartTitle()) {
            paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
            paint.setColor(mRenderer.getChartTitleColor());
            canvas.drawText(mRenderer.getChartTitle(), left + (convertFloatToDip(5)), top + convertFloatToDip(10), paint);
//            canvas.drawText( mRenderer.getChartOHLC(), left + (convertFloatToDip(5)), top+5+ (convertFloatToDip(8) + 3 * measeureHeightRect.height()), paint);
//            canvas.drawText(mRenderer.getChartTitle(), xPos + width / 2, top + convertFloatToDip(10), paint);

//            canvas.drawT
        }

        // Chart Title
        if (AccountDetails.getIsOHLCSHOW()) {
            paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
            paint.setColor(mRenderer.getChartTitleColor());
//            canvas.drawText(mRenderer.getChartTitle(), left + (convertFloatToDip(5)), top + convertFloatToDip(10), paint);
            canvas.drawText(mRenderer.getChartOHLC(), left + (convertFloatToDip(5)), top + 5 + (convertFloatToDip(8) + 3 * measeureHeightRect.height()), paint);
//            canvas.drawText(mRenderer.getChartTitle(), xPos + width / 2, top + convertFloatToDip(10), paint);

//            canvas.drawT
        }


        paint.setTextSize(convertFloatToDip(mRenderer.getChartLabelTextSize()));
        paint.setTextAlign(Align.LEFT);

        calculateXYLabelCount(height, width, paint);

        List<Double> xLabels = mDataset.getSeriesAt(0).getX();
        List<Double> yLabels = getYLabels(yAxisMin, yAxisMax, mRenderer.getYGridCount());

        if (mRenderer.isShowXLabels() || mRenderer.isShowYLabels()) {
            paint.setColor(mRenderer.getTextColor());
            paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
            paint.setTextAlign(Align.LEFT);
        }

        canvas.save();
        canvas.clipRect(left, top, right, bottom);

        drawXGrid(canvas, paint, left, right, width, top, bottom);
        drawYGrid(canvas, paint, left, right, bottom, top);

        // plot the main chart points
        drawSeries(canvas, paint, pointsHashtable, Math.min(bottom, (float) (bottom + yPixelsPerUnit * yAxisMin)));

        // plot the Inline Indicators chart
        drawInline(canvas, paint, pointsHashtable);

        // Draw Trend Line
        drawTrendLine(canvas, paint, bottom, yPixelsPerUnit, yAxisMin, yAxisMax, left, right, top);

        // Draw Fibo Line
        drawFiboLine(canvas, paint, bottom, yPixelsPerUnit, yAxisMin, yAxisMax, left, right, top);

        // canvas.clipRect(left, top, right, bottom,Op.REPLACE);
        canvas.restore();

        paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
        paint.setColor(mRenderer.getTextColor());
        paint.setStyle(Style.FILL);

        // boolean rightAlign = true;
        boolean rightAlign = mRenderer.isYAxisRightAlign();

        // if(mRenderer.getChartType() == ChartType.ProfitLossChart ||
        // mRenderer.getChartType() == ChartType.ConsensusChart)
        // rightAlign = false;

        // For X Labels we need to pick the values and then we need to draw
        drawXLabels(xLabels, canvas, paint, left, width, bottom, mRenderer.getXGridCount(), rightAlign);

        // For Y Labels we need to get the minimum & maximum value from that we
        // can
        // measure other values
        drawYLabels(yLabels, canvas, paint, left, right, bottom, top, rightAlign);

        // Draw Last Price
        if (mRenderer.isMainChart()) {
            if (mRenderer.isShowLastPrice() && mRenderer.isShowYLabels()) {

                String value = numberFormat.format(mRenderer.getyLabelValue()[mRenderer.getEndIndex() - 1]);

                drawEndValue(canvas, paint, value, right, top, bottom, yPixelsPerUnit, yAxisMin, mRenderer.getLastPriceBG(), mRenderer.getLastPriceTextColor());

            }
        }

        // Draw Previous Close Value Text
        drawPreviousCloseValue(canvas, left, right, bottom, yPixelsPerUnit, yAxisMin);
    }

    private void drawPreviousCloseValue(Canvas canvas, float left, float right, float bottom, double yPixelsPerUnit, double yAxisMin) {
        if (mRenderer.isMainChart()) {
            if (mRenderer.isShowPrevClose()) {
                if (mRenderer.getPrevClose() != 0) {

                    double close = mRenderer.getPrevClose();
                    float f = (float) (bottom - yPixelsPerUnit * (close - yAxisMin));
                    if (f > bottom) return;

                    Paint linePaint = new Paint();
                    linePaint.setTextSize(convertFloatToDip(mRenderer.getPrevCloseTextFontSize()));
                    linePaint.setColor(mRenderer.getPrevCloseLineColor());
                    DashPathEffect dashPath = new DashPathEffect(new float[]{10, 5}, 1);
                    linePaint.setPathEffect(dashPath);
                    canvas.drawLine(left, f, right, f, linePaint);
                    linePaint.setPathEffect(null);
                    linePaint.setColor(mRenderer.getPrevCloseTextColor());
                    linePaint.setTextAlign(Align.LEFT);
                    Rect measeureHeightRect = new Rect();
                    linePaint.getTextBounds("a", 0, 1, measeureHeightRect);
                    canvas.drawText("Prev.Close:" + numberFormat.format(mRenderer.getPrevClose()), left + 5, f - (measeureHeightRect.height()), linePaint);
                }
            }
            // }
        }
    }

    private void drawEndValue(Canvas canvas, Paint paint, String value, float right, float top, float bottom, double yPixelsPerUnit, double yAxisMin, int bgColor, int textColor) {

        paint.setColor(bgColor);
        paint.setStyle(Style.FILL);

        float endValue = (float) (bottom - yPixelsPerUnit * ((Double.parseDouble(value)) - yAxisMin));

        // Should draw in y axis only not beyond it
        if (endValue <= bottom && endValue > top) {

            canvas.drawRect(new RectF(right, endValue - (measeureHeightRect.height()), right + (paint.measureText(value + "00")), endValue + ((measeureHeightRect.height()))), paint);

            paint.setColor(textColor);
            canvas.drawText(value, right + paint.measureText("0"), endValue + (measeureHeightRect.height() / 2), paint);
        }
    }

    private void getStartEndIndex(List<Double> xPoints, float width) {
        int startIndex = xPoints.indexOf(mRenderer.getVirtualValues());

        // Get Pre Nearest Value for Start Index
        if (startIndex == -1)
            startIndex = xPoints.indexOf(MathHelper.preNearestValue(mRenderer.getVirtualValues(), xPoints));

        int endIndex = 0;
        if (xPoints.size() > 0) {

            if (width >= xPoints.get(xPoints.size() - 1)) {

                endIndex = xPoints.size();

            } else {

                endIndex = xPoints.indexOf(mRenderer.getVirtualValues() + width);

                // Get Post Nearest Value for End Index
                if (endIndex == -1)
                    endIndex = xPoints.indexOf(MathHelper.postNearestValue(mRenderer.getVirtualValues() + width, xPoints));

                // For Array handling
                endIndex++;

            }

            // To Give Space in the right side for streaming chart
            if (mRenderer.getChartType() == ChartType.StreamingChart) {
                if ((mRenderer.getVirtualValues() + width) == xPoints.get(xPoints.size() - 1)) {
                    mRenderer.setVirtualValues(mRenderer.getVirtualValues() + mRenderer.getIntervalBtwPoints());
                    notifyChartData.setVirtualValues(mRenderer.getVirtualValues() + mRenderer.getIntervalBtwPoints());
                    if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);

                }
            }

        }

        mRenderer.setStartIndex(startIndex);
        mRenderer.setEndIndex(endIndex);
    }

    private void calculateYAxisMinMax() {

        if (mRenderer.getChartType() == ChartType.ConsensusChart) {
            mRenderer.setYAxisMin(0);
            mRenderer.setYAxisMax(100);
            return;
        }

        List<double[]> yValues = mRenderer.getYAxisValue();
        double yAxisMin;
        double yAxisMax;

        double minMax[] = MathHelper.getMinMaxFromSelectedValue(yValues, mRenderer.getStartIndex(), mRenderer.getEndIndex());

        yAxisMin = minMax[0];
        yAxisMax = minMax[1];

        if (mRenderer.isRoundOffYValues()) {
            int values[] = MathHelper.getGraphPoints(yAxisMin, yAxisMax);
            yAxisMin = values[0];
            yAxisMax = values[1];
        } else {

            if (mRenderer.getChartType() != ChartType.SparkLineChart && mRenderer.getChartType() != ChartType.SparkAreaChart && mRenderer.getChartType() != ChartType.SparkLineColoredChart && mRenderer.getChartType() != ChartType.SparkAreaColoredChart) {
                // Bottom buffer
                // extra space - Add 10% of value to max value from maxmin
                // difference
                yAxisMax = ((yAxisMax - yAxisMin) / 10) + yAxisMax;

                // If min and max values are same, Add/Sub 10% of value to
                // max/min.
                if (yAxisMin == yAxisMax) {
                    yAxisMin -= (yAxisMin * 10) / 100;
                    yAxisMax += (yAxisMax * 10) / 100;
                }
            }
        }
        mRenderer.setYAxisMin(yAxisMin);
        mRenderer.setYAxisMax(yAxisMax);

    }

    public void drawStreaming(Canvas canvas, int xPos, int yPos, float width, int height) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));

        // Drawing part width
        width = width - mRenderer.getRightMargin() - mRenderer.getLeftMargin();

        paint.getTextBounds("a", 0, 1, measeureHeightRect);
        int i = measeureHeightRect.height() + ((int) convertFloatToDip(extraSpaceInBottoom));

        // Left or X starting position
        float left = xPos + mRenderer.getLeftMargin();

        // Top or Y starting position
        int top = yPos + mRenderer.getTopMargin();

        // Right position or Chart Drawing End Position
        float right = width + mRenderer.getLeftMargin();

        float bottom;
        if (mRenderer.isShowXLabels()) bottom = yPos + height - i;
        else {
            if (mRenderer.isShowVolumeChart()) bottom = yPos + height;
            else bottom = yPos + height - convertFloatToDip(2);
        }

        mRenderer.setChartHeight(convertFloatToDip(height));

        drawBackground(mRenderer, canvas, xPos, yPos, (int) width, height, paint);

        mRenderer.setWidth(width);

        List<Double> xPoints = mRenderer.getxAxisPositions();

        getStartEndIndex(xPoints, width);

        List<double[]> yAxisValues = new ArrayList<>();

        for (XYSeries series : mDataset.getSeries()) {
            double[] seriesData = new double[series.getY().size()];
            for (int k = 0; k < seriesData.length; k++) {
                seriesData[k] = series.getY(k);
            }
            yAxisValues.add(seriesData);
        }
        mRenderer.setYAxisValue(yAxisValues);

        calculateYAxisMinMax();

        double yAxisMin = mRenderer.getYAxisMin();
        double yAxisMax = mRenderer.getYAxisMax();

        // Calculate the Y Axis Pixels Per Unit.
        double yPixelsPerUnit = (float) ((bottom - top) / (yAxisMax - yAxisMin));

        // This hashtable will contain key and corresponding values.
        Hashtable<String, float[]> pointsHashtable = calculateXY(bottom, yPixelsPerUnit, xPoints);

        paint.setTextSize(convertFloatToDip(mRenderer.getChartLabelTextSize()));
        paint.setTextAlign(Align.LEFT);

        calculateXYLabelCount(height, width, paint);

        List<Double> yLabels = getYLabels(yAxisMin, yAxisMax, mRenderer.getYGridCount());

        if (mRenderer.isShowXLabels() || mRenderer.isShowYLabels()) {
            paint.setColor(mRenderer.getTextColor());
            paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
            paint.setTextAlign(Align.LEFT);
        }

        canvas.save();
        canvas.clipRect(left, top, right, bottom);

        drawXGrid(canvas, paint, left, right, width, top, bottom);
        drawYGrid(canvas, paint, left, right, bottom, top);

        // plot the points
        drawSeries(canvas, paint, pointsHashtable, Math.min(bottom, (float) (bottom + yPixelsPerUnit * yAxisMin)));

        canvas.restore();

        paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
        paint.setColor(mRenderer.getTextColor());
        paint.setStyle(Style.FILL);

        // For X Labels we need to pick the values and then we need to draw
        drawXLabels(mDataset.getSeriesAt(0).getX(), canvas, paint, left, right - left, bottom, mRenderer.getXGridCount(), true);

        // For Y Labels we need to get the minimum & maximum value from that we
        // can
        // measure other values
        drawYLabels(yLabels, canvas, paint, left, right, bottom, top, true);

        int index = 0;
        for (XYSeries xySeries : mDataset.getSeries()) {
//            if(xySeries.getY().size()>0) {
            String value = numberFormat.format(xySeries.getY().get(xySeries.getY().size() - 1));

            drawEndValue(canvas, paint, value, right, top, bottom, yPixelsPerUnit, yAxisMin, mRenderer.getColors()[index], Color.BLACK);
//            }
            index++;
        }

    }

    /**
     * Makes sure the fraction digit is not displayed, if not needed.
     *
     * @param label the input label value
     * @return the label without the useless fraction digit
     */
    private String getLabel(double label) {
        String text;
        if (label == Math.round(label)) {
            text = Math.round(label) + "";
        } else {
            text = label + "";
        }
        return text;
    }

    private void drawYGrid(Canvas canvas, Paint paint, float left, float right, float bottom, float top) {

        // Horizontal Grid

        int count = mRenderer.getYGridCount();
        paint.setColor(mRenderer.getGridColor());
        paint.setStrokeWidth(mRenderer.getGridWidth());
        paint.setStyle(Style.STROKE);

        if (mRenderer.isShowAxis()) {
            // Top Axis
            if (mRenderer.isMainChart()) canvas.drawLine(left, top, right, top, paint);
            // Bottom Axis
            canvas.drawLine(left, bottom, right, bottom, paint);
        }

        if (!mRenderer.isShowYGrid()) return;

        for (int j = 1; j < count; j++) {
            float yLabel = (count - j) * (bottom - top) / count + top;
            canvas.drawLine(left, yLabel, right, yLabel, paint);
        }

    }

    private void drawYLabels(List<Double> yLabels, Canvas canvas, Paint paint, float left, float right, float bottom, float top, boolean isTextRightAlign) {
        boolean showYLabels = mRenderer.isShowYLabels();

        int length = yLabels.size();

        float singleValue = ((bottom - top) / (length - 1));

        for (int j = 0; j < length; j++) {
            double label = yLabels.get(j);
            String labelText = getLabel(label);
            float yLabel;
            if (mRenderer.getShowYGridLastlabel()) {
                if (mRenderer.isMainChart()) {
                    yLabel = bottom - (singleValue * j);
                } else {
                    yLabel = (length - j) * (bottom - top) / length + top;
                }
            } else {
                yLabel = (length - j) * (bottom - top) / length + top;
            }

            if (showYLabels) {
                paint.setColor(this.mRenderer.getTextColor());

                // Draw small line to connect the left to right grid line & Y
                // label text

                paint.setColor(mRenderer.getGridColor());
                paint.setStrokeWidth(mRenderer.getGridWidth());
                canvas.drawLine(right + 4, yLabel, right, yLabel, paint);

                paint.setTextSize(mRenderer.getChartTitleTextSize() * scale);
                paint.setColor(mRenderer.getTextColor());
                if (mRenderer.isMainChart()) {
                    if (mRenderer.getShowYGridLastlabel()) {
                        if (mRenderer.getChartType() == ChartType.ProfitLossChart) {

                            if (j == (length - 1)) {
                                yLabel = yLabel + 22;
                            } else {
                                yLabel = yLabel + 5;
                            }

                        } else {
                            yLabel = yLabel + 5;
                        }
                    }

                    String value = "";
                    if (mRenderer.getChartType() == ChartType.ProfitLossChart) {
                        value = "$" + numberFormat.format(label);
                    } else {
                        value = numberFormat.format(label);
                    }

                    if (isTextRightAlign) {
                        paint.setTextAlign(Align.LEFT);
                        canvas.drawText(value, right + 5, yLabel, paint);
                    } else {
                        paint.setTextAlign(Align.RIGHT);
                        canvas.drawText(value, left, yLabel, paint);
                    }

                } else {
                    paint.setTextSize(convertFloatToDip(mRenderer.getChartTitleTextSize()));
                    String value = "";
                    if (mRenderer.getIndicatorType() == ChartIndicatorType.OUTLINE) {
                        value = numberFormat.format(Float.parseFloat(labelText));
                    } else {
                        if (Float.parseFloat(labelText) < 10000)
                            value = String.valueOf(Math.round(Float.parseFloat(labelText)));
                        else if (Float.parseFloat(labelText) < 1000000)
                            value = String.valueOf(Math.round(Float.parseFloat(labelText) / 1000) + "K");
                        else if (Float.parseFloat(labelText) < 1000000000)
                            value = String.valueOf(Math.round(Float.parseFloat(labelText) / 1000000) + "M");
                        else if (Float.parseFloat(labelText) > 999999999)
                            value = String.valueOf(Math.round(Float.parseFloat(labelText) / 1000000000) + "B");
                    }

                    if (isTextRightAlign) {
                        canvas.drawText(value, right + paint.measureText("0"), yLabel, paint);
                    } else {
                        canvas.drawText(value, left + paint.measureText("0"), yLabel, paint);
                    }

                }
            }

        }
    }

    private void drawXGrid(Canvas canvas, Paint paint, float left, float right, float width, int top, float bottom) {

        paint.setColor(mRenderer.getGridColor());
        paint.setStrokeWidth(mRenderer.getGridWidth());

        if (mRenderer.isShowAxis()) {
            // Left Axis
            if (mRenderer.isMainChart()) canvas.drawLine(left, bottom, left, top, paint);
            // Right Axis
            canvas.drawLine(width + left - 1, bottom, width + left - 1, top, paint);
        }

        if (!mRenderer.isShowXGrid()) return;

        float f;
        int count = mRenderer.getXGridCount();

        float xGridDistance = (width) / (count - 1);

        double offset = mRenderer.getPannedPixels() % xGridDistance;

        for (double i = 0; i < count; i++) {

            f = (float) (((i * xGridDistance) + offset) + mRenderer.getLeftMargin());

            if (f > width + left || f < left) continue;
            canvas.drawLine(f, bottom, f, top, paint);
        }

    }

    private void drawXLabels(List<Double> xLabels, Canvas canvas, Paint paint, float left, float width, float bottom, int count, boolean isGridRightAlign) {

        boolean showXLabels = mRenderer.isShowXLabels();

        if (!showXLabels) return;

        int length = xLabels.size();
        if (length < 1) return;

        DateFormat localDateFormat = getDateFormat(mRenderer.getDateFormat());

        List<Double> xPoints = mRenderer.getxAxisPositions();

        float xGridDistance = (width) / (count - 1);

        double offset = mRenderer.getPannedPixels() % xGridDistance;

        ArrayList<String> dateFormatList = new ArrayList<>();
        boolean isCustomDateFormat = false;

        // if (mRenderer.getChartType() != ChartType.StreamingChart) {
        if (mRenderer.getChartType() != ChartType.StreamingChart && mRenderer.getChartType() != ChartType.ProfitLossChart) {

            String startDate = (String) android.text.format.DateFormat.format("MMM dd,yyyy", mRenderer.getxLabelValue().get(0));
            String endDate = (String) android.text.format.DateFormat.format("MMM dd,yyyy", mRenderer.getxLabelValue().get(mRenderer.getxLabelValue().size() - 1));

            if (!startDate.equalsIgnoreCase(endDate) && mRenderer.isShowCustomDateFormat()) {

                String curDateFormat = "HH:mm";
                String checkDateFormat = "dd MM yyyy";
                String customDateFormat = "MM/dd HH:mm";
                if (mRenderer.getDateFormat().equalsIgnoreCase("HH:mm")) {
                    curDateFormat = "HH:mm";
                    checkDateFormat = "dd MM yyyy";
                    customDateFormat = "MM/dd HH:mm";
                } else if (mRenderer.getDateFormat().equalsIgnoreCase("MM/dd")) {
                    curDateFormat = "MM/dd";
                    checkDateFormat = "yyyy";
                    customDateFormat = "MM/dd/yyyy";
                } else if (mRenderer.getDateFormat().equalsIgnoreCase("dd/MM")) {
                    curDateFormat = "dd/MM";
                    checkDateFormat = "yyyy";
                    customDateFormat = "dd/MM/yyyy";
                }

                if (mRenderer.getDateFormat().equalsIgnoreCase(curDateFormat)) {
                    isCustomDateFormat = true;
                    for (double i = 0; i < count; i++) {
                        double f = (((i * xGridDistance) + offset) + mRenderer.getVirtualValues());

                        int oldIndex = xPoints.indexOf(closestValue(f, xPoints));

                        long l = Math.round(xLabels.get(oldIndex));

                        String currentDate = GreekDateFormatter.format(checkDateFormat, new Date(l));

                        f = f - xGridDistance;

                        int newIndex = xPoints.indexOf(closestValue(f, xPoints));

                        l = Math.round(xLabels.get(newIndex));

                        String oldDate = GreekDateFormatter.format(checkDateFormat, new Date(l));

                        // For first point we need to show our custom date
                        // format
                        if (oldIndex == 0 || newIndex == 0) {
                            dateFormatList.add(customDateFormat);
                        } else {
                            // For Other points we need to check whether the old
                            // date and new date are same if yes current date
                            // format else
                            // custom date format.
                            if (oldDate.equalsIgnoreCase(currentDate)) {
                                dateFormatList.add(curDateFormat);
                            } else {
                                dateFormatList.add(customDateFormat);
                            }
                        }
                    }
                }
            }

        }

        /*
         * double fi = (((0 * xGridDistance) + offset) + mRenderer
         * .getVirtualValues());
         *
         * double min = xLabels.get(xPoints.indexOf(closestValue(fi, xPoints)));
         *
         * fi = ((((count - 1) * xGridDistance) + offset) + mRenderer
         * .getVirtualValues());
         *
         * double max = xLabels.get(xPoints.indexOf(closestValue(fi, xPoints)));
         */

        double max = xLabels.get(mRenderer.getEndIndex() - 1);
        double min = xLabels.get(mRenderer.getStartIndex());

        double pointValue = width / (max - min);

        min = Math.ceil(min);
        max = Math.ceil(max);

        if (min % 2 == 0) {
            if (max % 2 != 0) {
                max = max + 1;
            }
        } else {
            if (max % 2 == 0) {
                max = max - 1;
            }
        }

        double range = (max - min) / (count - 1);
        // range =Math.ceil(range);

        for (int i = 0; i < count; i++) {
            double f = (((i * xGridDistance) + offset) + mRenderer.getVirtualValues());

            int index = xPoints.indexOf(closestValue(f, xPoints));

            if (mRenderer.getChartType() != ChartType.ProfitLossChart) {

                long l = Math.round(xLabels.get(index));

                if (isCustomDateFormat) {
                    localDateFormat = getDateFormat(dateFormatList.get(i));
                }

                float f1 = (float) (((i * xGridDistance) + offset) + mRenderer.getLeftMargin());

                if (f1 > width + left || f1 < left) continue;

                paint.setColor(mRenderer.getGridColor());
                paint.setStrokeWidth(mRenderer.getGridWidth());
                canvas.drawLine(f1, bottom, f1, bottom + 4, paint);

                if (isGridRightAlign) {
                    paint.setTextAlign(Align.LEFT);
                } else {
                    paint.setTextAlign(Align.RIGHT);
                }

                paint.setTextSize(22);
                paint.setColor(mRenderer.getTextColor());

                canvas.drawText(localDateFormat.format(new Date(l)), f1, bottom - paint.getFontMetricsInt().ascent + 4 + 2, paint);

            } else {

                // Profit/Loss Chart

                float f1 = (float) (((i * xGridDistance) + offset) + mRenderer.getLeftMargin());

                double val = xLabels.get(index);
                double finalVal = (min + (i * range));

                float moveTo = (float) (pointValue * (finalVal - val));
                // Comment this after checking
                f1 = f1 + moveTo;


                if (f1 > width + left || f1 < left) continue;

                paint.setColor(mRenderer.getGridColor());
                paint.setStrokeWidth(mRenderer.getGridWidth());
                canvas.drawLine(f1, bottom, f1, bottom + 4, paint);
                paint.setColor(mRenderer.getTextColor());

                if (isGridRightAlign) {
                    paint.setTextAlign(Align.LEFT);
                } else {
                    paint.setTextAlign(Align.RIGHT);
                }

                /*
                 * Open THis canvas.drawText(xLabels.get(index)+"", f1, bottom -
                 * paint.getFontMetricsInt().ascent + 4 + 2, paint);
                 */

                canvas.drawText(finalVal + "", f1, bottom - paint.getFontMetricsInt().ascent + 4 + 2, paint);

            }

        }
    }

    private DateFormat getDateFormat(String myFormat) {
        if (mRenderer.getDateFormat() != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                return format;
            } catch (Exception e) {
                // do nothing here
            }
        }
        return null;
    }

    /**
     * The graphical representation of a series.
     *
     * @param canvas     the canvas to paint
     * @param paint      the paint to be used for drawing
     * @param points     the array of points to be used for drawing the series
     * @param yAxisValue the minimum value of the y axis
     */
    public abstract void drawSeries(Canvas canvas, Paint paint, Hashtable<String, float[]> pointsHashtable, float yAxisValue);

    private void drawInline(Canvas canvas, Paint paint, Hashtable<String, float[]> pointsHashtable) {

        String dot = "dot";
        if (mRenderer.getIndicatorType() == ChartIndicatorType.INLINE) {
            Hashtable hashtable = mRenderer.getFullIndicatorData();

            if (hashtable == null) return;

            IndicatorData data;

            for (int s = 0; s < mRenderer.getIndicatorKeyValue().size(); s++) {
                if (s < INLINE_IND_COLORS.length)
                    mRenderer.setIndicatorFillColor(INLINE_IND_COLORS[s]);

                data = (IndicatorData) hashtable.get(mRenderer.getIndicatorKeyValue().get(s).split(VALUE_SEPERATOR)[0]);

                paint.setColor(mRenderer.getIndicatorFillColor());
                paint.setStrokeWidth(mRenderer.getIndicatorLineWidth());

                String inputValue = "";
                if (mRenderer.getIndicatorKeyValue().get(s).contains(VALUE_SEPERATOR)) {
                    inputValue = mRenderer.getIndicatorKeyValue().get(s).substring(mRenderer.getIndicatorKeyValue().get(s).indexOf(VALUE_SEPERATOR));
                }

                for (String outputKey : data.getOutputKey()) {
                    String lineType = data.getOutputLineType().get(data.getOutputKey().indexOf(outputKey));
                    if (pointsHashtable.containsKey(outputKey + inputValue)) {
                        float points[] = pointsHashtable.get(outputKey + inputValue);
                        if (lineType.equalsIgnoreCase(dot)) {
                            float closePoints[] = pointsHashtable.get(ChartConstants.CLOSE);
                            for (int k = 0; k < points.length; k += 2) {
                                if (closePoints[k + 1] > points[k + 1]) {
                                    paint.setColor(mRenderer.getIndicatorNegativeColor());
                                } else {
                                    paint.setColor(mRenderer.getIndicatorPositiveColor());
                                }
                                paint.setStyle(Style.FILL);
                                canvas.drawCircle(points[k], points[k + 1], convertFloatToDip(2), paint);
                            }
                        } else {
                            // Line
                            paint.setStyle(Style.STROKE);
                            drawPath(canvas, points, paint, false);
                        }
                    }
                }
            }
        }
    }

}
