package com.acumengroup.greekmain.chart.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartIndicatorType;
import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartType;
import com.acumengroup.greekmain.chart.draw.ChartConstants.XLabelDateFormat;
import com.acumengroup.greekmain.chart.AbstractChart;
import com.acumengroup.greekmain.chart.BarChart;
import com.acumengroup.greekmain.chart.CandleOHLCChart;
import com.acumengroup.greekmain.chart.LineChart;
import com.acumengroup.greekmain.chart.NotifyChartData;
import com.acumengroup.greekmain.chart.OutlineChart;
import com.acumengroup.greekmain.chart.ProfitLossChart;
import com.acumengroup.greekmain.chart.VolumeBarChart;
import com.acumengroup.greekmain.chart.dataset.BuildDataSet;
import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.dataset.XYSeries;
import com.acumengroup.greekmain.chart.scroll.ChartInterceptScrollView;
import com.acumengroup.greekmain.chart.settings.ChartSettings;
import com.acumengroup.greekmain.chart.tools.CalculatePoints;
import com.acumengroup.greekmain.chart.util.GreekChartLogger;
import com.acumengroup.greekmain.chart.util.MathHelper;
import com.acumengroup.greekmain.chart.xml.IndicatorData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * * Created by Arcadia
 */
public class DrawChart extends BuildDataSet {

    private List<double[]> streamValues = new ArrayList<double[]>();

    private List<double[]> seriesValues = new ArrayList<double[]>();
    private ArrayList<String> seriesName = new ArrayList<String>();

    private ArrayList<Date> date = new ArrayList<Date>();
    private ArrayList<Date> dateOfPriceArr = new ArrayList<Date>();

    private Context context;
    private ChartSettings chartSettings;

    private double xValues[];
    private double yValues[];

    private double openValues[];
    private double highValues[];
    private double lowValues[];
    private double closeValues[];
    private double volumeValues[];
    private String dateValues[];

    private double ratingValues[];
    private double buyOverWeightValues[];
    private double holdValues[];
    private double sellUnderWeightValues[];
    private double newBuyValues[];
    private double newHoldValues[];
    // private String dateForRatings[];
    private String dateValuesForPriceChart[];
    private double actualPriceValues[];
    private double targetPriceValues[];

    private LinearLayout chartLayout;

    private GreekChartView chartView;
    private AbstractChart mChart = null;
    private AbstractChart volumeChart;

    private DisplayMetrics displaymetrics;

    private int length;
    private float chartWidth;
    private float chartHeight;

    private long DAY = 24 * 60 * 60 * 1000;

    private ChartSettings mainChartRenderer;
    private ChartSettings volumeChartRenderer;

    private ArrayList<ChartSettings> multipleRenderer = new ArrayList<ChartSettings>();
    private ArrayList<AbstractChart> multipleCharts = new ArrayList<AbstractChart>();

    private NotifyChartData notifyChartData = new NotifyChartData();

    private Hashtable indicatorHashtable;

    private DateFormat formatter;

    private float scale;

    private String VALUE_SEPERATOR = ":";
    private String INLINE = "inline";

    private int OUTLINE_IND_COLORS[] = {0xFF00a2ff, 0xFF00b540, 0xFFffb400, 0xFFff48fd, 0xFF999999, 0xFF00c2bb, 0xFFff7538, 0xFF00cb69, 0xFF669cff, 0xFFa148ed, 0xFF81d300, 0xFF9b664b};

    private int INLINE_IND_COLORS[] = {0xFF9b664b, 0xFF81d300, 0xFFa148ed, 0xFF669cff, 0xFF00cb69, 0xFFff7538, 0xFF00c2bb, 0xFF999999, 0xFFff48fd, 0xFFffb400, 0xFF00b540, 0xFF00a2ff};

    private int indicatorIndex = 0;
    private TextView legendTextView, hintTextView;

    private GreekChartListener.onIndicatorMissingListener indicatorMissingListener;
    private GreekChartListener.onLongClickListener longClickListener;
    private GreekChartListener.onDoubleTapListener doubleTapListener;
    private GreekChartListener.onSingleTapListener singleTapListener;
    private GreekChartListener.onRemoveIndicatorListener removeIndicatorListener;
    private GreekChartListener.onNotifyListener notifyListener;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYSeries xySeries[];
    private ChartSettings streamChartSettings;
    private Hashtable<String, double[]> indicatorKeyValues;
    private String missedIndicators = null;

    public DrawChart(Context context) {
        this.context = context;
        chartLayout = new LinearLayout(context);
        chartLayout.setOrientation(LinearLayout.VERTICAL);
        GreekChartView.removeList = new ArrayList<String>();
        displaymetrics = new DisplayMetrics();
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        scale = context.getResources().getDisplayMetrics().density;
        indicatorIndex = 0;
        legendTextView = new TextView(context);
        hintTextView = new TextView(context);
    }

