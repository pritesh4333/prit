package com.acumengroup.greekmain.chart.dataset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Arcadia
 */
public abstract class BuildDataSet {

    /**
     * Builds an XY multiple time dataset using the provided values.
     *
     * @param xValues the values for the X axis
     * @param yValues the values for the Y axis
     * @return the XY multiple time dataset
     */
    protected XYMultipleSeriesDataset buildDateDataset(ArrayList<Date> xValues, List<double[]> yValues, ArrayList<String> seriesName) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = yValues.size();
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(seriesName.get(i));
            double[] yV = yValues.get(i);
            int seriesLength = xValues.size();
            for (int k = 0; k < seriesLength; k++) {
                series.add(xValues.get(k).getTime(), yV[k]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    protected XYMultipleSeriesDataset buildDataset(double xValues[], List<double[]> yValues, ArrayList<String> seriesName) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = yValues.size();
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(seriesName.get(i));
            double[] yV = yValues.get(i);
            int seriesLength = xValues.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xValues[k], yV[k]);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

}
