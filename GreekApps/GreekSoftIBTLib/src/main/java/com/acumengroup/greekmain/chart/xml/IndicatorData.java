package com.acumengroup.greekmain.chart.xml;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * * Created by Arcadia
 *         <p/>
 *         Getter and Setter method class for IndicatorXMLHandler Class.
 */
public class IndicatorData implements Serializable {

    private String name;
    private String description;
    private String category;
    private ArrayList inputName = new ArrayList();
    private ArrayList inputDescription = new ArrayList();
    private ArrayList inputType = new ArrayList();
    private ArrayList inputDefault = new ArrayList();
    private ArrayList<String> outputKey = new ArrayList<String>();
    private ArrayList<String> outputLineType = new ArrayList<String>();
    private ArrayList outputPlotType = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName.add(inputName);
    }

    public ArrayList getInputDescription() {
        return inputDescription;
    }

    public void setInputDescription(String inputDescription) {
        this.inputDescription.add(inputDescription);
    }

    public ArrayList getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType.add(inputType);
    }

    public ArrayList getInputDefault() {
        return inputDefault;
    }

    public void setInputDefault(String inputDefault) {
        this.inputDefault.add(inputDefault);
    }

    public ArrayList<String> getOutputKey() {
        return outputKey;
    }

    public void setOutputKey(String outputKey) {
        this.outputKey.add(outputKey);
    }

    public ArrayList<String> getOutputLineType() {
        return outputLineType;
    }

    public void setOutputLineType(String outputLineType) {
        this.outputLineType.add(outputLineType);
    }

    public ArrayList getOutputPlotType() {
        return outputPlotType;
    }

    public void setOutputPlotType(String outputPlotType) {
        this.outputPlotType.add(outputPlotType);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
