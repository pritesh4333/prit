package com.acumengroup.greekmain.chart;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.acumengroup.greekmain.chart.settings.ChartSettings;

import java.io.Serializable;

public abstract class AbstractChart implements Serializable {

    /**
     * The graphical representation of the chart.
     *
     * @param canvas the canvas to paint
     * @param x      the top left x value of the view to draw
     * @param y      the top left y value of the view to draw
     * @param width  the width of the view to draw
     * @param height the height of the view to draw
     */
    public abstract void draw(Canvas canvas, float xPos, int yPos, float width, int height);

    public abstract void drawStreaming(Canvas canvas, int xPos, int yPos, float width, int height);

    public abstract void drawCrossHairs(Canvas canvas, float xPos, float yPos, float width, float height);

    public abstract void drawMagnifier(Canvas canvas, Bitmap bitmap, float xPos, float yPos, float width, float height);

    protected void drawBackground(ChartSettings paramDefaultRenderer, Canvas paramCanvas, float x, int y, int width, int height, Paint paramPaint) {
        if (paramDefaultRenderer.isApplyBackgroundColor()) {
            paramPaint.setColor(paramDefaultRenderer.getBackgroundColor());
            paramPaint.setStyle(Paint.Style.FILL);
            paramCanvas.drawRect(x, y, x + width, y + height, paramPaint);
        }
    }

    /**
     * The graphical representation of a path.
     *
     * @param canvas   the canvas to paint to
     * @param points   the points that are contained in the path to paint
     * @param paint    the paint to be used for painting
     * @param circular if the path ends with the start point
     */
    protected void drawPath(Canvas canvas, float[] points, Paint paint, boolean circular) {
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) {
            path.lineTo(points[i], points[i + 1]);
        }
        if (circular) {
            path.lineTo(points[0], points[1]);
        }
        canvas.drawPath(path, paint);

    }

}