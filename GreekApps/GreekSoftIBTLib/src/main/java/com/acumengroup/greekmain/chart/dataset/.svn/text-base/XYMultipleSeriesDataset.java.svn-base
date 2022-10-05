package com.acumengroup.greekmain.chart.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arcadia
 */

/**
 * Dataset for a chart. Ex:- A single chart may contains all OHLC Values. Here
 * each one is a dataset ( ex:- OPEN is a dataset, HIGH is a dataset....) and
 * values are series.
 *
 */
public class XYMultipleSeriesDataset implements Serializable {
    private List<XYSeries> mSeries = new ArrayList<XYSeries>();

    public void addSeries(XYSeries paramXYSeries) {
        this.mSeries.add(paramXYSeries);
    }

    public void removeAllSeries() {
        this.mSeries.clear();
    }

    public List<XYSeries> getSeries() {
        return this.mSeries;
    }

    public XYSeries getSeriesAt(int paramInt) {
        return this.mSeries.get(paramInt);
    }

    public int getSeriesCount() {
        return this.mSeries.size();
    }

}
