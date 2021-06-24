package com.acumengroup.greekmain.core.model.userloginvalidation;

import com.acumengroup.greekmain.core.model.GreekRequestModel;
import com.acumengroup.greekmain.core.model.GreekResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sushant.patil on 3/23/2016.
 */
public class QuestionListManager implements GreekRequestModel, GreekResponseModel {
    private String question_id;
    private String answer;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public JSONObject toJSONObject()
            throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("question_id", this.question_id);
        jo.put("answer", this.answer);
        return jo;
    }

    public QuestionListManager fromJSON(JSONObject jo)
            throws JSONException {
        this.question_id = jo.optString("question_id");
        this.answer = jo.optString("answer");
        return this;
    }

}
