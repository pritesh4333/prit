package com.acumengroup.greekmain.chart.trendline;

public class TrendSeries {

    private int xStart = -1;
    private int xEnd = -1;
    private double yStart = -1;
    private double yEnd = -1;

    private double yCenter = -1;

    private float xStartPos;
    private float xEndPos;

    private float yStartPos;
    private float yEndPos;

    private boolean isPointTouched = false;

    private TrendPoint start;
    private TrendPoint end;
    private TrendPoint center;


    private TrendPoint newPoint;


    public TrendPoint getNewPoint() {
        return newPoint;
    }

    public void setNewPoint(TrendPoint newPoint) {
        this.newPoint = newPoint;
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public double getyStart() {
        return yStart;
    }

    public void setyStart(double yStart) {
        this.yStart = yStart;
    }

    public double getyEnd() {
        return yEnd;
    }

    public void setyEnd(double yEnd) {
        this.yEnd = yEnd;
    }

    public float getxStartPos() {
        return xStartPos;
    }

    public void setxStartPos(float xStartPos) {
        this.xStartPos = xStartPos;
    }

    public float getxEndPos() {
        return xEndPos;
    }

    public void setxEndPos(float xEndPos) {
        this.xEndPos = xEndPos;
    }

    public float getyStartPos() {
        return yStartPos;
    }

    public void setyStartPos(float yStartPos) {
        this.yStartPos = yStartPos;
    }

    public float getyEndPos() {
        return yEndPos;
    }

    public void setyEndPos(float yEndPos) {
        this.yEndPos = yEndPos;
    }


    public TrendPoint getStart() {
        return start;
    }

    public void setStart(TrendPoint start) {
        this.start = start;
    }

    public TrendPoint getEnd() {
        return end;
    }

    public void setEnd(TrendPoint end) {
        this.end = end;
    }

    public TrendPoint getCenter() {
        return center;
    }

    public void setCenter(TrendPoint center) {
        this.center = center;
    }

    public boolean isPointTouched() {
        return isPointTouched;
    }

    public void setPointTouched(boolean isPointTouched) {
        this.isPointTouched = isPointTouched;
    }

    public double getyCenter() {
        return yCenter;
    }

    public void setyCenter(double yCenter) {
        this.yCenter = yCenter;
    }


}