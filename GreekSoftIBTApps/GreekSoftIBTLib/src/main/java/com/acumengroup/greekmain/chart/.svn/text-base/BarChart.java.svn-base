package com.acumengroup.greekmain.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;

import com.acumengroup.greekmain.chart.dataset.XYMultipleSeriesDataset;
import com.acumengroup.greekmain.chart.dataset.XYSeries;
import com.acumengroup.greekmain.chart.draw.ChartConstants;
import com.acumengroup.greekmain.chart.settings.ChartSettings;

import java.util.Hashtable;

public class BarChart extends XYChart {

    float buy_points[];
    private int buyBarColor = 0xFFA6C23A;
    private int holdBarColor = 0xFFF8C833;
    private int sellBarColor = 0xFFE94F46;
    private int blackTextColor = 0xFF000000;
    private int darkHoldBarColor = 0xFFD8A409;
    private int darksellBarColor = 0xFFFF2E20;
    private int darkbuyBarColor = 0xFF7F9C16;

    //private ArrayList<Double> updatedBuyArr = new ArrayList<Double>();
    //private ArrayList<Double> updatedHoldArr = new ArrayList<Double>();
    //private ArrayList<Double> originalHoldArr = new ArrayList<Double>();
    //private ArrayList<Double> originalSellArr = new ArrayList<Double>();
    private int transparentBlackBorderColor = 0x30000000;
    private int length = 0;

    /**
     * Builds a new line chart instance.
     *
     * @param context  - application context
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     */
    public BarChart(Context context, XYMultipleSeriesDataset dataset, ChartSettings renderer) {
        super(context, dataset, renderer);
    }

    /**
     * The graphical representation of a series.
     *
     * @param canvas     the canvas to paint
     * @param paint      the paint to be used for drawing
     * @param points     the array of points to be used for drawing the series
     * @param yAxisValue the minimum value of the y axis
     */
    @Override
    public void drawSeries(Canvas canvas, Paint paint, Hashtable<String, float[]> pointsHashtable, float yAxisValue) {

        //Common Declarations
        float barCurVolumeWidth = 35;
        int incrementVal = -1;
        paint.setStyle(Paint.Style.FILL);
        if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
            barCurVolumeWidth = (barCurVolumeWidth * (mRenderer.getIntervalBtwPoints() / 10));

        }
        paint.setStrokeWidth(barCurVolumeWidth);

        //Buy Array
        buy_points = pointsHashtable.get(ChartConstants.CONSENSUS_CHART_BUYOVERWEIGHTRATTING);
        length = buy_points.length;

        //Hold Array
        float hold_points[] = pointsHashtable.get(ChartConstants.CONSENSUS_CHART_HOLDRATING);

        //Sell Array
        float sell_points[] = pointsHashtable.get(ChartConstants.CONSENSUS_CHART_SELLUNDERWEIGHTRATING);

        //Draw Bars using line
        double hold_value = 0, sell_value = 0;
        for (int k = 0; k < buy_points.length; k += 2) {
            float buyX = 0;
            float buyY = 0;

            float holdX = 0;
            float holdY = 0;

            float sellX = 0;
            float sellY = 0;

            //Buy Points
            buyX = buy_points[k];
            buyY = buy_points[k + 1];

            //Hold Points
            holdX = hold_points[k];
            holdY = hold_points[k + 1];

            //Sell Points
            sellX = sell_points[k];
            sellY = sell_points[k + 1];

            //Draw Buy Bar
            incrementVal++;
            paint.setColor(buyBarColor);
            canvas.drawLine(buyX, buyY, buyX, yAxisValue, paint);

            //Draw Hold Bar
            paint.setColor(holdBarColor);
            canvas.drawLine(holdX, holdY, holdX, yAxisValue, paint);

            //Draw Sell Bar
            paint.setColor(sellBarColor);
            canvas.drawLine(sellX, sellY, sellX, yAxisValue, paint);

            //Draw Border Lines
            barCurVolumeWidth = 2;
            if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
                barCurVolumeWidth = (barCurVolumeWidth * (mRenderer.getIntervalBtwPoints() / 2));

            }
            paint.setStrokeWidth(barCurVolumeWidth);
            paint.setColor(transparentBlackBorderColor);
            canvas.drawLine(buyX - 15, buyY, sellX - 15, yAxisValue, paint);//1 - Overall Left Border Line
            canvas.drawLine(buyX + 15, buyY, sellX + 15, yAxisValue, paint);//2 - Overall Right Border Line
            canvas.drawLine(sellX - 15, yAxisValue - 1, sellX + 15, yAxisValue - 1, paint);//3 - Sell Bottom Border Line
            canvas.drawLine(buyX - 15, buyY + 1, buyX + 15, buyY + 1, paint);//4 - Buy Top Border Line
            canvas.drawLine(buyX - 15, holdY, buyX + 15, holdY, paint);//5 - Buy Bottom Border Line
            canvas.drawLine(holdX - 15, sellY, holdX + 15, sellY, paint);//6 - Hold Bottom Border Line

            //Restore
            paint.setColor(blackTextColor);
            barCurVolumeWidth = 30;
            if (mRenderer.getIntervalBtwPoints() > mRenderer.getBaseIntervalBtwPoints()) {
                barCurVolumeWidth = (barCurVolumeWidth * (mRenderer.getIntervalBtwPoints() / 10));

            }
            paint.setStrokeWidth(barCurVolumeWidth);

            //Values over Bar
            for (XYSeries xySeries : mDataset.getSeries()) {
                if (xySeries.getTitle().equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_HOLDRATING)) {
                    hold_value = xySeries.getY(mRenderer.getStartIndex() + (incrementVal));
                }
                if (xySeries.getTitle().equalsIgnoreCase(ChartConstants.CONSENSUS_CHART_SELLUNDERWEIGHTRATING)) {
                    sell_value = xySeries.getY(mRenderer.getStartIndex() + (incrementVal));
                }
            }

            //Common Settings for Percentage Text
            paint.setColor(blackTextColor);
            paint.setTextAlign(Align.CENTER);
            paint.setTextSize(12);
            //int size = (context)getResources().getDimensionPixelSize(R.dimen.text_size_fourteen);
            //paint.setTextSize(size);

            //Draw Sell Value
            //String sellValue = String.valueOf(sell_value)+"%";
            String sellValue = String.valueOf(Math.round(sell_value)) + "%";
            int height = getTextHeight(sellValue, paint);
            canvas.drawText(sellValue, sellX, sellY + height + 5, paint);

            //Draw Hold Value
            double originalHoldValue = hold_value - sell_value;
            //String holdingsValue = String.valueOf(originalHoldValue)+"%";
            String holdingsValue = String.valueOf(Math.round(originalHoldValue)) + "%";
            height = getTextHeight(holdingsValue, paint);
            canvas.drawText(holdingsValue, holdX, holdY + height + 5, paint);

            //Draw Buy Value
            double originalBuyValue = 100 - hold_value;
            //String buyValue = String.valueOf(originalBuyValue)+"%";
            String buyValue = String.valueOf(Math.round(originalBuyValue)) + "%";
            height = getTextHeight(buyValue, paint);
            canvas.drawText(buyValue, buyX, buyY + height + 5, paint);

        }
    }


    public int getTextHeight(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, 1, bounds);
        int height = bounds.bottom + bounds.height();
        return height;
    }

}