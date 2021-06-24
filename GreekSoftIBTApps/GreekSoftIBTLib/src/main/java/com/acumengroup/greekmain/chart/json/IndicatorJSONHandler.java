package com.acumengroup.greekmain.chart.json;

import com.acumengroup.greekmain.chart.xml.IndicatorData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 * * Created by Arcadia
 */
public class IndicatorJSONHandler {

    /**
     * Handling Indicator.xml. Its common for all brokers. 814 response contains
     * indicator values.
     *
     * @param response
     * @return
     */
    public Hashtable parser(String response) {

        Hashtable<String, IndicatorData> fullIndicatorData = new Hashtable<String, IndicatorData>();

        IndicatorList indicatorList = new IndicatorList();

        try {

            indicatorList.fromJSON(new JSONObject(response));

            List<Indicator> indicatorLists = indicatorList.getIndicatorList();

            for (Indicator ind : indicatorLists) {

                List<InputList> InpitList = ind.getInputList();
                List<OutputList> outpitList = ind.getOutputList();

                IndicatorData indicatorData = new IndicatorData();

                indicatorData.setCategory(ind.getCategory());
                indicatorData.setDescription(ind.getDescription());

                for (OutputList outputList : outpitList) {
                    indicatorData.setOutputKey(outputList.getKey());
                    indicatorData.setOutputLineType(outputList.getLinetype());
                    indicatorData.setOutputPlotType(outputList.getPlottype());

                }

                for (InputList inputList : InpitList) {
                    indicatorData.setInputDefault(inputList.getDefaultValue());
                    indicatorData.setInputDescription(inputList.getDescription());
                    indicatorData.setInputName(inputList.getName());
                    indicatorData.setInputType(inputList.getType());

                }

                indicatorData.setName(ind.getName());

                fullIndicatorData.put(ind.getName(), indicatorData);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fullIndicatorData;
    }

}
