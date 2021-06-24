package com.acumengroup.greekmain.chart.xml;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

import com.acumengroup.greekmain.chart.util.GreekChartLogger;
import com.acumengroup.greekmain.chart.util.GreekHashtable;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Hashtable;

/**
 * * Created by Arcadia
 */
public class IndicatorXMLHandler {

    private GreekHashtable rootHashtable = new GreekHashtable();
    private IndicatorData indicatorData;

    private String INDICATOR_LIST = "indicator-list";
    private String INDICATOR = "indicator";
    private String NAME = "name";
    private String CATEGORY = "category";
    private String DESCRIPTION = "description";
    private String INPUT_LIST = "input-list";
    private String INPUT = "input";
    private String TYPE = "type";
    private String DEFAULT = "default";
    private String OUTPUT_LIST = "output-list";
    private String OUTPUT = "output";
    private String KEY = "key";
    private String LINE_TYPE = "linetype";
    private String PLOT_TYPE = "plottype";

    /**
     * Handling Indicator.xml. Its common for all brokers. 814 response contains
     * indicator values.
     *
     * @param response
     * @return
     */
    public Hashtable parser(String response) {
        RootElement root = new RootElement(INDICATOR_LIST);
        Element indicator = root.getChild(INDICATOR);
        indicator.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes arg0) {
                indicatorData = new IndicatorData();
            }
        });
        indicator.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                rootHashtable.put(indicatorData.getName(), indicatorData);
            }
        });
        indicator.getChild(NAME).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setName(body);
                    }
                });
        indicator.getChild(CATEGORY).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setCategory(body);
                    }
                });
        indicator.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setDescription(body);
                    }
                });
        Element inputList = indicator.getChild(INPUT_LIST);
        Element input = inputList.getChild(INPUT);
        input.getChild(NAME).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setInputName(body);
                    }
                });
        input.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setInputDescription(body);
                    }
                });
        input.getChild(TYPE).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setInputType(body);
                    }
                });
        input.getChild(DEFAULT).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setInputDefault(body);
                    }
                });
        Element outputList = indicator.getChild(OUTPUT_LIST);
        Element output = outputList.getChild(OUTPUT);
        output.getChild(KEY).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setOutputKey(body);
                    }
                });
        output.getChild(LINE_TYPE).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setOutputLineType(body);
                    }
                });
        output.getChild(PLOT_TYPE).setEndTextElementListener(new EndTextElementListener() {
                    public void end(String body) {
                        indicatorData.setOutputPlotType(body);
                    }
                });

        try {
            Xml.parse(response, root.getContentHandler());
        } catch (SAXException e) {
            GreekChartLogger.logError("Problem in parsing the indicator.");
            e.printStackTrace();
        }
        return rootHashtable;
    }

}
