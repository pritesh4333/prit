package com.acumengroup.greekmain.chart.settings;

import android.graphics.Color;
import android.graphics.Typeface;

import com.acumengroup.greekmain.chart.draw.ChartConstants.CrossHairDateFormat;
import com.acumengroup.greekmain.chart.draw.ChartConstants.CrossHairMainChartDateFormat;
import com.acumengroup.greekmain.chart.draw.ChartConstants.XLabelDateFormat;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.draw.GreekChartView;
import com.acumengroup.greekmain.chart.json.IndicatorJSONHandler;
import com.acumengroup.greekmain.chart.util.GreekChartLogger;
import com.acumengroup.greekmain.chart.xml.IndicatorXMLHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * * Created by Arcadia
 */
public class ChartSettings extends InternalSettings implements Serializable {

    // Chart Title
    private String chartTitle = "";
    private String chartOHLC = "";
    private float chartTitleTextSize = 12.0F;
    private boolean showChartTitle = false;
    private boolean isShowOHLC = false;
    private int chartTitleColor = Color.WHITE;

    // Line Chart & Area Chart
    private float lineChartLineWidth = 2.0F;

    // Magnify
    private boolean isMagnifyMovable = false;
    private boolean isMagnify = false;
    private int magnifyRadius = 30;

    private int backgroundColor;
    private int axisColor = Color.argb(75, 200, 200, 200);
    private int gridColor = Color.parseColor("#777777");

    private int textColor = Color.WHITE;
    private Hashtable chartData;

    // Indicator
    private int indicatorLineColor = Color.WHITE;
    private int indicatorFillColor = Color.parseColor("#FFE5E9");
    private int indicatorPositiveColor = 0xFFC7FC36;
    private int indicatorNegativeColor = 0xFFFF0300;

    // private int indicatorPositiveColor = 0xFF7DC575;
    // private int indicatorNegativeColor = 0xFFFF5F5F;

    private float indicatorLineWidth = 2;
    private ArrayList<String> indicatorNameAndValues = new ArrayList<String>();
    private String indicatorXMLResponse;
    private String indicatorJSONResponse;
    private Hashtable fullIndicatorData;
    private int outlineIndPositiveColor = 0xFF6BB6FF;
    private double virtualValues = 0;

    // Volume Bar
    private boolean joinCharts = true;
    private boolean showVolumeChart = true;
    private int volumeBarColor = 0xFF6BB6FF;
    private String volumeTitle = "";

    private boolean showAxis = true;

    private float axisWidth = 1;
    private float gridWidth = 1;

    private boolean showYGrid = true;
    private boolean showXGrid = true;

    private boolean roundOffYValues = false;
    private boolean roundOffXValues = false;
    private boolean showYGridLastlabel = false;
    private boolean YAxisRightAlign = true;

    private float labelTextSize = 12.0F;

    private boolean showCrossHairs = false;
    private boolean panningEnabled = true;
    private float crossHairsWidth = 1;
    private int crossHairsColor = Color.YELLOW;
    private int crossHairsMeasureTextColor = Color.BLACK;

    // Previous Close Value
    private double prevClose;
    private boolean showPrevClose = true;
    private int prevCloseLineColor = Color.RED;
    private int prevCloseTextColor = Color.WHITE;
    private float prevCloseTextFontSize = 10.0F;

    // Date
    private boolean showDate = true;
    private boolean showCrossHairDate = true;
    private int dateTextColor = Color.WHITE;
    private float dateFontSize = 10.f;

    // Chart X & Y Labels
    private boolean showXLabels = true;
    private boolean showYLabels = true;
    private int xGirdCount = -1;
    private int yGridCount = -1;

    // For Area Chart
    private boolean applyGradientColor = true;
    private int gradientStartColor = 0xEE0EAFFF;
    private int gradientEndColor = 0xAAA1E0FF;

    // Last Price
    private boolean showLastPrice = false;
    private int lastPriceBG = Color.WHITE;
    private int lastPriceTextColor = Color.BLACK;

    private int yLabelDecimalPoint = 3;
    private float pannedPixels = 0;

    private float chartWidth = 0;
    private float chartHeight = 0;

    private float chartLabelTextSize = 9.0F;
    private String dateFormat = "HH:mm";
    private String errorMessage = "No Chart Available";
    private float intervalBtwPoints = 10;

