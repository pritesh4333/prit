package com.acumengroup.greekmain.chart.tools;

import com.acumengroup.greekmain.chart.settings.ChartSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * * Created by Arcadia
 */
public class CalculatePoints {

    /**
     * @param mainChartRenderer
     * @param interval          <br>
     *                          Calculate X Points using interval mentioned.
     */
    public void calculateXPoints(ChartSettings mainChartRenderer, float interval) {
        List<Double> xPoints = new ArrayList<Double>();
        int xSeriesLength = mainChartRenderer.getxLabelValue().size();
        double val = 0;
        val = val + mainChartRenderer.getEndPointSpace();
        for (int j = 0; j < xSeriesLength; j++) {
            xPoints.add(val);
            if (j != xSeriesLength - 1) val += interval;
        }

        val = val + mainChartRenderer.getEndPointSpace();
        mainChartRenderer.setMaxX(val);
        mainChartRenderer.setxAxisPositions(xPoints);
    }

    public void calculateXPointsForProfitLossChart(ChartSettings mainChartRenderer, float interval) {

        List<Double> xPoints = new ArrayList<Double>();
        int xSeriesLength = mainChartRenderer.getxLabelSeries().length;

        double val = 0;
        val = val + mainChartRenderer.getEndPointSpace();
        for (int j = 0; j < xSeriesLength; j++) {
            xPoints.add(val);
            if (j != xSeriesLength - 1) val += interval;
        }

        val = val + mainChartRenderer.getEndPointSpace();
        mainChartRenderer.setMaxX(val);
        mainChartRenderer.setxAxisPositions(xPoints);
    }

}
