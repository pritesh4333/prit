package com.acumengroup.greekmain.core.request;

import android.content.Context;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.parser.JSONRequest;

import org.json.JSONObject;

public class GreekJSONRequest extends JSONRequest implements GreekConstants {

    public GreekJSONRequest(Context context, JSONObject request) {
        super(context, request);
        //setFormFactorValue("M");
        //setData("FormFactor", "M");
//        setBaseUrl(CURRENT_URL);
        setBaseUrl(AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port());
    }
}
