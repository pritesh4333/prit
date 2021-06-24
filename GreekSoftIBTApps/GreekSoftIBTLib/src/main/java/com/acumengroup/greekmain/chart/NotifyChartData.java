package com.acumengroup.greekmain.chart;

/**
 * Created by Arcadia
 */
public class NotifyChartData {

    private boolean isCrossHairEnabled = false;
    private boolean isMagnifyMovable = false;
    private boolean isMagnify = false;
    private float intervalBtwPoints = 10;
    private float pannedPixels = 0;
    private double virtualValues = 0;

    public boolean isMagnifyMovable() {
        return isMagnifyMovable;
    }

    public void setMagnifyMovable(boolean isMagnifyMovable) {
        this.isMagnifyMovable = isMagnifyMovable;
        this.isMagnify = false;
        this.isCrossHairEnabled = false;
    }

    public boolean isMagnify() {
        return isMagnify;
    }

    public void setMagnify(boolean isMagnify) {
        this.isMagnify = isMagnify;
        this.isMagnifyMovable = false;
        this.isCrossHairEnabled = false;
    }

    public boolean isCrossHairEnabled() {
        return isCrossHairEnabled;
    }

    public void setCrossHairEnabled(boolean isCrossHairEnabled) {
        this.isCrossHairEnabled = isCrossHairEnabled;
        this.isMagnify = false;
        this.isMagnifyMovable = false;
    }

    public float getIntervalBtwPoints() {
        return intervalBtwPoints;
    }

    public void setIntervalBtwPoints(float intervalBtwPoints) {
        this.intervalBtwPoints = intervalBtwPoints;
    }

    public float getPannedPixels() {
        return pannedPixels;
    }

    public void setPannedPixels(float pannedPixels) {
        this.pannedPixels = pannedPixels;
    }

    public double getVirtualValues() {
        return virtualValues;
    }

    public void setVirtualValues(double virtualValues) {
        this.virtualValues = virtualValues;
    }


}
