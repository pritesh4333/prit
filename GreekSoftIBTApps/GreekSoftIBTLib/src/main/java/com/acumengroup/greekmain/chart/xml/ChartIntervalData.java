package com.acumengroup.greekmain.chart.xml;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * * Created by Arcadia
 *         <p/>
 *         <br>
 *         <p/>
 *         Getter and Setter method class for ChartintervalXmlHandler Class.
 */
public class ChartIntervalData implements Serializable {
    private ArrayList<String> intervalDisplay = new ArrayList<String>();
    private ArrayList<String> intervaValue = new ArrayList<String>();

    public ArrayList<String> getintervalDisplay() {
        return intervalDisplay;
    }

    public void addIntervalDisplay(String key) {
        this.intervalDisplay.add(key);
    }

    public ArrayList<String> getIntervalValue() {
        return intervaValue;
    }

    public void addIntervalValue(String value) {
        this.intervaValue.add(value);
    }

}