    /**
     * @param chartSettings ChartSettings to get chart properties
     * @param layout        layout to show chart
     * @param lineColor     line color
     */
    public void showLineChart(ChartSettings chartSettings, LinearLayout layout, int lineColor) {
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, lineColor, 0, ChartType.LineChart);
    }

    public void showProfitLossChart(ChartSettings chartSettings, LinearLayout layout, int lineColor) {
        this.chartSettings = chartSettings;

        if (!splitValuesFromResponseForProfitLossChart(layout)) return;
        handleProfitLossChartSettings(layout, lineColor, 0, ChartType.ProfitLossChart);
    }

    /**
     * @param chartSettings
     * @param layout        layout to show chart
     * @param lineColor     Set line color for area chart
     * @param areaColor     Set area color for area chart
     */
    public void showAreaChart(ChartSettings chartSettings, LinearLayout layout, int lineColor, int areaColor) {
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, lineColor, areaColor, ChartType.AreaChart);
    }

    /**
     * @param chartSettings
     * @param layout        layout to show chart
     * @param positiveColor Set positive color for OHLC
     * @param negativeColor Set negative color for OHLC
     */
    public void showOHLCChart(ChartSettings chartSettings, LinearLayout layout, int positiveColor, int negativeColor) {
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, positiveColor, negativeColor, ChartType.OHLCChart);
    }

    /**
     * @param chartSettings
     * @param layout        layout to show chart
     * @param positiveColor Set positive color for CandleStick
     * @param negativeColor Set negative color for CandleStick
     */
    public void showCandleStickChart(ChartSettings chartSettings, LinearLayout layout, int positiveColor, int negativeColor) {
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, positiveColor, negativeColor, ChartType.CandleStickChart);
    }

    public void showStreamingChart(ChartSettings chartSettings, LinearLayout layout, String names[], int colors[], int seriesLength, boolean clearValues) {

        chartLayout.removeAllViews();
        layout.removeAllViews();

        if (xySeries == null || clearValues) {
            try {
                streamChartSettings = chartSettings.deepClone();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            streamChartSettings.setChartWidth(0);
            streamChartSettings.setChartHeight(0);
        }

        streamChartSettings.setXGridCount(-1);
        streamChartSettings.setYGridCount(-1);

        if (streamChartSettings.getChartWidth() == 0) chartWidth = layout.getWidth();
        else chartWidth = streamChartSettings.getChartWidth();

        if (streamChartSettings.getChartHeight() == 0) {
            if (layout.getParent() instanceof ChartInterceptScrollView) {
                chartHeight = ((ChartInterceptScrollView) layout.getParent()).getHeight();
            } else {
                chartHeight = layout.getHeight();
            }
        } else {
            chartHeight = streamChartSettings.getChartHeight();
        }

        TextView view = new TextView(context);
        view.setPadding(5, 0, 0, 0);
        view.setTextSize(streamChartSettings.getDateFontSize());

        String value = "";
        for (int i = 0; i < names.length; i++) {
            value += "<font color=" + colors[i] + ">";
            value += names[i];
            value += "</font>";
        }
        view.setText(Html.fromHtml(value));

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(view);

        chartHeight = chartHeight - view.getLineHeight();

        streamChartSettings.setChartWidth(chartWidth);
        streamChartSettings.setChartHeight(chartHeight);

        if (xySeries == null || clearValues) {
            xySeries = new XYSeries[seriesLength];
            mDataset.removeAllSeries();
            for (int i = 0; i < seriesLength; i++) {
                xySeries[i] = new XYSeries("");
                mDataset.addSeries(xySeries[i]);
            }
        }

        streamChartSettings.setChartType(ChartType.StreamingChart);

        float scale = context.getResources().getDisplayMetrics().density;

        Paint paint = new Paint();
        paint.setTextSize(streamChartSettings.getChartTitleTextSize() * scale);

        Rect measeureHeightRect = new Rect();
        paint.getTextBounds("a", 0, 1, measeureHeightRect);

        if (clearValues) {
            streamChartSettings.setLeftMargin(Math.round(scale * streamChartSettings.getLeftMargin()));
            streamChartSettings.setTopMargin(Math.round(scale * streamChartSettings.getTopMargin()));
            streamChartSettings.setRightMargin(Math.round(paint.measureText(streamChartSettings.getTextWidthCalculateText())) + 5);
            streamChartSettings.setBottomMargin(measeureHeightRect.height() + 5);

            streamChartSettings.setIntervalBtwPoints(streamChartSettings.getIntervalBtwPoints() * scale);
        }

        streamChartSettings.setDataset(mDataset);
        streamChartSettings.setColors(colors);
        streamChartSettings.setDateFormat("kk:mm:ss");
        streamChartSettings.setMainChart(true);

        if (clearValues) {
            streamChartSettings.setVirtualValues(0);
        } else {
            if (xySeries[0].getItemCount() > 1) {
                double xVal = xySeries[0].getX().get(xySeries[0].getX().size() - 1);
                double yVal = xySeries[0].getY().get(xySeries[0].getY().size() - 1);
                xySeries[0].remove(xySeries[0].getX().size() - 1);
                addValuesForStreamingChart(String.valueOf(xVal), new double[]{yVal});
            }
        }

        LineChart lineChart = new LineChart(context, mDataset, streamChartSettings);
        mChart = lineChart;

        ArrayList<AbstractChart> abstractCharts = new ArrayList<AbstractChart>();
        abstractCharts.add(mChart);

        ArrayList<ChartSettings> settings = new ArrayList<ChartSettings>();
        settings.add(streamChartSettings);

        chartView = new GreekChartView(context, abstractCharts, settings, chartHeight, chartWidth);
        chartLayout.addView(chartView);

        layout.addView(chartLayout);

        if (!clearValues) {

            if (chartView != null) {

                if (xySeries[0].getItemCount() > 1) {
                    chartView.reDraw(streamChartSettings.getVirtualValues(), settings);
                }
            }
        }

    }

    public void addInitialValuesForStreamingChart(String xValue[], double yValues[][]) {

        if (streamChartSettings == null) return;

        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        for (int k = 0; k < xValue.length; k++) {
            Date xDate = new Date();
            try {
                xDate = formatter.parse(xValue[k]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            streamValues.clear();
            for (int j = 0; j < yValues.length; j++) {
                if (xySeries[j] == null) return;
                xySeries[j].add(xDate.getTime(), yValues[j][k]);
            }
        }

        float width = streamChartSettings.getChartWidth();
        width = (width - streamChartSettings.getRightMargin() - streamChartSettings.getLeftMargin());

        double virtual = (xValue.length - 1) * streamChartSettings.getIntervalBtwPoints();
        virtual = virtual - width;

        if (virtual < 0) {
            virtual = 0;
        }

        streamChartSettings.setVirtualValues(virtual);

        showStreamingChart();

    }

    private void showStreamingChart() {
        float width = streamChartSettings.getChartWidth();

        width = (width - streamChartSettings.getRightMargin() - streamChartSettings.getLeftMargin());

        int interval = (int) streamChartSettings.getIntervalBtwPoints();

        List<Double> xPoints = new ArrayList<Double>();
        double value = 0;
        for (int j = 0; j < xySeries[0].getItemCount(); j++) {
            xPoints.add(value);
            if (j != xySeries[0].getItemCount() - 1) value += interval;
        }

        streamChartSettings.setWidth(width);

        streamChartSettings.setMaxX(value);

        streamChartSettings.setxAxisPositions(xPoints);

        double virtual = streamChartSettings.getLeftMargin();
        virtual = value - width;

        if (virtual < 0) {
            virtual = 0;
        } else {
            virtual = streamChartSettings.getVirtualValues() + interval;

            if (virtual + width >= value) {
                virtual = (value - width);
            }

        }

        streamChartSettings.setVirtualValues(virtual);

        ArrayList<ChartSettings> settings = new ArrayList<ChartSettings>();
        settings.add(streamChartSettings);

        if (chartView != null) {

            if (xySeries[0].getItemCount() > 1) {
                chartView.reDraw(virtual, settings);
            }
        }
    }

    public void addValuesForStreamingChart(String xValue, double yValues[]) {

        if (streamChartSettings == null) return;

        formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date xDate = new Date();
        try {
            xDate = formatter.parse(xValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // If values size is greater then 850, remove the value from first
        // index.
        if (xySeries[0].getItemCount() > 850) {
            xySeries[0].remove(0);
            xySeries[1].remove(0);
        }

        streamValues.clear();

        for (int j = 0; j < yValues.length; j++) {
            if (xySeries[j] == null) return;
            xySeries[j].add(xDate.getTime(), yValues[j]);
        }

        showStreamingChart();
    }

    /**
     * @param chartSettings
     * @param layout
     * @param lineColor
     */
    public void showSparkLineChart(ChartSettings chartSettings, LinearLayout layout, int lineColor) {
        chartSettings.setPanningEnabled(false);
        chartSettings.setJoinCharts(false);
        chartSettings.setShowCrossHairs(false);
        chartSettings.setShowVolumeChart(false);
        chartSettings.setShowXLabels(false);
        chartSettings.setShowYLabels(false);
        chartSettings.setShowXGrid(false);
        chartSettings.setShowYGrid(false);
        chartSettings.setShowAxis(false);
        chartSettings.setShowDate(false);
        chartSettings.setMainChartHeightinPercentage(100);
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, lineColor, 0, ChartType.SparkLineChart);
    }

    public void showSparkLineChart(ChartSettings chartSettings, int lineColor) {
        showSparkLineChart(chartSettings, chartLayout, lineColor);
    }

    public void showSparkAreaChart(ChartSettings chartSettings, LinearLayout layout, int lineColor, int areaColor) {
        chartSettings.setPanningEnabled(false);
        chartSettings.setJoinCharts(false);
        chartSettings.setShowCrossHairs(false);
        chartSettings.setShowVolumeChart(false);
        chartSettings.setShowXLabels(false);
        chartSettings.setShowYLabels(false);
        chartSettings.setShowXGrid(false);
        chartSettings.setShowYGrid(false);
        chartSettings.setShowAxis(false);
        chartSettings.setShowDate(false);
        chartSettings.setMainChartHeightinPercentage(100);
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, lineColor, areaColor, ChartType.SparkAreaChart);
    }

    public void showSparkAreaChart(ChartSettings chartSettings, int lineColor, int areaColor) {
        showSparkAreaChart(chartSettings, chartLayout, lineColor, areaColor);
    }

    public void showSparkLineColoredChart(ChartSettings chartSettings, LinearLayout layout, int lineColor) {
        chartSettings.setPanningEnabled(false);
        chartSettings.setJoinCharts(false);
        chartSettings.setShowCrossHairs(false);
        chartSettings.setShowVolumeChart(false);
        chartSettings.setShowXLabels(false);
        chartSettings.setShowYLabels(false);
        chartSettings.setShowXGrid(false);
        chartSettings.setShowYGrid(false);
        chartSettings.setShowAxis(false);
        chartSettings.setShowDate(false);
        chartSettings.setMainChartHeightinPercentage(100);
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, lineColor, 0, ChartType.SparkLineColoredChart);
    }

    public void showSparkAreaColoredChart(ChartSettings chartSettings, LinearLayout layout, int lineColor, int areaColor) {
        chartSettings.setPanningEnabled(false);
        chartSettings.setJoinCharts(false);
        chartSettings.setShowCrossHairs(false);
        chartSettings.setShowVolumeChart(false);
        chartSettings.setShowXLabels(false);
        chartSettings.setShowYLabels(false);
        chartSettings.setShowXGrid(true);
        chartSettings.setShowYGrid(true);
        chartSettings.setShowAxis(false);
        chartSettings.setShowDate(false);
        chartSettings.setMainChartHeightinPercentage(100);
        this.chartSettings = chartSettings;
        if (!splitValuesFromResponse(layout)) return;
        handleChartSettings(layout, lineColor, areaColor, ChartType.SparkAreaColoredChart);
    }

    /**
     * @param chartSettings <br>
     *                      Set true to chartSettings.setShowCrossHairs(true) to enable
     *                      CrossHairs.<br>
     *                      Set false to chartSettings.setShowCrossHairs(false) to disable
     *                      CrossHairs.
     */
    public void showCrossHairs(ChartSettings chartSettings) {
        this.chartSettings = chartSettings;
        if (chartView != null) {
            chartView.setCrossHairs(chartSettings.isShowCrossHairs());
        }
    }

    public void showMagnify(ChartSettings chartSettings) {
        this.chartSettings = chartSettings;
        if (chartView != null) {
            chartView.setMagnify(chartSettings.isMagnify());
        }
    }

    public void showFibonacciLine(boolean showFiboLine) {
        if (chartView != null) {
            chartView.setFibonacciLine(showFiboLine);
        }
    }

    /**
     * @param chartSettings <br>
     *                      Set true to chartSettings.setShowCrossHairs(true) to enable
     *                      CrossHairs.<br>
     *                      Set false to chartSettings.setShowCrossHairs(false) to disable
     *                      CrossHairs.
     */
    public void showTrendLine(boolean showTrendLine) {
        this.chartSettings.setTrendline(showTrendLine);
        if (chartView != null) {
            chartView.setTrendLine(showTrendLine);
        }
    }

    public void setMagnifyMovable(boolean magnifyMovable) {
        this.chartSettings.setMagnifyMovable(magnifyMovable);
        if (chartView != null) {
            chartView.setMagnifyMovable(magnifyMovable);
        }
    }

    public boolean getTrendLine() {
        return chartView != null && chartView.getTrendLine();
    }

    public boolean getDeleteTrendLine() {
        return chartView != null && chartView.getDeleteTrendLine();
    }

    public void deleteTrendLine(boolean deleteTrendLine) {
        if (deleteTrendLine) {
            this.chartSettings.setTrendline(deleteTrendLine);
            this.chartSettings.setDeleteTrendline(deleteTrendLine);
            if (chartView != null) {
                chartView.deleteTrendLine(deleteTrendLine);
            }
        }
    }

    public boolean getFiboLine() {
        return chartView != null && chartView.getFibonacciLine();

    }

    public boolean getDeleteFiboLine() {
        return chartView != null && chartView.getDeleteFibonacciLine();
    }

    public void deleteFiboLine(boolean deleteFiboLine) {
        if (deleteFiboLine) {
            this.chartSettings.setFiboLine(deleteFiboLine);
            this.chartSettings.setDeleteFiboLine(deleteFiboLine);
            if (chartView != null) {
                chartView.deleteFibonacciLine(deleteFiboLine);
            }
        }
    }

    /**
     * @param indicatorNameAndValue <br>
     *                              <br>
     *                              indicatorNameAndValue Ex:- <br>
     *                              PSAR <br>
     *                              SMA:7 <br>
     *                              MACD:25:10:7 <br>
     */
    private boolean addIndicator(String indicatorNameAndValue) {

        if (indicatorKeyValues == null || indicatorKeyValues.size() == 0 || indicatorHashtable == null) {
            GreekChartLogger.logError("Indicators not available || Indicator Hashtable null || Values are not splitted.");

            return false;
        }

        indicatorNameAndValue = indicatorNameAndValue.replaceAll(",", VALUE_SEPERATOR);

        // Split the Indicator Name & Value from indicatorNameAndValue
        String indicatorName = indicatorNameAndValue.split(VALUE_SEPERATOR)[0];
        String indicatorValue = "";

        if (indicatorNameAndValue.contains(VALUE_SEPERATOR)) {
            /** Example:- SMA:7 , MACD:25:10:7 */

            // indicatorValue - 7 , 25:10:7
            indicatorValue = indicatorNameAndValue.substring(indicatorNameAndValue.indexOf(VALUE_SEPERATOR) + 1);
        }

        GreekChartLogger.logMsg("Indicator Name=" + indicatorName);
        GreekChartLogger.logMsg("Indicator Input Value=" + indicatorValue);

        // Get the indicator details using the raw indicator.xml response
        IndicatorData indicatorData = getIndicatorDetails(indicatorHashtable, indicatorName);

        if (indicatorData == null) {
            // MSFChartLogger.logError("Indicator data not available.");
            return false;
        }

        // Check whether the indicator is inline or outline.
        boolean isInlineIndicator = isInlineIndicator(indicatorData);

        if (isInlineIndicator) {
            // Inline indicator
            createInlineIndicator(indicatorData, indicatorNameAndValue);
        } else {
            // Outline indicator
            createOutlineIndicator(indicatorData, indicatorNameAndValue);
        }

        return true;

    }

    private void createInlineIndicator(IndicatorData indicatorData, String indicatorNameAndValue) {
        if (mChart != null) {
            mainChartRenderer.setIndicatorType(ChartIndicatorType.INLINE);

            String indicatorValue = "";

            if (indicatorNameAndValue.contains(VALUE_SEPERATOR)) {
                indicatorValue = indicatorNameAndValue.substring(indicatorNameAndValue.indexOf(VALUE_SEPERATOR));
            }

            String label = "<font color=" + INLINE_IND_COLORS[indicatorIndex < INLINE_IND_COLORS.length ? indicatorIndex : 0] + ">";
            label += indicatorNameAndValue.split(VALUE_SEPERATOR)[0] + ((indicatorValue.length() > 0) ? "(" + indicatorValue.substring(1).replace(VALUE_SEPERATOR, ",") + ")" : indicatorValue);
            label += "</font>";

            indicatorIndex++;

            legendTextView.append(Html.fromHtml("&nbsp;&nbsp;" + label));

            if (chartSettings.getTypeface() != null)
                legendTextView.setTypeface(chartSettings.getTypeface());

            mainChartRenderer.setIndicatorKeyValue(indicatorNameAndValue);

            for (String output : indicatorData.getOutputKey()) {
                String outputKeyValue = output + indicatorValue;

                boolean isAddIndicatorSuccess = indicatorKeyValues.containsKey(outputKeyValue);
                if (!isAddIndicatorSuccess) {
                    if (missedIndicators != null) {
                        missedIndicators += ", " + indicatorData.getName() + VALUE_SEPERATOR + indicatorValue;
                    } else {
                        missedIndicators = indicatorData.getName() + VALUE_SEPERATOR + indicatorValue;
                    }
                    break;
                }

                if (isAddIndicatorSuccess) {
                    chartView.addInlineindicator(date, indicatorKeyValues.get(outputKeyValue), outputKeyValue);
                }
            }
        }

    }

    /**
     * Split the values from response and save it in array list.
     *
     * @param layout When we parse the response, there may a chance to get error.<br>
     *               To show that error message we need a layout.
     * @return
     */
    private boolean splitValuesFromResponse(LinearLayout layout) {

        // If open values is not null, then the values are already split. So no
        // need to split again, return it.
        if (openValues != null) return true;

        Hashtable chartData = chartSettings.getChartData();

        // Here 6 is - In hashtable we will get minimum 6 values { Open, High,
        // Low, Close, Volume, Date } other values are indicator values. So
        // chartData.Size - 6 = Indicator Size.

        // Check Whether Indicator value is present or not. If yes, Parse the
        // xml file to get the xml types,etc...
        if(chartData !=null) {
            if (chartData.size() > 6) {
                if (chartSettings.getFullIndicatorData() == null) {
                    if (chartSettings.getIndicatorXMLResponse() != null) {
                        chartSettings.parseXMLContent(chartSettings.getIndicatorXMLResponse());
                        indicatorHashtable = chartSettings.getFullIndicatorData();
                    }
                    if (chartSettings.getIndicatorJSONResponse() != null) {
                        chartSettings.parseIndicatorJSONContent(chartSettings.getIndicatorJSONResponse());
                        indicatorHashtable = chartSettings.getFullIndicatorData();
                    }
                } else {
                    indicatorHashtable = chartSettings.getFullIndicatorData();
                }
            }

            indicatorKeyValues = new Hashtable<String, double[]>();

            String key;
            Enumeration enumeration = chartData.keys();
            while (enumeration.hasMoreElements()) {
                key = (String) enumeration.nextElement();

                if (key.equalsIgnoreCase(ChartConstants.OPEN)) {
                    openValues = (double[]) chartData.get(key);
                } else if (key.equalsIgnoreCase(ChartConstants.HIGH)) {
                    highValues = (double[]) chartData.get(key);
                } else if (key.equalsIgnoreCase(ChartConstants.LOW)) {
                    lowValues = (double[]) chartData.get(key);
                } else if (key.equalsIgnoreCase(ChartConstants.CLOSE)) {
                    closeValues = (double[]) chartData.get(key);
                } else if (key.equalsIgnoreCase(ChartConstants.VOLUME)) {
                    volumeValues = (double[]) chartData.get(key);

                    // Some indicators need volume (Ex:- VSMA, VEMA,....)
                    double minMax[] = MathHelper.getMinMaxValue(volumeValues);
                    if (minMax[0] != 0 || minMax[1] != 0) {
                        indicatorKeyValues.put(key, volumeValues);
                    } /*
                     * else { chartSettings.setShowVolumeChart(false); }
                     */
                } else if (key.equalsIgnoreCase(ChartConstants.DATE)) {
                    dateValues = (String[]) chartData.get(key);
                } else {
                    // indicators
                    double minMax[] = MathHelper.getMinMaxValue((double[]) chartData.get(key));
                    if (minMax[0] != 0 || minMax[1] != 0)
                        indicatorKeyValues.put(key, (double[]) chartData.get(key));
                }
            }

            length = openValues.length;

            formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            date.clear();
            for (int f = 0; f < length; f++) {
                try {
                    // For Response 342 alone. B'coz response 342 won't contains
                    // time.
                    // So add time to maintain same date format in all chart
                    // response.
                    dateValues[f] = dateValues[f].contains(" ") ? dateValues[f] : dateValues[f] + " 00:00:00";
                    date.add(formatter.parse(dateValues[f]));
                } catch (ParseException e) {
                    try {
                        // For Response 342 alone. B'coz response 342 won't contains
                        // time.
                        date.add(formatter.parse(dateValues[f] + " 00:00:00"));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return true;
        }
        return false;
    }

    private boolean splitValuesFromResponseForProfitLossChart(LinearLayout layout) {

        // If open values is not null, then the values are already split. So no
        // need to split again, return it.
        // if (openValues != null)
        // return true;

        Hashtable chartData = chartSettings.getChartData();

        String key;
        Enumeration enumeration = chartData.keys();
        while (enumeration.hasMoreElements()) {
            key = (String) enumeration.nextElement();

            if (key.equalsIgnoreCase(ChartConstants.PL_CHART_STOCK)) {
                String stockValues[] = (String[]) chartData.get(key);

                xValues = new double[stockValues.length];
                for (int i = 0; i < stockValues.length; i++) {
                    xValues[i] = Double.parseDouble(stockValues[i]);
                }

            } else if (key.equalsIgnoreCase(ChartConstants.PL_CHART_PLO)) {
                String ploValues[] = (String[]) chartData.get(key);

                yValues = new double[ploValues.length];
                for (int i = 0; i < ploValues.length; i++) {
                    yValues[i] = Double.parseDouble(ploValues[i]);
                }

            }

        }

        return true;
    }

    /**
     * @param layout Show Error message when values are invalid.
     */
    private void showErrorMessage(LinearLayout layout, String errorMessage) {
        layout.removeAllViews();
        TextView textView = new TextView(context);
        textView.setPadding(5, 5, 5, 5);
        textView.setText(errorMessage);
        layout.addView(textView);
        openValues = null;
    }

    private IndicatorData getIndicatorDetails(Hashtable indicatorResponse, String indicatorName) {
        if (indicatorResponse.containsKey(indicatorName)) {
            return (IndicatorData) indicatorResponse.get(indicatorName);
        } else {
            GreekChartLogger.logError(indicatorName + " Indicator Not Exists.");
            return null;
        }
    }

    private boolean isInlineIndicator(IndicatorData indicatorData) {

        return indicatorData.getOutputPlotType().get(0).toString().equalsIgnoreCase(INLINE);

    }

    private void handleProfitLossChartSettings(LinearLayout layout, int Color1, int Color2, ChartType type) {

        chartLayout.removeAllViews();

        layout.removeAllViews();

        legendTextView.setText("");

        if (chartSettings.getChartWidth() == 0) chartWidth = layout.getWidth();
        else chartWidth = chartSettings.getChartWidth() * scale;

        if (chartSettings.getChartHeight() == 0) {
            if (layout.getParent() instanceof ChartInterceptScrollView) {
                chartHeight = ((ChartInterceptScrollView) layout.getParent()).getHeight();
            } else {
                chartHeight = layout.getHeight();
            }
        } else {
            chartHeight = chartSettings.getChartHeight() * scale;
        }

        GreekChartLogger.logMsg("Chart Height = " + chartHeight);
        GreekChartLogger.logMsg("Chart Width = " + chartWidth);

        if (chartHeight < 1 || chartWidth < 1) {
            GreekChartLogger.logError("Chart Width or Height is lesser then 1. Can't proceed further.");
            return;
        }

//		Make Y Axis Grid in Left Side
        chartSettings.setYAxisRightAlign(false);

        // Make Y Axis Grid in Left Side
        chartSettings.setYAxisRightAlign(false);

        // We will assign all the values that need to draw y axis. Using this
        // values array we need to calculate the min & max value.
        // yValue[][] min size is 1 (for Line & Area Chart) or 2 (for OHLC &
        // Candle Stick). B'coz default this multidimensional array
        // will have close values (for Line & Area Chart) or high & low array
        // values(for OHLC & Candle Stick).
        // YValue size will increase when inline indicator response (Response
        // 814) available.

        // Check Chart Type and set the yValue array size. 1 for Line & Area
        // Chart or 2 for OHLC & Candle Stick.
        // If 1 - then its Close Value.
        // If 2 - then its High & low Value.
        // Plus we need to add only inline indicator values.
        // B'coz inline indicator values may higher or lower then our High or
        // low or close Values.
        // Note : We should not add outline indicator values in these. B'coz we
        // will draw outline chart as a separate chart like volume chart.

        seriesValues.clear();
        seriesName.clear();

        // For Line, Area and Spark Line Chart close value is enough to draw
        // chart.
        if (type == ChartType.ProfitLossChart) {
            seriesValues.add(yValues);
            seriesName.add(ChartConstants.PL_CHART_PLO);
        }

        // Set the chartSettings values
        try {
            mainChartRenderer = chartSettings.deepClone();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (chartSettings.getTypeface() != null)
            mainChartRenderer.setTypeface(chartSettings.getTypeface());

        mainChartRenderer.setYAxisValue(seriesValues);

        // Volume chart not applicable.
        mainChartRenderer.setShowVolumeChart(false);

        mainChartRenderer.setChartType(type);

        mainChartRenderer.setChartWidth(chartWidth);

        mainChartRenderer.setIntervalBtwPoints(mainChartRenderer.getIntervalBtwPoints() * scale);

        mainChartRenderer.setVirtualValues(0);


        Paint paint = new Paint();
        paint.setTextSize(mainChartRenderer.getChartTitleTextSize() * scale);


        if (mainChartRenderer.isShowYLabels()) {
            // Y Axis Labels Visible
            mainChartRenderer.setLeftMargin(Math.round(paint.measureText(mainChartRenderer.getTextWidthCalculateText())) + Math.round(scale * mainChartRenderer.getLeftMargin()));
        }

        Rect measeureHeightRect = new Rect();
        paint.getTextBounds("a", 0, 1, measeureHeightRect);
        mainChartRenderer.setBottomMargin(measeureHeightRect.height() + Math.round(scale * mainChartRenderer.getBottomMargin()));

        // mainChartRenderer.setxLabelValue(date);
        mainChartRenderer.setxLabelSeries(xValues);

        // Get the exact chart drawing width
        float width = (mainChartRenderer.getChartWidth() - mainChartRenderer.getRightMargin() - mainChartRenderer.getLeftMargin());
        mainChartRenderer.setWidth(width);

        // For OHLC & Candle Stick chart, we need to give 5px margin at left &
        // right side inside the chart.(i.e padding)
        if (mainChartRenderer.getChartType() != ChartType.OHLCChart && mainChartRenderer.getChartType() != ChartType.CandleStickChart) {
            mainChartRenderer.setEndPointSpace(0);
        }

        // Interval between 2 points.
        float interval = mainChartRenderer.getIntervalBtwPoints();

        if (chartSettings.isPanningEnabled()) {
            // If panning is enabled, use default interval.

            // If plotting width is greater then points calculated width,
            // calculate interval as width/point size.
            if (((xValues.length - 1) * interval) < width) {
                interval = ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (xValues.length - 1));
            }
        } else {
            // If panning is disabled, we need to show all the values in screen
            // at
            // a time. So calculate interval as width/point size.
            interval = ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (xValues.length - 1));
        }

        mainChartRenderer.setIntervalBtwPoints(interval);
        mainChartRenderer.setBaseIntervalBtwPoints(interval);

        GreekChartLogger.logMsg("Interval Btw 2 Points = " + interval);

        // Calculate X value for all points
        if (mainChartRenderer.isDefaultZoomIn()) {
            new CalculatePoints().calculateXPointsForProfitLossChart(mainChartRenderer, interval);
        } else {
            new CalculatePoints().calculateXPointsForProfitLossChart(mainChartRenderer, ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (xValues.length - 1)));
        }

        // if (chartSettings.isVirtualValueFirstTime) {

        double lastPoint = mainChartRenderer.getMaxX();


//		mainChartRenderer.setTextRightAlign(false);

        // Virtual value is used to get the starting point of the x label in
        // screen.
        double virtual = 0;

        if (width < lastPoint && mainChartRenderer.isYAxisRightAlign()) {
            virtual = (lastPoint - width);
        }

        mainChartRenderer.setVirtualValues(virtual);

        mainChartRenderer.setMainChart(true);

        mainChartRenderer.setyLabelValue(yValues);

        if (type == ChartType.LineChart || type == ChartType.AreaChart || type == ChartType.SparkLineChart || type == ChartType.SparkAreaChart || type == ChartType.SparkLineColoredChart || type == ChartType.SparkAreaColoredChart) {
            mainChartRenderer.setLineColor(Color1);
            if (type == ChartType.AreaChart || type == ChartType.SparkAreaChart || type == ChartType.SparkAreaColoredChart) {
                mainChartRenderer.setFillBelowLine(true);
                mainChartRenderer.setFillBelowLineColor(Color2);
            }
        } else if (type == ChartType.OHLCChart || type == ChartType.CandleStickChart) {
            mainChartRenderer.setCandlePositiveColor(Color1);
            mainChartRenderer.setCandleNegativeColor(Color2);
        } else if (type == ChartType.ProfitLossChart) {
            mainChartRenderer.setLineColor(Color1);
        }

        mainChartRenderer.setApplyBackgroundColor(true);

        XYMultipleSeriesDataset seriesDataset = buildDataset(xValues, seriesValues, seriesName);
        mainChartRenderer.setDataset(seriesDataset);

        if (type == ChartType.LineChart || type == ChartType.AreaChart || type == ChartType.SparkLineChart || type == ChartType.SparkAreaChart || type == ChartType.SparkLineColoredChart || type == ChartType.SparkAreaColoredChart) {
            LineChart lineChart = new LineChart(context, seriesDataset, mainChartRenderer);
            mChart = lineChart;
        } else if (type == ChartType.CandleStickChart || type == ChartType.OHLCChart) {
            CandleOHLCChart candleOHLCChart = new CandleOHLCChart(context, seriesDataset, mainChartRenderer);
            mChart = candleOHLCChart;
        } else if (type == ChartType.ProfitLossChart) {
            ProfitLossChart profitLossChart = new ProfitLossChart(context, seriesDataset, mainChartRenderer);
            mChart = profitLossChart;
        }

        // Date not applicable
        chartSettings.setShowDate(false);

        if (chartSettings.isShowDate()) {
            legendTextView.setTextColor(chartSettings.getDateTextColor());

            String indName = mainChartRenderer.getIntraDate();
            indName += " " + mainChartRenderer.getLegend() + " ";

            if (indName.endsWith(", ")) indName = indName.substring(0, indName.length() - 2);
            legendTextView.setText(Html.fromHtml(indName));
            legendTextView.setPadding(15, 0, 0, 0);
            legendTextView.setTextSize(mainChartRenderer.getDateFontSize());

            if (chartSettings.getTypeface() != null)
                legendTextView.setTypeface(chartSettings.getTypeface());

            chartLayout.addView(legendTextView);

            chartHeight = chartHeight - legendTextView.getLineHeight();
        }

        mainChartRenderer.setChartHeight(chartHeight);

        if (chartSettings.isJoinCharts()) {
            if (mainChartRenderer.isShowVolumeChart()) {
                mainChartRenderer.setShowXLabels(false);
            }
        }

        if (mainChartRenderer.isShowVolumeChart()) {
            drawVolumeChart();
        }

        multipleRenderer.clear();
        multipleCharts.clear();

        multipleRenderer.add(mainChartRenderer);
        multipleCharts.add(mChart);
        if (mainChartRenderer.isShowVolumeChart()) {
            multipleRenderer.add(volumeChartRenderer);
            multipleCharts.add(volumeChart);
        }

        chartView = new GreekChartView(context, multipleCharts, multipleRenderer, chartHeight, chartWidth);
        chartView.setOnLongPressListener(longClickListener);
        chartView.setOnDoubleTapListener(doubleTapListener);
        chartView.setOnSingleTapListener(singleTapListener);
        chartView.setOnRemoveIndicatorListener(removeIndicatorListener);
        chartView.setOnNotifyListener(notifyListener);
        chartLayout.addView(chartView);

        if (chartLayout.getId() != layout.getId()) {
            layout.addView(chartLayout);
        }

		/*
		 * // Add Indicators for (String indicator :
		 * chartSettings.getIndicatorNameAndValues()) { addIndicator(indicator);
		 * }
		 */

        missedIndicators = null;

        // Add Indicators
        for (String indicator : chartSettings.getIndicatorNameAndValues()) {
            boolean isAddIndicatorSuccess = addIndicator(indicator);
            if (!isAddIndicatorSuccess) {
                if (missedIndicators != null) {
                    missedIndicators += ", " + indicator;
                } else {
                    missedIndicators = indicator;
                }
            }
        }

        if (missedIndicators != null && indicatorMissingListener != null) {
            indicatorMissingListener.onIndicatorMissing(missedIndicators);
        }

    }

    private void handleChartSettings(LinearLayout layout, int Color1, int Color2, ChartType type) {

        chartLayout.removeAllViews();

        // For ICICI widget
        if (chartLayout.getId() == layout.getId()) layout = chartLayout;

        layout.removeAllViews();

        legendTextView.setText("");

        if (chartSettings.getChartWidth() == 0) chartWidth = layout.getWidth();
        else chartWidth = chartSettings.getChartWidth() * scale;

        if (chartSettings.getChartHeight() == 0) {
            if (layout.getParent() instanceof ChartInterceptScrollView) {
                chartHeight = ((ChartInterceptScrollView) layout.getParent()).getHeight();
            } else {
                chartHeight = layout.getHeight();
            }
        } else {
            chartHeight = chartSettings.getChartHeight() * scale;
        }

        GreekChartLogger.logMsg("Chart Height = " + chartHeight);
        GreekChartLogger.logMsg("Chart Width = " + chartWidth);

        if (chartHeight < 1 || chartWidth < 1) {
            GreekChartLogger.logError("Chart Width or Height is lesser then 1. Can't proceed further.");
            return;
        }

        // We will assign all the values that need to draw y axis. Using this
        // values array we need to calculate the min & max value.
        // yValue[][] min size is 1 (for Line & Area Chart) or 2 (for OHLC &
        // Candle Stick). B'coz default this multidimensional array
        // will have close values (for Line & Area Chart) or high & low array
        // values(for OHLC & Candle Stick).
        // YValue size will increase when inline indicator response (Response
        // 814) available.

        // Check Chart Type and set the yValue array size. 1 for Line & Area
        // Chart or 2 for OHLC & Candle Stick.
        // If 1 - then its Close Value.
        // If 2 - then its High & low Value.
        // Plus we need to add only inline indicator values.
        // B'coz inline indicator values may higher or lower then our High or
        // low or close Values.
        // Note : We should not add outline indicator values in these. B'coz we
        // will draw outline chart as a separate chart like volume chart.

        seriesValues.clear();
        seriesName.clear();

        // For Line, Area and Spark Line Chart close value is enough to draw
        // chart.
        if (type == ChartType.AreaChart || type == ChartType.LineChart || type == ChartType.SparkLineChart || type == ChartType.SparkAreaChart || type == ChartType.SparkLineColoredChart || type == ChartType.SparkAreaColoredChart) {
            seriesValues.add(closeValues);
            seriesName.add(ChartConstants.CLOSE);
        } else if (type == ChartType.OHLCChart || type == ChartType.CandleStickChart) {
            // For OHLC & CandleStick we need open, close, high & low values to
            // draw chart.
            seriesValues.add(openValues);
            seriesValues.add(highValues);
            seriesValues.add(lowValues);
            seriesValues.add(closeValues);
            seriesName.add(ChartConstants.OPEN);
            seriesName.add(ChartConstants.HIGH);
            seriesName.add(ChartConstants.LOW);
            seriesName.add(ChartConstants.CLOSE);
        }

        // Set the chartSettings values
        try {
            mainChartRenderer = chartSettings.deepClone();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (chartSettings.getTypeface() != null)
            mainChartRenderer.setTypeface(chartSettings.getTypeface());

        // Check volume min and max value. If both are Zero hide the volume
        // chart.
        double minMax[] = MathHelper.getMinMaxValue(volumeValues);
        if (minMax[0] == 0 && minMax[1] == 0) {
            mainChartRenderer.setShowVolumeChart(false);
        }

        mainChartRenderer.setChartType(type);

        mainChartRenderer.setChartWidth(chartWidth);

        mainChartRenderer.setIntervalBtwPoints(mainChartRenderer.getIntervalBtwPoints() * scale);
        mainChartRenderer.setVirtualValues(0);
        // notifyChartData.setVirtualValues(0);
        // notifyListener.onNotifyData(notifyChartData);

        Paint paint = new Paint();
        paint.setTextSize(mainChartRenderer.getChartTitleTextSize() * scale);

        if (type != ChartType.SparkLineChart && type != ChartType.SparkAreaChart && type != ChartType.SparkLineColoredChart && type != ChartType.SparkAreaColoredChart) {
            // Calculate Left, Right, Top & Bottom Margin for charts
            if (mainChartRenderer.isShowYLabels() && (!mainChartRenderer.isYAxisRightAlign())) {
                mainChartRenderer.setLeftMargin(Math.round(paint.measureText(mainChartRenderer.getTextWidthCalculateText())) + Math.round(scale * mainChartRenderer.getLeftMargin()));
            } else {
                mainChartRenderer.setLeftMargin(Math.round(scale * mainChartRenderer.getLeftMargin()));
            }

            mainChartRenderer.setTopMargin(Math.round(scale * mainChartRenderer.getTopMargin()));

            if (mainChartRenderer.isShowYLabels() && mainChartRenderer.isYAxisRightAlign()) {
                // Y Axis Labels Visible
                mainChartRenderer.setRightMargin(Math.round(paint.measureText(mainChartRenderer.getTextWidthCalculateText())) + Math.round(scale * mainChartRenderer.getRightMargin()));
            } else {
                // Y Axis Labels Not Visible
                mainChartRenderer.setRightMargin(Math.round(scale * mainChartRenderer.getRightMargin()));
            }

        } else {
            mainChartRenderer.setLeftMargin(0);
            mainChartRenderer.setTopMargin(0);
            mainChartRenderer.setRightMargin(0);
        }

        Rect measeureHeightRect = new Rect();
        paint.getTextBounds("a", 0, 1, measeureHeightRect);
        if (type != ChartType.SparkLineChart && type != ChartType.SparkAreaChart && type != ChartType.SparkLineColoredChart && type != ChartType.SparkAreaColoredChart) {

            mainChartRenderer.setBottomMargin(measeureHeightRect.height() + Math.round(scale * mainChartRenderer.getBottomMargin()));
        } else mainChartRenderer.setBottomMargin(0);

        mainChartRenderer.setxLabelValue(date);

        // Get the exact chart drawing width
        float width = (mainChartRenderer.getChartWidth() - mainChartRenderer.getRightMargin() - mainChartRenderer.getLeftMargin());
        mainChartRenderer.setWidth(width);

        // For OHLC & Candle Stick chart, we need to give 5px margin at left &
        // right side inside the chart.
        if (mainChartRenderer.getChartType() != ChartType.OHLCChart && mainChartRenderer.getChartType() != ChartType.CandleStickChart) {
            mainChartRenderer.setEndPointSpace(0);
        }

        // Interval between 2 points.
        float interval = mainChartRenderer.getIntervalBtwPoints();

        if (chartSettings.isPanningEnabled()) {
            // If panning is enabled, use default interval.

            // If plotting width is greater then points calculated width,
            // calculate interval as width/point size.
            if (((date.size() - 1) * interval) < width) {
                interval = ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (date.size() - 1));
            }
        } else {
            // If panning is disabled, we need to show all the values in screen
            // at
            // a time. So calculate interval as width/point size.
            interval = ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (date.size() - 1));
        }

        mainChartRenderer.setIntervalBtwPoints(interval);
        mainChartRenderer.setBaseIntervalBtwPoints(interval);

        GreekChartLogger.logMsg("Interval Btw 2 Points = " + interval);

        // Calculate X value for all points
        if (mainChartRenderer.isDefaultZoomIn()) {
            new CalculatePoints().calculateXPoints(mainChartRenderer, interval);
        } else {
            new CalculatePoints().calculateXPoints(mainChartRenderer, ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (date.size() - 1)));
        }

        // if (chartSettings.isVirtualValueFirstTime) {

        double lastPoint = mainChartRenderer.getMaxX();

        // Virtual value is used to get the starting point of the x label in
        // screen.
		/*double virtual = (lastPoint - width);

		double virtual = 0;

		if (width < lastPoint && mainChartRenderer.isYAxisRightAlign()) {
			virtual = (lastPoint - width);
		}

		mainChartRenderer.setVirtualValues(virtual);*/

        double virtual = 0;

        if (width < lastPoint && mainChartRenderer.isYAxisRightAlign()) {
            virtual = (lastPoint - width);
        }

        mainChartRenderer.setVirtualValues(virtual);

        mainChartRenderer.setMainChart(true);

        mainChartRenderer.setyLabelValue(closeValues);

        // Set date in top left of the chart
        String startDate = (String) android.text.format.DateFormat.format("MMM dd,yyyy", date.get(0));
        String endDate = (String) android.text.format.DateFormat.format("MMM dd,yyyy", date.get(date.size() - 1));

        try {
            if (startDate.equalsIgnoreCase(endDate)) {
                // Start & End point date are same, So show the date format as
                // Hour:Minutes
                mainChartRenderer.setIntraDate(startDate);
                mainChartRenderer.setDateFormat("HH:mm");
            } else {
                // Start & End point date are different
                mainChartRenderer.setIntraDate(startDate + " - " + endDate);

                // If Start & End point Date are in between 3 days, then set
                // the date format as Hour:Minutes else Month/day 787889
                if (date.get(0).getTime() + (DAY * 3) < date.get(date.size() - 1).getTime()) {
                    mainChartRenderer.setDateFormat("MM/dd");
                } else {
                    mainChartRenderer.setDateFormat("HH:mm");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        chartSettings.setIntraDate(mainChartRenderer.getIntraDate());

        if (mainChartRenderer.getxLabelDateFormat() != XLabelDateFormat.DEFAULT) {
            String dateFormat = mainChartRenderer.getDateFormat();

            switch (mainChartRenderer.getxLabelDateFormat()) {
                case HOUR_MINUTES_HHmm:
                    dateFormat = "HH:mm";
                    break;
                case MONTH_DAY_MMdd:
                    dateFormat = "MM/dd";
                    break;
                case DAY_MONTH_ddMM:
                    dateFormat = "dd/MMM";
                    break;
                case DAY_MONTH_YEAR_ddMMyyyy:
                    dateFormat = "dd/MM/yyyy";
                    break;
                case MONTH_DAY_YEAR_MMddyyyy:
                    dateFormat = "MM/dd/yyyy";
                    break;
                case MINUTES_SECONDS_mmss:
                    dateFormat = "mm:ss";
                    break;
                case HOUR_MINUTES_SECONDS_HHmmss:
                    dateFormat = "kk:mm:ss";
                    break;
                case MONTH_YEAR_MMyyyy:
                    dateFormat = "MM/yyyy";
                    break;
            }
            mainChartRenderer.setDateFormat(dateFormat);
        }

        GreekChartLogger.logMsg("Date Format = " + mainChartRenderer.getDateFormat());

        mainChartRenderer.setYAxisValue(seriesValues);

        if (type == ChartType.LineChart || type == ChartType.AreaChart || type == ChartType.SparkLineChart || type == ChartType.SparkAreaChart || type == ChartType.SparkLineColoredChart || type == ChartType.SparkAreaColoredChart) {
            mainChartRenderer.setLineColor(Color1);
            if (type == ChartType.AreaChart || type == ChartType.SparkAreaChart || type == ChartType.SparkAreaColoredChart) {
                mainChartRenderer.setFillBelowLine(true);
                mainChartRenderer.setFillBelowLineColor(Color2);
            }
        } else if (type == ChartType.OHLCChart || type == ChartType.CandleStickChart) {
            mainChartRenderer.setCandlePositiveColor(Color1);
            mainChartRenderer.setCandleNegativeColor(Color2);
        }
        mainChartRenderer.setApplyBackgroundColor(true);

        mainChartRenderer.setVolumeValues(volumeValues);

        XYMultipleSeriesDataset seriesDataset = buildDateDataset(date, seriesValues, seriesName);
        mainChartRenderer.setDataset(seriesDataset);

        LineChart lineChart = null;
        CandleOHLCChart candleOHLCChart = null;
        if (type == ChartType.LineChart || type == ChartType.AreaChart || type == ChartType.SparkLineChart || type == ChartType.SparkAreaChart || type == ChartType.SparkLineColoredChart || type == ChartType.SparkAreaColoredChart) {
            lineChart = new LineChart(context, seriesDataset, mainChartRenderer);
            mChart = lineChart;
        } else if (type == ChartType.CandleStickChart || type == ChartType.OHLCChart) {
            candleOHLCChart = new CandleOHLCChart(context, seriesDataset, mainChartRenderer);
            mChart = candleOHLCChart;
        }

        if (chartSettings.isShowDate()) {
            legendTextView.setTextColor(chartSettings.getDateTextColor());

            String indName = mainChartRenderer.getIntraDate();
            indName += " " + mainChartRenderer.getLegend() + " ";

            if (indName.endsWith(", ")) indName = indName.substring(0, indName.length() - 2);
            legendTextView.setText(Html.fromHtml(indName));
            legendTextView.setPadding(15, 0, 0, 0);
            legendTextView.setTextSize(mainChartRenderer.getDateFontSize());

            if (chartSettings.getTypeface() != null)
                legendTextView.setTypeface(chartSettings.getTypeface());

            chartLayout.addView(legendTextView);

            chartHeight = chartHeight - legendTextView.getLineHeight();
        }

        mainChartRenderer.setChartHeight(chartHeight);

        if (chartSettings.isJoinCharts()) {
            if (mainChartRenderer.isShowVolumeChart()) {
                mainChartRenderer.setShowXLabels(false);
            }
        }

        if (mainChartRenderer.isShowVolumeChart()) {
            drawVolumeChart();
        }

        multipleRenderer.clear();
        multipleCharts.clear();

        multipleRenderer.add(mainChartRenderer);
        multipleCharts.add(mChart);
        if (mainChartRenderer.isShowVolumeChart()) {
            multipleRenderer.add(volumeChartRenderer);
            multipleCharts.add(volumeChart);
        }

        chartView = new GreekChartView(context, multipleCharts, multipleRenderer, chartHeight, chartWidth);
        chartView.setOnLongPressListener(longClickListener);
        chartView.setOnDoubleTapListener(doubleTapListener);
        chartView.setOnSingleTapListener(singleTapListener);
        chartView.setOnRemoveIndicatorListener(removeIndicatorListener);
        chartView.setOnNotifyListener(notifyListener);
        chartLayout.addView(chartView);

        if (chartLayout.getId() != layout.getId()) layout.addView(chartLayout);

		/*
		 * // Add Indicators for (String indicator :
		 * chartSettings.getIndicatorNameAndValues()) { addIndicator(indicator);
		 * }
		 */

        missedIndicators = null;

        indicatorIndex = 0;

        // Add Indicators
        for (String indicator : chartSettings.getIndicatorNameAndValues()) {
            boolean isAddIndicatorSuccess = addIndicator(indicator);
            if (!isAddIndicatorSuccess) {
                if (missedIndicators != null) {
                    missedIndicators += ", " + indicator;
                } else {
                    missedIndicators = indicator;
                }
            }
        }

        if (missedIndicators != null && indicatorMissingListener != null) {
            indicatorMissingListener.onIndicatorMissing(missedIndicators);
        }

    }

    public void setOnIndicatorMissingListener(GreekChartListener.onIndicatorMissingListener indicatorMissingListener) {
        this.indicatorMissingListener = indicatorMissingListener;
    }

    /**
     * @param longClickListener
     */
    public void setOnLongClickListener(GreekChartListener.onLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
        if (chartView != null) chartView.setOnLongPressListener(longClickListener);
    }

    public void setOnDoubleTapListener(GreekChartListener.onDoubleTapListener doubleTapListener) {
        this.doubleTapListener = doubleTapListener;
        if (chartView != null) chartView.setOnDoubleTapListener(doubleTapListener);
    }

    public void setOnSingleTapListener(GreekChartListener.onSingleTapListener singleTapListener) {
        this.singleTapListener = singleTapListener;
        if (chartView != null) chartView.setOnSingleTapListener(singleTapListener);
    }

    public void setOnRemoveIndicatorListener(GreekChartListener.onRemoveIndicatorListener removeIndicatorListener) {
        this.removeIndicatorListener = removeIndicatorListener;
        if (chartView != null) chartView.setOnRemoveIndicatorListener(removeIndicatorListener);
    }

    public void setOnNotifyListener(GreekChartListener.onNotifyListener notifyListener) {
        this.notifyListener = notifyListener;
        if (chartView != null) {
            chartView.setOnNotifyListener(notifyListener);
            // chartSettings.isBaseIntervalFirstTime = false;
        }
    }

    /**
     * @return Chart Layout
     */
    public LinearLayout getChartLayout() {
        return chartLayout;
    }

    /**
     * @return Chart as Bitmap View
     */
    public Bitmap getChartAsBitmapView() {
        return chartView.toBitmapFromView();
    }

    /**
     * @return Chart as a View
     */
    public View getChartView() {
        return chartView.chartView();
    }

    private void setCommonMethods(ChartSettings chartSettings) {
        chartSettings.setxAxisPositions(mainChartRenderer.getxAxisPositions());
        chartSettings.setVirtualValues(mainChartRenderer.getVirtualValues());
        notifyChartData.setVirtualValues(mainChartRenderer.getVirtualValues());
        if (notifyListener != null) notifyListener.onNotifyData(notifyChartData);

        chartSettings.setLeftMargin(mainChartRenderer.getLeftMargin());
        chartSettings.setTopMargin(mainChartRenderer.getTopMargin());
        chartSettings.setRightMargin(mainChartRenderer.getRightMargin());
        chartSettings.setBottomMargin(mainChartRenderer.getBottomMargin());
        chartSettings.setWidth(mainChartRenderer.getWidth());

        chartSettings.setDateFormat(mainChartRenderer.getDateFormat());
        chartSettings.setIntervalBtwPoints(mainChartRenderer.getIntervalBtwPoints());
        chartSettings.setMaxX(mainChartRenderer.getMaxX());
    }

    private void drawVolumeChart() {
        try {
            volumeChartRenderer = chartSettings.deepClone();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (chartSettings.getTypeface() != null)
            volumeChartRenderer.setTypeface(chartSettings.getTypeface());

        volumeChartRenderer.setShowXGrid(false);
        volumeChartRenderer.setShowYGrid(false);
        volumeChartRenderer.setxLabelValue(date);
        volumeChartRenderer.setyLabelValue(volumeValues);
        volumeChartRenderer.setChartType(mainChartRenderer.getChartType());
        volumeChartRenderer.setCandlePositiveColor(mainChartRenderer.getCandlePositiveColor());
        volumeChartRenderer.setCandleNegativeColor(mainChartRenderer.getCandleNegativeColor());
        volumeChartRenderer.setChartTitle(chartSettings.getVolumeTitle());
        volumeChartRenderer.setIndicatorLabel(ChartConstants.VOLUME);

        setCommonMethods(volumeChartRenderer);

        List<double[]> volumeYValues = new ArrayList<double[]>();
        volumeYValues.add(volumeValues);
        volumeChartRenderer.setYAxisValue(volumeYValues);

        ArrayList<String> volumeSeriesName = new ArrayList<String>();
        volumeSeriesName.add(ChartConstants.VOLUME);

        VolumeBarChart volumeBarChart = new VolumeBarChart(context, buildDateDataset(date, volumeYValues, volumeSeriesName), volumeChartRenderer);
        volumeChart = volumeBarChart;
    }

    private void createOutlineIndicator(IndicatorData indicatorData, String indicatorNameAndValue) {

        ChartSettings outlineRenderer = new ChartSettings();
        try {
            outlineRenderer = chartSettings.deepClone();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (chartSettings.getTypeface() != null)
            outlineRenderer.setTypeface(chartSettings.getTypeface());

        if (!mainChartRenderer.isShowVolumeChart()) {
            mainChartRenderer.setShowXLabels(false);
        }
        outlineRenderer.setxLabelValue(date);

        outlineRenderer.setShowXGrid(false);
        outlineRenderer.setShowYGrid(false);

        setCommonMethods(outlineRenderer);

        outlineRenderer.setChartTitle(chartSettings.getVolumeTitle());
        outlineRenderer.setIndicatorType(ChartIndicatorType.OUTLINE);

        // Split the Indicator Name & Value from indicatorNameAndValue
        String indicatorName = indicatorNameAndValue.split(VALUE_SEPERATOR)[0];
        String indicatorValue = "";

        if (indicatorNameAndValue.contains(VALUE_SEPERATOR)) {
            /** Example:- SMA:7 , MACD:25:10:7 */

            // indicatorValue - 7 , 25:10:7
            indicatorValue = indicatorNameAndValue.substring(indicatorNameAndValue.indexOf(VALUE_SEPERATOR) + 1);
        }

        String indicatorLabel = (indicatorValue.length() > 0) ? indicatorName + "(" + indicatorValue.replaceAll(VALUE_SEPERATOR, ",") + ")" : indicatorName;
        outlineRenderer.setIndicatorLabel(indicatorLabel);

        outlineRenderer.setIndicatorKeyValue(indicatorNameAndValue);

        ArrayList<String> outlineSeriesName = new ArrayList<String>();

        List<double[]> outlineValues = new ArrayList<double[]>();
        outlineValues.clear();
        for (String output : indicatorData.getOutputKey()) {
            String outputKeyValue = indicatorValue.length() > 0 ? output + VALUE_SEPERATOR + indicatorValue : output;
            //            For volume indicators - no need to consider input values
            if (output.equalsIgnoreCase(ChartConstants.VOLUME)) {
                outputKeyValue = output;
            }

            boolean isAddIndicatorSuccess = indicatorKeyValues.containsKey(outputKeyValue);
            if (!isAddIndicatorSuccess) {
                if (missedIndicators != null) {
                    missedIndicators += ", " + indicatorData.getName() + VALUE_SEPERATOR + indicatorValue;
                } else {
                    missedIndicators = indicatorData.getName() + VALUE_SEPERATOR + indicatorValue;
                }
                break;
            }

            if (isAddIndicatorSuccess) {
                outlineSeriesName.add(outputKeyValue);
                outlineValues.add(indicatorKeyValues.get(outputKeyValue));
            }
        }

        if (outlineValues.size() < 1) {
            GreekChartLogger.logError("No Output available for " + indicatorLabel);
            return;
        }

        outlineRenderer.setYAxisValue(outlineValues);

        OutlineChart outline = new OutlineChart(context, buildDateDataset(date, outlineValues, outlineSeriesName), outlineRenderer);

        multipleCharts.add(outline);
        multipleRenderer.add(outlineRenderer);

    }

    //Consensus Chart
    public void showConsensusChart(ChartSettings chartSettings, LinearLayout layout, int lineColor) {
        this.chartSettings = chartSettings;

        if (!splitValuesFromResponseForConsensusChart(layout)) return;
        handleConsensusChartSettings(layout, lineColor, 0, ChartType.ConsensusChart);
    }

    private boolean splitValuesFromResponseForConsensusChart(LinearLayout layout) {

        // If ratings values is not null, then the values are already split. So no
        // need to split again, return it.
        if (ratingValues != null) return true;

        //592 <RATINGS,DATE,BUYOVERWEIGHTRATTING,HOLDRATING,SELLUNDERWEIGHTRATING|22,20110902,50,45,5
        //592 <DATE,ACTUALPRICE,TARGETPRICE|20110902,3.13,3.40903|

        Hashtable chartData = chartSettings.getChartData();

        String key;
        Enumeration enumeration = chartData.keys();
        while (enumeration.hasMoreElements()) {
            key = (String) enumeration.nextElement();

            if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_RATINGS)) {
                ArrayList val = (ArrayList) chartData.get(key);
                ratingValues = new double[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    ratingValues[i] = Double.parseDouble(val.get(i).toString());
                }

            } else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_SELLUNDERWEIGHTRATING)) {
                ArrayList val = (ArrayList) chartData.get(key);
                sellUnderWeightValues = new double[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    sellUnderWeightValues[i] = Double.parseDouble(val.get(i).toString());
                }
            } else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_BUYOVERWEIGHTRATTING)) {
                ArrayList val = (ArrayList) chartData.get(key);
                buyOverWeightValues = new double[val.size()];
                newBuyValues = new double[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    buyOverWeightValues[i] = Double.parseDouble(val.get(i).toString());
                    newBuyValues[i] = 100;//New Buy always as the maximum value say 100
                }
            } else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_HOLDRATING)) {
                ArrayList val = (ArrayList) chartData.get(key);
                holdValues = new double[val.size()];
                newHoldValues = new double[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    holdValues[i] = Double.parseDouble(val.get(i).toString());
                }
            } else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_DATE_OF_RATINGS_CHART)) {
                ArrayList val = (ArrayList) chartData.get(key);
                dateValues = new String[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    dateValues[i] = val.get(i).toString();
                }
            }
            //Price Chart
            else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_TARGET_PRICE)) {
                ArrayList val = (ArrayList) chartData.get(key);
                targetPriceValues = new double[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    targetPriceValues[i] = Double.parseDouble(val.get(i).toString());
                }

            } else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_ACTUAL_PRICE)) {
                ArrayList val = (ArrayList) chartData.get(key);
                actualPriceValues = new double[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    actualPriceValues[i] = Double.parseDouble(val.get(i).toString());
                }
            } else if (key.equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_DATE_OF_PRICE_CHART)) {
                ArrayList val = (ArrayList) chartData.get(key);
                dateValuesForPriceChart = new String[val.size()];
                for (int i = 0; i < val.size(); i++) {
                    dateValuesForPriceChart[i] = val.get(i).toString();
                }
            }
        }

        if (holdValues != null && sellUnderWeightValues != null) {
            for (int i = 0; i < holdValues.length; i++) {
                newHoldValues[i] = holdValues[i] + sellUnderWeightValues[i];//New Hold is the addition of hold and buy
            }
        }


		/*String key;
		Enumeration enumeration = chartData.keys();
		while (enumeration.hasMoreElements()) {
			key = (String) enumeration.nextElement();

			 if (key.equalsIgnoreCase(ChartConstants.BAR_CHART_RATINGS)) {
				String ratings_values[] = (String[]) chartData.get(key);

				ratingValues = new double[ratings_values.length];
				for (int i = 0; i < ratings_values.length; i++) {
					ratingValues[i] = Double.parseDouble(ratings_values[i]);
				}

			}else if (key.equalsIgnoreCase(ChartConstants.BAR_CHART_SELLUNDERWEIGHTRATING)) {
				String sell_underweight_values[] = (String[]) chartData.get(key);

				sellUnderWeightValues = new double[sell_underweight_values.length];
				for (int i = 0; i < sell_underweight_values.length; i++) {
					sellUnderWeightValues[i] = Double.parseDouble(sell_underweight_values[i]);
				}

			}else if (key.equalsIgnoreCase(ChartConstants.BAR_CHART_BUYOVERWEIGHTRATTING)) {
				String buy_overweight_values[] = (String[]) chartData.get(key);

				buyOverWeightValues = new double[buy_overweight_values.length];
				for (int i = 0; i < buy_overweight_values.length; i++) {
					buyOverWeightValues[i] = Double.parseDouble(buy_overweight_values[i]);
				}

			} else if (key.equalsIgnoreCase(ChartConstants.BAR_CHART_HOLDRATING)) {
				String hold_values[] = (String[]) chartData.get(key);

				holdValues = new double[hold_values.length];
				for (int i = 0; i < hold_values.length; i++) {
					holdValues[i] = Double.parseDouble(hold_values[i]);
				}

			}
			else if (key.equalsIgnoreCase(ChartConstants.BAR_CHART_DATE)) {
				dateValues = (String[]) chartData.get(key);
			}

		}*/

        //Date of Ratings Chart
        length = ratingValues.length;

        formatter = new SimpleDateFormat("yyyyMMdd");//20110902

        date.clear();
        for (int f = 0; f < length; f++) {
            try {
                // For Response 342 alone. B'coz response 342 won't contains
                // time.
                // So add time to maintain same date format in all chart
                // response.
                dateValues[f] = dateValues[f].contains(" ") ? dateValues[f] : dateValues[f] + " 00:00:00";
                date.add(formatter.parse(dateValues[f]));
            } catch (ParseException e) {
                try {
                    // For Response 342 alone. B'coz response 342 won't contains
                    // time.
                    date.add(formatter.parse(dateValues[f] + " 00:00:00"));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Date of Price Chart
        length = targetPriceValues.length;

        formatter = new SimpleDateFormat("yyyyMMdd");//20110902

        dateOfPriceArr.clear();
        for (int f = 0; f < length; f++) {
            try {
                // For Response 342 alone. B'coz response 342 won't contains
                // time.
                // So add time to maintain same date format in all chart
                // response.
                dateValuesForPriceChart[f] = dateValuesForPriceChart[f].contains(" ") ? dateValuesForPriceChart[f] : dateValuesForPriceChart[f] + " 00:00:00";
                dateOfPriceArr.add(formatter.parse(dateValuesForPriceChart[f]));
            } catch (ParseException e) {
                try {
                    // For Response 342 alone. B'coz response 342 won't contains
                    // time.
                    dateOfPriceArr.add(formatter.parse(dateValuesForPriceChart[f] + " 00:00:00"));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private void handleConsensusChartSettings(LinearLayout layout, int Color1, int Color2, ChartType type) {

        chartLayout.removeAllViews();

        // For ICICI widget
        if (chartLayout.getId() == layout.getId()) layout = chartLayout;

        layout.removeAllViews();

        legendTextView.setText("");
        hintTextView.setText("");

        if (chartSettings.getChartWidth() == 0) chartWidth = layout.getWidth();
        else chartWidth = chartSettings.getChartWidth() * scale;

        if (chartSettings.getChartHeight() == 0) {
            if (layout.getParent() instanceof ChartInterceptScrollView) {
                chartHeight = ((ChartInterceptScrollView) layout.getParent()).getHeight();
            } else {
                chartHeight = layout.getHeight();
            }
        } else {
            chartHeight = chartSettings.getChartHeight() * scale;
        }

        chartSettings.setShowXGrid(false);//No need grids on chart
        chartSettings.setShowYGrid(false);
        chartSettings.setYGridCount(10);// Y axis contains 0 to 100
        chartSettings.setyLabelDecimalPoint(0);//No need decimal values in Y axis
        chartSettings.setTopMargin(30);
        //chartSettings.setLeftMargin(5);
        chartSettings.setLeftMargin(-30);
        chartSettings.setBottomMargin(-20);

        GreekChartLogger.logMsg("Chart Height = " + chartHeight);
        GreekChartLogger.logMsg("Chart Width = " + chartWidth);

        if (chartHeight < 1 || chartWidth < 1) {
            GreekChartLogger.logError("Chart Width or Height is lesser then 1. Can't proceed further.");
            return;
        }

        chartSettings.setYAxisRightAlign(false);

        seriesValues.clear();
        seriesName.clear();

        seriesValues.add(ratingValues);
        seriesValues.add(newBuyValues);//changed as new buy values
        seriesValues.add(newHoldValues);//changed as new hold values
        seriesValues.add(sellUnderWeightValues);
        //seriesValues.add(newHoldValues);//changed as new hold values
        seriesName.add(ChartConstants.CONSENSUS_CHART_RATINGS);
        seriesName.add(ChartConstants.CONSENSUS_CHART_BUYOVERWEIGHTRATTING);
        seriesName.add(ChartConstants.CONSENSUS_CHART_HOLDRATING);
        seriesName.add(ChartConstants.CONSENSUS_CHART_SELLUNDERWEIGHTRATING);


        // Set the chartSettings values
        try {
            mainChartRenderer = chartSettings.deepClone();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (chartSettings.getTypeface() != null)
            mainChartRenderer.setTypeface(chartSettings.getTypeface());

        // Check volume min and max value. If both are Zero hide the volume
        // chart.
        double minMax[] = MathHelper.getMinMaxValue(volumeValues);
        if (minMax[0] == 0 && minMax[1] == 0) {
            mainChartRenderer.setShowVolumeChart(false);
        }

        mainChartRenderer.setChartType(type);

        mainChartRenderer.setChartWidth(chartWidth);

        mainChartRenderer.setIntervalBtwPoints(mainChartRenderer.getIntervalBtwPoints() * scale);
        mainChartRenderer.setVirtualValues(0);
        // notifyChartData.setVirtualValues(0);
        // notifyListener.onNotifyData(notifyChartData);

        Paint paint = new Paint();
        paint.setTextSize(mainChartRenderer.getChartTitleTextSize() * scale);

		/*if (type != ChartType.SparkLineChart
				&& type != ChartType.SparkAreaChart) {
			// Calculate Left, Right, Top & Bottom Margin for charts
			mainChartRenderer.setLeftMargin(Math.round(scale
					* mainChartRenderer.getLeftMargin()));
			mainChartRenderer.setTopMargin(Math.round(scale
					* mainChartRenderer.getTopMargin()));

			if (mainChartRenderer.isShowYLabels()) {
				// Y Axis Labels Visible
				mainChartRenderer
						.setRightMargin(Math.round(paint
								.measureText(mainChartRenderer
										.getTextWidthCalculateText()))
								+ Math.round(scale
										* mainChartRenderer.getRightMargin()));
			} else {
				// Y Axis Labels Not Visible
				mainChartRenderer.setRightMargin(Math.round(scale
						* mainChartRenderer.getRightMargin()));
			}
		} */
        //else {
			/*mainChartRenderer.setLeftMargin(0);
			mainChartRenderer.setTopMargin(0);
			mainChartRenderer.setRightMargin(0);*/
        //}
			
			
			/*mainChartRenderer
			.setRightMargin(Math.round(paint
					.measureText(mainChartRenderer
							.getTextWidthCalculateText()))
					+ Math.round(scale
							* mainChartRenderer.getRightMargin()));*/

        // Y Axis Labels Visible
        mainChartRenderer.setLeftMargin(Math.round(paint.measureText(mainChartRenderer.getTextWidthCalculateText())) + Math.round(scale * mainChartRenderer.getLeftMargin()));

        Rect measeureHeightRect = new Rect();
        paint.getTextBounds("a", 0, 1, measeureHeightRect);
		/*if (type != ChartType.SparkLineChart
				&& type != ChartType.SparkAreaChart) {*/

        mainChartRenderer.setBottomMargin(measeureHeightRect.height() + Math.round(scale * mainChartRenderer.getBottomMargin()));
		/*} else
			mainChartRenderer.setBottomMargin(0);*/

        mainChartRenderer.setxLabelValue(date);

        // Get the exact chart drawing width
        float width = (mainChartRenderer.getChartWidth() - mainChartRenderer.getRightMargin() - mainChartRenderer.getLeftMargin());
        mainChartRenderer.setWidth(width);

        // For OHLC & Candle Stick chart, we need to give 5px margin at left &
        // right side inside the chart.
		/*if (mainChartRenderer.getChartType() != ChartType.OHLCChart
				&& mainChartRenderer.getChartType() != ChartType.CandleStickChart) {*/
        mainChartRenderer.setEndPointSpace(0);
        //}

        // Interval between 2 points.
        float interval = mainChartRenderer.getIntervalBtwPoints();

        if (chartSettings.isPanningEnabled()) {
            // If panning is enabled, use default interval.

            // If plotting width is greater then points calculated width,
            // calculate interval as width/point size.
            if (((date.size() - 1) * interval) < width) {
                interval = ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (date.size() - 1));
            }
        } else {
            // If panning is disabled, we need to show all the values in screen
            // at
            // a time. So calculate interval as width/point size.
            interval = ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (date.size() - 1));
        }

        mainChartRenderer.setIntervalBtwPoints(interval);


        // Used after the user has pinched the chart.
        // if (chartSettings.isBaseIntervalFirstTime) {
        mainChartRenderer.setBaseIntervalBtwPoints(interval);

        GreekChartLogger.logMsg("Interval Btw 2 Points = " + interval);

        // Calculate X value for all points
        if (mainChartRenderer.isDefaultZoomIn()) {
            new CalculatePoints().calculateXPoints(mainChartRenderer, interval);
        } else {
            new CalculatePoints().calculateXPoints(mainChartRenderer, ((width - (mainChartRenderer.getEndPointSpace() * 2)) / (date.size() - 1)));
        }

        // if (chartSettings.isVirtualValueFirstTime) {

        double lastPoint = mainChartRenderer.getMaxX();

        // Virtual value is used to get the starting point of the x label in
        // screen.
        double virtual = 0;

        if (width < lastPoint && mainChartRenderer.isYAxisRightAlign()) {
            virtual = (lastPoint - width);
        }

        mainChartRenderer.setVirtualValues(virtual);

        mainChartRenderer.setMainChart(true);

        mainChartRenderer.setyLabelValue(ratingValues);

        // Set date in top left of the chart
        String startDate = (String) android.text.format.DateFormat.format("MMM dd,yyyy", date.get(0));
        String endDate = (String) android.text.format.DateFormat.format("MMM dd,yyyy", date.get(date.size() - 1));

        try {
            if (startDate.equalsIgnoreCase(endDate)) {
                // Start & End point date are same, So show the date format as
                // Hour:Minutes
                mainChartRenderer.setIntraDate(startDate);
                mainChartRenderer.setDateFormat("HH:mm");
            } else {
                // Start & End point date are different
                mainChartRenderer.setIntraDate(startDate + " - " + endDate);

                // If Start & End point Date are in between 3 days, then set
                // the date format as Hour:Minutes else Month/day 787889
                if (date.get(0).getTime() + (DAY * 3) < date.get(date.size() - 1).getTime()) {
                    mainChartRenderer.setDateFormat("MM/dd");
                } else {
                    mainChartRenderer.setDateFormat("HH:mm");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        chartSettings.setIntraDate(mainChartRenderer.getIntraDate());

        if (mainChartRenderer.getxLabelDateFormat() != XLabelDateFormat.DEFAULT) {
            String dateFormat = mainChartRenderer.getDateFormat();

            switch (mainChartRenderer.getxLabelDateFormat()) {
                case HOUR_MINUTES_HHmm:
                    dateFormat = "HH:mm";
                    break;
                case MONTH_DAY_MMdd:
                    dateFormat = "MM/dd";
                    break;
                case DAY_MONTH_ddMM:
                    dateFormat = "dd/MMM";
                    break;
                case DAY_MONTH_YEAR_ddMMyyyy:
                    dateFormat = "dd/MM/yyyy";
                    break;
                case MONTH_DAY_YEAR_MMddyyyy:
                    dateFormat = "MM/dd/yyyy";
                    break;
                case MINUTES_SECONDS_mmss:
                    dateFormat = "mm:ss";
                    break;
                case HOUR_MINUTES_SECONDS_HHmmss:
                    dateFormat = "kk:mm:ss";
                    break;
                case MONTH_YEAR_MMyyyy:
                    dateFormat = "MM/yyyy";
                    break;
                case MONTH_YEAR_MMyy://For Consensus Chart
                    dateFormat = "MM/yy";
                    break;
            }
            mainChartRenderer.setDateFormat(dateFormat);
        }

        GreekChartLogger.logMsg("Date Format = " + mainChartRenderer.getDateFormat());

        mainChartRenderer.setYAxisValue(seriesValues);

		/*if (type == ChartType.LineChart || type == ChartType.AreaChart
				|| type == ChartType.SparkLineChart
				|| type == ChartType.SparkAreaChart) {*/
        mainChartRenderer.setLineColor(Color1);
			/*if (type == ChartType.AreaChart || type == ChartType.SparkAreaChart) {
				mainChartRenderer.setFillBelowLine(true);
				mainChartRenderer.setFillBelowLineColor(Color2);
			}
		} */
		/*else if (type == ChartType.OHLCChart
				|| type == ChartType.CandleStickChart) {
			mainChartRenderer.setCandlePositiveColor(Color1);
			mainChartRenderer.setCandleNegativeColor(Color2);
		}*/
        mainChartRenderer.setApplyBackgroundColor(true);

        mainChartRenderer.setVolumeValues(volumeValues);

        XYMultipleSeriesDataset seriesDataset = buildDateDataset(date, seriesValues, seriesName);
        mainChartRenderer.setDataset(seriesDataset);

		/*LineChart lineChart = null;
		CandleOHLCChart candleOHLCChart = null;
		if (type == ChartType.LineChart || type == ChartType.AreaChart
				|| type == ChartType.SparkLineChart
				|| type == ChartType.SparkAreaChart) {
			lineChart = new LineChart(context, seriesDataset, mainChartRenderer);
			mChart = (AbstractChart) lineChart;
		} else if (type == ChartType.CandleStickChart
				|| type == ChartType.OHLCChart) {
			candleOHLCChart = new CandleOHLCChart(context, seriesDataset,
					mainChartRenderer);
			mChart = (AbstractChart) candleOHLCChart;
		}*/
        BarChart barChart = null; //BAR CHART
        barChart = new BarChart(context, seriesDataset, mainChartRenderer);
        mChart = barChart;

        if (chartSettings.isShowDate()) {
            legendTextView.setTextColor(chartSettings.getDateTextColor());

            String indName = mainChartRenderer.getIntraDate();
            indName += " " + mainChartRenderer.getLegend() + " ";

            if (indName.endsWith(", ")) indName = indName.substring(0, indName.length() - 2);
            legendTextView.setText(Html.fromHtml(indName));
            legendTextView.setPadding(15, 0, 0, 0);
            legendTextView.setTextSize(mainChartRenderer.getDateFontSize());

            if (chartSettings.getTypeface() != null)
                legendTextView.setTypeface(chartSettings.getTypeface());

            chartLayout.addView(legendTextView);

            chartHeight = chartHeight - legendTextView.getLineHeight();
        }
        hintTextView.setText("Testing Testing Testing");
        hintTextView.setTextColor(Color.BLUE);

        mainChartRenderer.setChartHeight(chartHeight);

        if (chartSettings.isJoinCharts()) {
            if (mainChartRenderer.isShowVolumeChart()) {
                mainChartRenderer.setShowXLabels(false);
            }
        }

		/*if (mainChartRenderer.isShowVolumeChart()) {
			drawVolumeChart();
		}*/

        multipleRenderer.clear();
        multipleCharts.clear();

        multipleRenderer.add(mainChartRenderer);
        multipleCharts.add(mChart);
        if (mainChartRenderer.isShowVolumeChart()) {
            multipleRenderer.add(volumeChartRenderer);
            multipleCharts.add(volumeChart);
        }

        chartView = new GreekChartView(context, multipleCharts, multipleRenderer, chartHeight, chartWidth);
        chartView.setOnLongPressListener(longClickListener);
        chartView.setOnDoubleTapListener(doubleTapListener);
        chartView.setOnSingleTapListener(singleTapListener);
        chartView.setOnRemoveIndicatorListener(removeIndicatorListener);
        chartView.setOnNotifyListener(notifyListener);
        chartLayout.addView(chartView);

        if (chartLayout.getId() != layout.getId()) layout.addView(chartLayout);

		/*
		 * // Add Indicators for (String indicator :
		 * chartSettings.getIndicatorNameAndValues()) { addIndicator(indicator);
		 * }
		 */

        missedIndicators = null;

        // Add Indicators
        for (String indicator : chartSettings.getIndicatorNameAndValues()) {
            boolean isAddIndicatorSuccess = addIndicator(indicator);
            if (!isAddIndicatorSuccess) {
                if (missedIndicators != null) {
                    missedIndicators += ", " + indicator;
                } else {
                    missedIndicators = indicator;
                }
            }
        }

        if (missedIndicators != null && indicatorMissingListener != null) {
            indicatorMissingListener.onIndicatorMissing(missedIndicators);
        }

    }
}
