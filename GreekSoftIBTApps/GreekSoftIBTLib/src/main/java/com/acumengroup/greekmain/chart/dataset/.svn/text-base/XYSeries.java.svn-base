package com.acumengroup.greekmain.chart.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XYSeries implements Serializable {
    private String mTitle;
    private List<Double> mX = new ArrayList<Double>();
    private List<Double> mY = new ArrayList<Double>();

    public XYSeries(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void add(double xValue, double yValue) {
        this.mX.add(Double.valueOf(xValue));
        this.mY.add(Double.valueOf(yValue));
    }

    public void remove(int index) {
        mX.remove(index);
        mY.remove(index);
    }

    public double getX(int paramInt) {
        return this.mX.get(paramInt).doubleValue();
    }

    public double getY(int paramInt) {
//        if(mY.size()>0)
        return this.mY.get(paramInt).doubleValue();
//        return 0;
    }

    public List<Double> getX() {
        return mX;
    }

    public List<Double> getY() {
        return mY;
    }

    public int getItemCount() {
        return this.mX.size();
    }

}
