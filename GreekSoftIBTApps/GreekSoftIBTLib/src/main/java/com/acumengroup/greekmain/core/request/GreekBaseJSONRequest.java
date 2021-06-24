package com.acumengroup.greekmain.core.request;

import android.content.Context;

import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.parser.JSONRequest;

import org.json.JSONObject;

public class GreekBaseJSONRequest extends JSONRequest implements GreekConstants {

    public GreekBaseJSONRequest(Context context, JSONObject request) {
        super(context, request);
        //setFormFactorValue("M");
        //setData("FormFactor", "M");
        setBaseUrl(CURRENT_BASE_URL);
    }
}