    private boolean defaultZoomIn = true;

    private boolean pinchZoom = true;
    private float zoomRate = 1;
    private float maxZoomRate = 50f;

    private XLabelDateFormat xLabelDateFormat = XLabelDateFormat.DEFAULT;
    private CrossHairDateFormat crossHairDateFormat = CrossHairDateFormat.HOUR_MINUTES_HHmm;
    private String crossHairCustomDateFormat;
    private CrossHairMainChartDateFormat crossHairMainChartDateFormat = CrossHairMainChartDateFormat.DEFAULT_MMMddyyyy_HHmmss;
    private boolean showCustomDateFormat = true;

    private String legend = "";
    private boolean isDoubleTapOverride = false;
    private boolean isSingleTapOverride = false;

    private boolean showLogMessage = true;

    private boolean showCloseBtnForIndicators = true;
    private boolean showCloseBtnForVolumeChart = false;
    private boolean showOHLCOnCrossHair = false;

    // public boolean isBaseIntervalFirstTime = true;
    // public boolean isVirtualValueFirstTime = true;

    private transient Typeface typeface;
    private transient SimpleDateFormat customDateFormat;

    public ChartSettings() {
        // isBaseIntervalFirstTime = true;
        // isVirtualValueFirstTime=true;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public boolean isShowOHLCOnCrossHair() {
        return showOHLCOnCrossHair;
    }

    public void setShowOHLCOnCrossHair(boolean showOHLCOnCrossHair) {
        this.showOHLCOnCrossHair = showOHLCOnCrossHair;
    }

    public boolean isPinchZoom() {
        return pinchZoom;
    }

