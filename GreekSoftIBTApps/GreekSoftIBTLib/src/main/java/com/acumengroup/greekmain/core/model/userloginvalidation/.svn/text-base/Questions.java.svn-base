package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sushant.patil on 3/22/2016.
 */
public class Questions implements GreekRequestModel, GreekResponseModel, Serializable {
    private String question_id;
    private String question;
    private String last_updated_time;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLast_updated_time() {
        return last_updated_time;
    }

    public void setLast_updated_time(String last_updated_time) {
        this.last_updated_time = last_updated_time;
    }

    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("question_id", this.question_id);
        jo.put("question", this.question);
        jo.put("last_updated_time", this.last_updated_time);

        return jo;
    }

    public GreekResponseModel fromJSON(JSONObject jo)
            throws JSONException {
        this.question_id = jo.optString("question_id");
        //this.question = String.valueOf(Base64.decode(jo.optString("question"), Base64.DEFAULT));
        this.question = jo.optString("question");
        this.last_updated_time = jo.optString("last_updated_time");

        return this;
    }
}


