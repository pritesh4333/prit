package com.acumengroup.greekmain.chart.settings;

import android.graphics.Color;

import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartIndicatorType;
import com.acumengroup.greekmain.chart.draw.ChartConstants.ChartType;
import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.trendline.TrendSeries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * * Created by Arcadia
 */

/**
 *
 * For internal use.
 */
public class InternalSettings implements Serializable {

    private boolean mApplyBackgroundColor;

    private String indicatorLabel = "";
    private ArrayList<String> indicatorKeyValue = new ArrayList<String>();

    private int lineColor = -16776961;

    private int gradientHeight;

    private boolean isMainChart = false;
    private ArrayList<Date> xLabelValue;
    private double yLabelValue[];
    private String intraDate;
    private List<double[]> yAxisValue;
    private boolean mFillBelowLine = false;
    private int mFillColor = Color.argb(125, 0, 0, 200);
    private int candlePositiveColor;
    private int candleNegativeColor;

    private double[] xLabelSeries;

    private double mMinY;
    private double mMaxY;

    private double volumeValues[];
    private ChartType chartType;
    private int mainChartHeightinPercentage = 65;
    private int otherChartsHeightinPercentage = 30;

    private int startIndex;
    private int endIndex;

    private int color1 = Color.GREEN;
    private int color2 = Color.RED;

    private int colors[];

    private XYMultipleSeriesDataset dataset;

    private float width;
    private double maxX;

    private float baseIntervalBtwPoints = 0;

    private List<Double> xAxisPositions = new ArrayList<Double>();

    private String textWidthCalculateText = "44444444";

    private float endPointSpace = 5;

    private int leftMargin = 5;
    private int topMargin = 5;
    private float rightMargin = 5;

	/*private boolean isTextRightAlign = true;
	
	public boolean isTextRightAlign() {
		return isTextRightAlign;
	}

	public void setTextRightAlign(boolean isTextRightAlign) {
		this.isTextRightAlign = isTextRightAlign;
	}*/

    private ChartIndicatorType indicatorType;
    private int bottomMargin = 5;
    private float right;
    private float left;
    private float bottom;
    private double yPixelsPerUnit;
    //TrendLine
    private boolean isTrendline = false;
    private boolean isDeleteTrendline = false;
    private List<TrendSeries> trendSeries = new ArrayList<TrendSeries>();
    //Fibo Line
    private boolean isFiboLine = false;
    private boolean isDeleteFiboLine = false;
    private List<TrendSeries> FiboLineSeries = new ArrayList<TrendSeries>();

    public float getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(float rightMargin) {
        this.rightMargin = rightMargin;
    }

    public boolean isTrendline() {
        return isTrendline;
    }

    public void setTrendline(boolean isTrendline) {
        this.isTrendline = isTrendline;
    }

    public List<TrendSeries> getTrendSeries() {
        return trendSeries;
    }

    public void addTrendSeries(TrendSeries trendSeries) {
        this.trendSeries.add(trendSeries);
    }

    public void deleteTrendSeries() {
        this.trendSeries.clear();
    }

    public boolean isDeleteTrendline() {
        return isDeleteTrendline;
    }

    public void setDeleteTrendline(boolean isDeleteTrendline) {
        this.isDeleteTrendline = isDeleteTrendline;
    }


    public double[] getxLabelSeries() {
        return xLabelSeries;
    }

    public void setxLabelSeries(double[] xLabelSeries) {
        this.xLabelSeries = xLabelSeries;
    }

    public boolean isFiboLine() {
        return isFiboLine;
    }

    public void setFiboLine(boolean isFiboLine) {
        this.isFiboLine = isFiboLine;
    }

    public boolean isDeleteFiboLine() {
        return isDeleteFiboLine;
    }

    public void setDeleteFiboLine(boolean isDeleteFiboLine) {
        this.isDeleteFiboLine = isDeleteFiboLine;
    }

    public void addFiboLineSeries(TrendSeries fiboLineSeries) {
        this.FiboLineSeries.add(fiboLineSeries);
    }

    public List<TrendSeries> getFiboLineSeries() {
        return FiboLineSeries;
    }

    public void deleteFiboLineSeries() {
        this.trendSeries.clear();
    }


    /**
     * @return
     *
     *         the space before first point and space after last point.
     */
    public float getEndPointSpace() {
        return endPointSpace;
    }

    /**
     * @param endPointSpace
     *            The space before first point and space after last point.
     */
    public void setEndPointSpace(float endPointSpace) {
        this.endPointSpace = endPointSpace;
    }

    public String getTextWidthCalculateText() {
        return textWidthCalculateText;
    }

