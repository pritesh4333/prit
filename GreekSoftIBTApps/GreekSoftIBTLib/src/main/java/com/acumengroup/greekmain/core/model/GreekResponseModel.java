/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acumengroup.greekmain.core.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Arcadia
 */
public interface GreekResponseModel extends Serializable {

    GreekResponseModel fromJSON(JSONObject res) throws JSONException;
}