    public void setPinchZoom(boolean pinchZoom) {
        this.pinchZoom = pinchZoom;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public CrossHairDateFormat getCrossHairDateFormat() {
        return crossHairDateFormat;
    }

    public void setCrossHairDateFormat(CrossHairDateFormat crossHairDateFormat) {
        this.crossHairDateFormat = crossHairDateFormat;
    }

    public String getCrossHairCustomDateFormat() {
        return crossHairCustomDateFormat;
    }

    public void setCrossHairCustomDateFormat(String crossHairCustomDateFormat) {
        this.crossHairCustomDateFormat = crossHairCustomDateFormat;
    }

    public XLabelDateFormat getxLabelDateFormat() {
        return xLabelDateFormat;
    }

    public void setxLabelDateFormat(XLabelDateFormat xLabelDateFormat) {
        this.xLabelDateFormat = xLabelDateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @returns the First Plotting Point Value
     * <p/>
     * i.e The starting point of the x label in the screen.
     */
    public double getVirtualValues() {
        return virtualValues;
    }

    /**
     * @param virtualValues Virtual value is used to get the starting point of the x label
     *                      in screen.
     */
    public void setVirtualValues(double virtualValues) {
        if (virtualValues < 0) virtualValues = 0;
        this.virtualValues = virtualValues;
    }

    public float getChartLabelTextSize() {
        return this.chartLabelTextSize;
    }

    public void setChartLabelTextSize(float chartLabelTextSize) {
        this.chartLabelTextSize = chartLabelTextSize;
    }

    /**
     * @param indicatorResponse <br>
     *                          Parse the XML content and save it in hashtable. <br>
     *                          You can get the hashtable - getFullIndicatorData()
     */
    public void parseXMLContent(String indicatorResponse) {
        if (indicatorResponse != null) {
            try {
                IndicatorXMLHandler handler = new IndicatorXMLHandler();
                Hashtable indicatorHashtable = handler.parser(indicatorResponse);
                setFullIndicatorData(indicatorHashtable);
            } catch (Exception e) {
            }
        }
    }

    public void parseIndicatorJSONContent(String indicatorResponse) {
        if (indicatorResponse != null) {
            try {
                IndicatorJSONHandler handler = new IndicatorJSONHandler();
                Hashtable indicatorHashtable = handler.parser(indicatorResponse);
                setFullIndicatorData(indicatorHashtable);
            } catch (Exception ignored) {
            }
        }
    }

    public boolean isShowCrossHairDate() {
        return showCrossHairDate;
    }

    public void setShowCrossHairDate(boolean showCrossHairDate) {
        this.showCrossHairDate = showCrossHairDate;
    }

    /**
     * @return the chart title
     */
    public String getChartTitle() {
        return chartTitle;
    }

    public String getChartOHLC() {
        return chartOHLC;
    }

    public void setChartOHLC(String chartOHLC) {
        this.chartOHLC = chartOHLC;
    }

    /**
     * set title to chart
     *
     * @param chartTitle
     */
    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    /**
     * @return chart title text size
     */
    public float getChartTitleTextSize() {
        return chartTitleTextSize;
    }

    /**
     * set chart title text size Default Size - 12.0F
     *
     * @param chartTitleTextSize
     */
    public void setChartTitleTextSize(float chartTitleTextSize) {
        this.chartTitleTextSize = chartTitleTextSize;
    }

    /**
     * @return is chart title displaying
     */
    public boolean isShowChartTitle() {
        return showChartTitle;
    }

    public boolean isShowOHLC() {
        return isShowOHLC;
    }

    public void setShowOHLC(boolean showOHLC) {
        isShowOHLC = showOHLC;
    }

    /**
     * set to show chart title or not Default - false
     *
     * @param showChartTitle
     */
    public void setShowChartTitle(boolean showChartTitle) {
        this.showChartTitle = showChartTitle;
    }

    /**
     * @return the chart title color
     */
    public int getChartTitleColor() {
        return chartTitleColor;
    }

    /**
     * Set the chart title color. Default color - White
     *
     * @param chartTitleColor
     */
    public void setChartTitleColor(int chartTitleColor) {
        this.chartTitleColor = chartTitleColor;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(int axisColor) {
        this.axisColor = axisColor;
    }

    public int getVolumeBarColor() {
        return volumeBarColor;
    }

    public void setVolumeBarColor(int volumeBarColor) {
        this.volumeBarColor = volumeBarColor;
    }

    public String getVolumeTitle() {
        return volumeTitle;
    }

    public void setVolumeTitle(String volumeTitle) {
        this.volumeTitle = volumeTitle;
    }

    public double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(double prevClose) {
        this.prevClose = prevClose;
    }

    public boolean isShowAxis() {
        return showAxis;
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
    }

    public float getAxisWidth() {
        return axisWidth;
    }

    public void setAxisWiidth(float axisWidth) {
        this.axisWidth = axisWidth;
    }

    public float getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(float gridWidth) {
        this.gridWidth = gridWidth;
    }

    public float getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(float labelSize) {
        this.labelTextSize = labelSize;
    }

    public boolean isShowCrossHairs() {
        return showCrossHairs;
    }

    public void setShowCrossHairs(boolean showCrossHairs) {
        this.showCrossHairs = showCrossHairs;
    }

    public float getCrossHairsWidth() {
        return crossHairsWidth;
    }

    public void setCrossHairsWidth(float crossHairsWidth) {
        this.crossHairsWidth = crossHairsWidth;
    }

    public int getCrossHairsColor() {
        return crossHairsColor;
    }

    public void setCrossHairsColor(int crossHairsColor) {
        this.crossHairsColor = crossHairsColor;
    }

    public int getCrossHairsMeasureTextColor() {
        return crossHairsMeasureTextColor;
    }

    public void setCrossHairsMeasureTextColor(int crossHairsMeasureTextColor) {
        this.crossHairsMeasureTextColor = crossHairsMeasureTextColor;
    }

    /**
     * @return <br>
     * Returns the current panning state. <br>
     * Default is True.
     */
    public boolean isPanningEnabled() {
        return panningEnabled;
    }

    /**
     * @param panningEnabled <br>
     *                       Set true to enable panning or false to disable. <br>
     *                       Default is True.
     */
    public void setPanningEnabled(boolean panningEnabled) {
        this.panningEnabled = panningEnabled;
    }

    public boolean isShowPrevClose() {
        return showPrevClose;
    }

    public void setShowPrevClose(boolean showPrevClose) {
        this.showPrevClose = showPrevClose;
    }

    public int getPrevCloseLineColor() {
        return prevCloseLineColor;
    }

    public void setPrevCloseLineColor(int prevCloseLineColor) {
        this.prevCloseLineColor = prevCloseLineColor;
    }

    public int getPrevCloseTextColor() {
        return prevCloseTextColor;
    }

    public void setPrevCloseTextColor(int prevCloseTextColor) {
        this.prevCloseTextColor = prevCloseTextColor;
    }

    public float getPrevCloseTextFontSize() {
        return prevCloseTextFontSize;
    }

    public void setPrevCloseTextFontSize(float prevCloseTextFontSize) {
        this.prevCloseTextFontSize = prevCloseTextFontSize;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public int getDateTextColor() {
        return dateTextColor;
    }

    public void setDateTextColor(int dateTextColor) {
        this.dateTextColor = dateTextColor;
    }

    public float getDateFontSize() {
        return dateFontSize;
    }

    public void setDateFontSize(float dateFontSize) {
        this.dateFontSize = dateFontSize;
    }

    public int getIndicatorLineColor() {
        return indicatorLineColor;
    }

    public void setIndicatorLineColor(int indicatorLineColor) {
        this.indicatorLineColor = indicatorLineColor;
    }

    public int getIndicatorFillColor() {
        return indicatorFillColor;
    }

    public void setIndicatorFillColor(int indicatorFillColor) {
        this.indicatorFillColor = indicatorFillColor;
    }

    public float getIndicatorLineWidth() {
        return indicatorLineWidth;
    }

    public void setIndicatorLineWidth(float indicatorLineWidth) {
        this.indicatorLineWidth = indicatorLineWidth;
    }

    public boolean isShowXLabels() {
        return showXLabels;
    }

    public void setShowXLabels(boolean showXLabels) {
        this.showXLabels = showXLabels;
    }

    public boolean isShowYLabels() {
        return showYLabels;
    }

    /**
     * @param showYLabels <br>
     *                    Set true to visible Y Axis Labels or false to hide the labels. <br>
     *                    Default is True.
     */
    public void setShowYLabels(boolean showYLabels) {
        this.showYLabels = showYLabels;
    }

    /**
     * Default : true
     */
    public boolean isJoinCharts() {
        return joinCharts;
    }

    /**
     * @param joinCharts Default : true
     */
    public void setJoinCharts(boolean joinCharts) {
        this.joinCharts = joinCharts;
    }

    public boolean isApplyGradientColor() {
        return applyGradientColor;
    }

    public void setApplyGradientColor(boolean applyGradientColor) {
        this.applyGradientColor = applyGradientColor;
    }

    public int getGradientStartColor() {
        return gradientStartColor;
    }

    /**
     * @param gradientStartColor - Used for Area Chart <br>
     *                           Gradient Start Color - Default Color is 0xEE0EAFFF <br>
     *                           Ex:- 0xAAA1E0FF or Color.WHITE
     */
    public void setGradientStartColor(int gradientStartColor) {
        this.gradientStartColor = gradientStartColor;
    }

    public int getGradientEndColor() {
        return gradientEndColor;
    }

    /**
     * @param gradientEndColor - Used for Area Chart <br>
     *                         Gradient End Color - Default Color is 0xAAA1E0FF <br>
     *                         Ex:- 0xAAA1E0FF or Color.WHITE
     */
    public void setGradientEndColor(int gradientEndColor) {
        this.gradientEndColor = gradientEndColor;
    }

    /**
     * @return xGirdCount - Number of X labels to be displayed in main chart and
     * other charts. <br>
     * Default is -1. If value is -1, then X labels count will be
     * depends upon the chart width.
     */
    public int getXGridCount() {
        return xGirdCount;
    }

    /**
     * @param xGirdCount - Number of X labels to be displayed in main chart and other
     *                   charts. <br>
     *                   Default is -1. If value is -1, then X labels count will be
     *                   depends upon the chart width.
     */
    public void setXGridCount(int xGirdCount) {
        this.xGirdCount = xGirdCount;
    }

    /**
     * @return yGridCount - Number of Y labels to be displayed in main charts. <br>
     * Default is -1. If value is -1, then Y labels count will be
     * depends upon the chart width.
     */
    public int getYGridCount() {
        return yGridCount;
    }

    /**
     * @param yGridCount - Number of Y labels to be displayed in main charts. <br>
     *                   Default is -1. If value is -1, then Y labels count will be
     *                   depends upon the chart width.
     */
    public void setYGridCount(int yGridCount) {
        this.yGridCount = yGridCount;
    }

    public int getIndicatorPositiveColor() {
        return indicatorPositiveColor;
    }

    public void setIndicatorPositiveColor(int indicatorPositiveColor) {
        this.indicatorPositiveColor = indicatorPositiveColor;
    }

    public int getIndicatorNegativeColor() {
        return indicatorNegativeColor;
    }

    public void setIndicatorNegativeColor(int indicatorNegativeColor) {
        this.indicatorNegativeColor = indicatorNegativeColor;
    }

    public float getLineChartLineWidth() {
        return lineChartLineWidth;
    }

    public void setLineChartLineWidth(float lineChartLineWidth) {
        this.lineChartLineWidth = lineChartLineWidth;
    }

    public boolean isShowLastPrice() {
        return showLastPrice;
    }

    /**
     * @param showLastPrice Default is false
     */
    public void setShowLastPrice(boolean showLastPrice) {
        this.showLastPrice = showLastPrice;
    }

    public int getLastPriceBG() {
        return lastPriceBG;
    }

    public void setLastPriceBG(int lastPriceBG) {
        this.lastPriceBG = lastPriceBG;
    }

    public int getLastPriceTextColor() {
        return lastPriceTextColor;
    }

    public void setLastPriceTextColor(int lastPriceTextColor) {
        this.lastPriceTextColor = lastPriceTextColor;
    }

    /**
     * @return Default is 3.
     */
    public int getyLabelDecimalPoint() {
        return yLabelDecimalPoint;
    }

    /**
     * @param yLabelDecimalPoint <br>
     *                           Default values is 3. <br>
     *                           Ex:- <br>
     *                           192.34076 show as 192.340 <br>
     *                           167.68 show as 167.680 <br>
     */
    public void setyLabelDecimalPoint(int yLabelDecimalPoint) {
        this.yLabelDecimalPoint = yLabelDecimalPoint;
    }

    public int getOutlineIndPositiveColor() {
        return outlineIndPositiveColor;
    }

    public void setOutlineIndPositiveColor(int outlineIndPositiveColor) {
        this.outlineIndPositiveColor = outlineIndPositiveColor;
    }

    public boolean isPanStarted() {
        return GreekChartView.isPanStarted;
    }

    public ArrayList<String> getRemovedIndicators() {
        if (GreekChartView.removeList == null) GreekChartView.removeList = new ArrayList<String>();
        return GreekChartView.removeList;
    }

    /**
     * @return Indicator Data in Hashtable <br>
     * Hashtable Keys are indicator name ( ex:- SMA ) <br>
     * Hashtable Value is IndicatorData Class (
     * com.bfsl.chart.xml.IndicatoData ) <br>
     */
    public Hashtable getFullIndicatorData() {
        return fullIndicatorData;
    }

    public void setFullIndicatorData(Hashtable fullIndicatorData) {
        this.fullIndicatorData = fullIndicatorData;
    }

    public String getIndicatorXMLResponse() {
        return indicatorXMLResponse;
    }

    /**
     * @param indicatorXMLResponse <br>
     *                             Set the full indcator.xml response as String.
     */
    public void setIndicatorXMLResponse(String indicatorXMLResponse) {
        this.indicatorXMLResponse = indicatorXMLResponse;
    }

    public String getIndicatorJSONResponse() {
        return indicatorJSONResponse;
    }

    /**
     * @param indicatorJSONResponse <br>
     *                              Set the full indcator.json response as String.
     */
    public void setIndicatorJSONResponse(String indicatorJSONResponse) {
        this.indicatorJSONResponse = indicatorJSONResponse;
    }

    /**
     * @return the chart values Hashtable contains <br>
     * Open <br>
     * Hign <br>
     * Low <br>
     * Close <br>
     * Volume <br>
     * Date <br>
     * All the above values are compulsory in hashtable. <br>
     * Indicator Values <br>
     */
    public Hashtable getChartData() {
        return chartData;
    }

    public void setChartData(JSONObject jsonObject) {
        try {
            JSONArray chartDataArray = jsonObject.getJSONArray("chartData");

            int size = chartDataArray.length();
            double[] open = new double[size];
            double[] close = new double[size];
            double[] high = new double[size];
            double[] low = new double[size];
            double[] volume = new double[size];
            String[] date = new String[size];

            for (int i = 0; i < chartDataArray.length(); i++) {
                JSONObject pointsJsonObject = chartDataArray.getJSONObject(i);
                open[i] = pointsJsonObject.getDouble(ChartConstants.OPEN);
                close[i] = pointsJsonObject.getDouble(ChartConstants.CLOSE);
                high[i] = pointsJsonObject.getDouble(ChartConstants.HIGH);
                low[i] = pointsJsonObject.getDouble(ChartConstants.LOW);
                volume[i] = pointsJsonObject.getDouble(ChartConstants.VOLUME);
                date[i] = getDateFromTimeStamp(pointsJsonObject.getString(ChartConstants.DATE), "yyyy/MM/dd HH:mm:ss");
            }

            Hashtable chartData = new Hashtable();

            chartData.put(ChartConstants.OPEN, open);
            chartData.put(ChartConstants.HIGH, high);
            chartData.put(ChartConstants.LOW, low);
            chartData.put(ChartConstants.CLOSE, close);
            chartData.put(ChartConstants.DATE, date);
            chartData.put(ChartConstants.VOLUME, volume);

            setChartData(chartData);

        } catch (Exception e) {
            e.printStackTrace();
            GreekChartLogger.logError("Error in Parsing Data");
        }
    }

    /**
     * @param chartData Set the chart values as hashtable. <br>
     *                  Hashtable contains <br>
     *                  Open <br>
     *                  Hign <br>
     *                  Low <br>
     *                  Close <br>
     *                  Volume <br>
     *                  Date <br>
     *                  All the above values are compulsory in hashtable. <br>
     *                  Indicator Values <br>
     */
    public void setChartData(Hashtable chartData) {
        this.chartData = chartData;
        // Clear the cache - Indicator list
        if (GreekChartView.removeList == null) GreekChartView.removeList = new ArrayList<String>();
        else GreekChartView.removeList.clear();
    }

    public String getDateFromTimeStamp(String timeStamp, String customFormat) {
        try {
            if (customDateFormat == null) {
                customDateFormat = new SimpleDateFormat(customFormat);
            }
            return customDateFormat.format(new Date(Long.parseLong(timeStamp)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @return Chart Width with margin
     */
    public float getChartWidth() {
        return chartWidth;
    }

    /**
     * @param chartWidth Chart Width with margin
     */
    public void setChartWidth(float chartWidth) {
        this.chartWidth = chartWidth;
    }

    public boolean isShowVolumeChart() {
        return showVolumeChart;
    }

    public void setShowVolumeChart(boolean showVolumeChart) {
        this.showVolumeChart = showVolumeChart;
    }

    public float getChartHeight() {
        return chartHeight;
    }

    public void setChartHeight(float chartHeight) {
        this.chartHeight = chartHeight;
    }

    public ChartSettings deepClone() {
        try {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(100);
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
            objectoutputstream.writeObject(this);
            byte abyte0[] = bytearrayoutputstream.toByteArray();
            objectoutputstream.close();
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte0);
            ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
            Object clone = objectinputstream.readObject();
            objectinputstream.close();
            return (ChartSettings) clone;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getIndicatorNameAndValues() {
        return indicatorNameAndValues;
    }

    /**
     * @param Format indicatorNameAndValues<br>
     *               SMA:14 <br>
     *               MOM:10 <br>
     *               AVGPRICE <br>
     *               MACD:12:26:9<br>
     */
    public void setIndicatorNameAndValues(ArrayList<String> indicatorNameAndValues) {
        this.indicatorNameAndValues = indicatorNameAndValues;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return Used to align the X Grid Positions
     */
    public float getPannedPixels() {
        return pannedPixels;
    }

    /**
     * @param pannedPixels
     */
    public void setPannedPixels(float pannedPixels) {
        this.pannedPixels = pannedPixels;
    }

    public float getIntervalBtwPoints() {
        return intervalBtwPoints;
    }

    /**
     * @param intervalBtwPoints Default is 5.
     */
    public void setIntervalBtwPoints(float intervalBtwPoints) {
        this.intervalBtwPoints = intervalBtwPoints;
    }

    /**
     * @return Default is true.
     */
    public boolean isDefaultZoomIn() {
        return defaultZoomIn;
    }

    /**
     * @param defaultZoomIn Default is true.
     */
    public void setDefaultZoomIn(boolean defaultZoomIn) {
        this.defaultZoomIn = defaultZoomIn;
    }

    /**
     * @return Default is 1f.<br>
     * Zoom Speed.
     */
    public float getZoomRate() {
        return zoomRate;
    }

    /**
     * @param zoomRate <br>
     *                 Set Zoom Speed <br>
     *                 Default is 1f. <br>
     */
    public void setZoomRate(float zoomRate) {
        this.zoomRate = zoomRate;
    }

    public float getMaxZoomRate() {
        return maxZoomRate;
    }

    /**
     * @param maxZoomRate <br>
     *                    Set Max Zoom Value <br>
     *                    Default is 50. <br>
     */
    public void setMaxZoomRate(float maxZoomRate) {
        this.maxZoomRate = maxZoomRate;
    }

    public boolean isDoubleTapOverride() {
        return isDoubleTapOverride;
    }

    public void setDoubleTapOverride(boolean isDoubleTapOverride) {
        this.isDoubleTapOverride = isDoubleTapOverride;
    }

    public boolean isShowLogMessage() {
        return showLogMessage;
    }

    public void setShowLogMessage(boolean showLogMessage) {
        this.showLogMessage = showLogMessage;
        GreekChartLogger.showLogMessage = showLogMessage;
    }

    public boolean isShowCloseBtnForVolumeChart() {
        return showCloseBtnForVolumeChart;
    }

    public void setShowCloseBtnForVolumeChart(boolean showCloseBtnForVolumeChart) {
        this.showCloseBtnForVolumeChart = showCloseBtnForVolumeChart;
    }

    public CrossHairMainChartDateFormat getCrossHairMainChartDateFormat() {
        return crossHairMainChartDateFormat;
    }

    public void setCrossHairMainChartDateFormat(CrossHairMainChartDateFormat crossHairMainChartDateFormat) {
        this.crossHairMainChartDateFormat = crossHairMainChartDateFormat;
    }

    public boolean isSingleTapOverride() {
        return isSingleTapOverride;
    }

    public void setSingleTapOverride(boolean isSingleTapOverride) {
        this.isSingleTapOverride = isSingleTapOverride;
    }

    public boolean isMagnifyMovable() {
        return isMagnifyMovable;
    }

    public void setMagnifyMovable(boolean isMagnifyMovable) {
        this.isMagnifyMovable = isMagnifyMovable;
    }

    public boolean isMagnify() {
        return isMagnify;
    }

    public void setMagnify(boolean isMagnify) {
        this.isMagnify = isMagnify;
    }

    public int getMagnifyRadius() {
        return magnifyRadius;
    }

    public void setMagnifyRadius(int magnifyRadius) {
        this.magnifyRadius = magnifyRadius;
    }

    public boolean isShowYGrid() {
        return showYGrid;
    }

    public void setShowYGrid(boolean showYGrid) {
        this.showYGrid = showYGrid;
    }

    public boolean isShowXGrid() {
        return showXGrid;
    }

    public void setShowXGrid(boolean showXGrid) {
        this.showXGrid = showXGrid;
    }

    public boolean isRoundOffYValues() {
        return roundOffYValues;
    }

    public void setRoundOffYValues(boolean roundOffYValues) {
        this.roundOffYValues = roundOffYValues;
    }

    public boolean isRoundOffXValues() {
        return roundOffXValues;
    }

    public void setRoundOffXValues(boolean roundOffXValues) {
        this.roundOffXValues = roundOffXValues;
    }

    public boolean getShowYGridLastlabel() {
        return showYGridLastlabel;
    }

    public void setShowYGridLastlabel(boolean showYGridLastlabel) {
        this.showYGridLastlabel = showYGridLastlabel;
    }

    public boolean isYAxisRightAlign() {
        return YAxisRightAlign;
    }

    public void setYAxisRightAlign(boolean yAxisRightAlign) {
        YAxisRightAlign = yAxisRightAlign;
    }

    public boolean isShowCustomDateFormat() {
        return showCustomDateFormat;
    }

    public void setShowCustomDateFormat(boolean showCustomDateFormat) {
        this.showCustomDateFormat = showCustomDateFormat;
    }

    public boolean isShowCloseBtnForIndicators() {
        return showCloseBtnForIndicators;
    }

    public void setShowCloseBtnForIndicators(boolean showCloseBtnForIndicators) {
        this.showCloseBtnForIndicators = showCloseBtnForIndicators;
    }

}