    public void setTextWidthCalculateText(String textWidthCalculateText) {
        this.textWidthCalculateText = textWidthCalculateText;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public int getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public List<Double> getxAxisPositions() {
        return xAxisPositions;
    }

    public void setxAxisPositions(List<Double> xAxisPositions) {
        this.xAxisPositions = xAxisPositions;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    /**
     * @return
     * Exact Chart Width without margin
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width
     * Exact Chart Width without margin
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return<br> User defined Interval or First Set Interval. <br>
     */
    public float getBaseIntervalBtwPoints() {
        return baseIntervalBtwPoints;
    }

    public void setBaseIntervalBtwPoints(float baseIntervalBtwPoints) {
        this.baseIntervalBtwPoints = baseIntervalBtwPoints;
    }

    public XYMultipleSeriesDataset getDataset() {
        return dataset;
    }


    public void setDataset(XYMultipleSeriesDataset dataset) {
        this.dataset = dataset;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public ChartType getChartType() {
        return this.chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public double[] getVolumeValues() {
        return volumeValues;
    }

    public void setVolumeValues(double[] volumeValues) {
        this.volumeValues = volumeValues;
    }

    public double getYAxisMin() {
        return this.mMinY;
    }

    public void setYAxisMin(double paramDouble) {
        this.mMinY = paramDouble;
    }

    public double getYAxisMax() {
        return this.mMaxY;
    }

    public void setYAxisMax(double paramDouble) {
        this.mMaxY = paramDouble;
    }

    public int getCandlePositiveColor() {
        return candlePositiveColor;
    }

    public void setCandlePositiveColor(int candlePositiveColor) {
        this.candlePositiveColor = candlePositiveColor;
    }

    public int getCandleNegativeColor() {
        return candleNegativeColor;
    }

    public void setCandleNegativeColor(int candleNegativeColor) {
        this.candleNegativeColor = candleNegativeColor;
    }

    public int getFillBelowLineColor() {
        return this.mFillColor;
    }

    public void setFillBelowLineColor(int paramInt) {
        this.mFillColor = paramInt;
    }

    public boolean isFillBelowLine() {
        return this.mFillBelowLine;
    }

    public void setFillBelowLine(boolean paramBoolean) {
        this.mFillBelowLine = paramBoolean;
    }

    public List<double[]> getYAxisValue() {
        return yAxisValue;
    }

    public void setYAxisValue(List<double[]> yAxisValue) {
        this.yAxisValue = yAxisValue;
    }

    public String getIntraDate() {
        return intraDate;
    }

    public void setIntraDate(String intraDate) {
        this.intraDate = intraDate;
    }

    public double[] getyLabelValue() {
        return yLabelValue;
    }

    public void setyLabelValue(double[] yLabelValue) {
        this.yLabelValue = yLabelValue;
    }

    public ArrayList<Date> getxLabelValue() {
        return xLabelValue;
    }

    public void setxLabelValue(ArrayList<Date> xLabelValue) {
        this.xLabelValue = xLabelValue;
    }

    public boolean isMainChart() {
        return isMainChart;
    }

    public void setMainChart(boolean isMainChart) {
        this.isMainChart = isMainChart;
    }

    public int getLineColor() {
        return this.lineColor;
    }

    public void setLineColor(int paramInt) {
        this.lineColor = paramInt;
    }

    public boolean isApplyBackgroundColor() {
        return this.mApplyBackgroundColor;
    }

    public void setApplyBackgroundColor(boolean paramBoolean) {
        this.mApplyBackgroundColor = paramBoolean;
    }

    public int getGradientHeight() {
        return gradientHeight;
    }

    public void setGradientHeight(int gradientHeight) {
        this.gradientHeight = gradientHeight;
    }

    public String getIndicatorLabel() {
        return indicatorLabel;
    }

    public void setIndicatorLabel(String indicatorLabel) {
        this.indicatorLabel = indicatorLabel;
    }

    /**
     * Default mainChartHeightinPercentage value is 65.
     *
     * @return
     */
    public int getMainChartHeightinPercentage() {
        return mainChartHeightinPercentage;
    }

    public void setMainChartHeightinPercentage(int mainChartHeightinPercentage) {
        this.mainChartHeightinPercentage = mainChartHeightinPercentage;
    }

    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public int getOtherChartsHeightinPercentage() {
        return otherChartsHeightinPercentage;
    }

    public void setOtherChartsHeightinPercentage(int otherChartsHeightinPercentage) {
        this.otherChartsHeightinPercentage = otherChartsHeightinPercentage;
    }

    public ArrayList<String> getIndicatorKeyValue() {
        return indicatorKeyValue;
    }

    public void setIndicatorKeyValue(String indicatorKeyValue) {
        this.indicatorKeyValue.add(indicatorKeyValue);
    }

    /**
     * @return Inline or Outline
     */
    public ChartIndicatorType getIndicatorType() {
        return indicatorType;
    }

    /**
     * @param indicatorType
     *            Set it as Inline or Outline
     */
    public void setIndicatorType(ChartIndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public double getyPixelsPerUnit() {
        return yPixelsPerUnit;
    }

    public void setyPixelsPerUnit(double yPixelsPerUnit) {
        this.yPixelsPerUnit = yPixelsPerUnit;
    }

}
